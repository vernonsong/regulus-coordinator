/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.integration.bot;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.message.service.BotService;
import com.regulus.domain.core.message.service.MessageProcessService;
import com.regulus.domain.generic.service.ExternalOpenapiService;
import com.regulus.infrastructure.util.HttpUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

/** QQ机器人 接口层实现 */
@Slf4j
@Service
public class QQBotService implements BotService {
    private static final String QQ = "QQ";

    private static final String SEND_MESSAGE_TO_USER = "sendMessageToUser";

    private static final String GET_TOKEN = "getToken";

    private static final int NORMAL_MESSAGE = 0;

    private static final int CALLBACK_VERIFY = 13;

    private String cachedToken = null; // 缓存的 Token
    private long cacheExpiry = 0; // 缓存过期时间（时间戳）

    @Value("${bot.appId:}")
    private String appId;

    @Value("${bot.secret:}")
    private String secret;

    @Resource private ExternalOpenapiService externalOpenapiService;

    @Resource private CaffeineCacheManager cacheManager;

    @Resource private HttpUtil httpUtil;

    @Resource
    @Qualifier("fixedThreadPool") // 指定配置的固定线程池
    private Executor fixedExecutor;

    @Resource
    // 指定配置的固定线程池
    private MessageProcessService messageProcessService;

    @Override
    public void sendMessage(String message, String userId, String messageId) {
        try {
            // 获取 Token
            String token = getToken();

            // 构建请求 URL
            String url = externalOpenapiService.getApiUrl(QQ, SEND_MESSAGE_TO_USER);
            url = url.replace("{openid}", userId); // 将 URL 占位符替换为 `openId`

            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("content", message);
            requestBody.put("msg_type", 0);
            requestBody.put("msg_id", messageId);

            // 设置 HTTP 请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "QQBot " + token);

            // 同步发送 POST 请求
            String response = httpUtil.post(url, headers, requestBody.toJSONString());

            // 日志记录成功信息
            log.info("发送消息成功: {}", response);
        } catch (Exception e) {
            // 发生异常时记录日志
            log.error("发送消息失败: {}", e.getMessage(), e);
        }
    }

    @Override
    public JSONObject receiveEvent(JSONObject payload, Map<String, String> header) {
        return switch (payload.getInteger("op")) {
            case NORMAL_MESSAGE -> processMessage(payload, header);
            case CALLBACK_VERIFY -> callbackVerify(payload, header);
            default -> null;
        };
    }

    private JSONObject processMessage(JSONObject payload, Map<String, String> header) {
        String messageContent = payload.getJSONObject("d").getString("content");
        String imageUrl;
        JSONArray attachments = payload.getJSONObject("d").getJSONArray("attachments");
        String userId = payload.getJSONObject("d").getJSONObject("author").getString("user_openid");

        String messageId = payload.getJSONObject("d").getString("id");
        if (checkDuplicate(messageId)) return null;
        if (attachments != null && !attachments.isEmpty()) {
            imageUrl = attachments.getJSONObject(0).getString("url");
        } else {
            imageUrl = null;
        }
        fixedExecutor.execute(
                () ->
                        messageProcessService.processMessage(
                                messageContent, imageUrl, this, userId, messageId));
        return null;
    }

    private boolean checkDuplicate(String messageId) {
        Cache cache = cacheManager.getCache("24HoursLocalCache");

        // 使用putIfAbsent保证原子性操作（若不存在则存入标记）
        Cache.ValueWrapper existing = null;
        if (cache != null) {
            existing = cache.putIfAbsent(messageId, Boolean.TRUE);
        }
        return existing != null && Boolean.TRUE.equals(existing.get());
    }

    private JSONObject callbackVerify(JSONObject payload, Map<String, String> header) {
        try {
            String signature = signMessage(payload.getJSONObject("d"));
            JSONObject result = new JSONObject();
            result.put("plain_token", payload.getJSONObject("d").getString("plain_token"));
            result.put("signature", signature);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** 初始化时加载 `cachedTokenMono`，并动态设置缓存时间 */
    @PostConstruct
    private void initializeTokenCache() {
        cachedToken = fetchTokenWithCache(); // 分离逻辑，处理动态缓存时间
    }

    /** 获取 Token，如果缓存有效则直接使用，否则重新获取 @return the token */
    public synchronized String getToken() {
        long now = System.currentTimeMillis();

        // 判断缓存是否过期
        if (cachedToken != null && cacheExpiry > now) {
            return cachedToken;
        }

        // 如果过期，调用阻塞式方法获取新的 Token
        return fetchTokenWithCache();
    }

    /** 动态获取 Token，同时获取到有效期并更新缓存时间 */
    private String fetchTokenWithCache() {
        String url = externalOpenapiService.getApiUrl(QQ, GET_TOKEN);

        // 构建请求体
        JSONObject requestBody = new JSONObject();
        requestBody.put("appId", appId);
        requestBody.put("clientSecret", secret);

        try {
            String response = httpUtil.post(url, null, requestBody.toJSONString());

            // 解析响应数据
            JSONObject responseJson = JSONObject.parseObject(response);
            String accessToken = responseJson.getString("access_token");
            Integer expiresIn = responseJson.getInteger("expires_in");

            // 检查响应字段是否齐全
            if (accessToken == null || expiresIn == null) {
                throw new RuntimeException("响应数据缺少 access_token 或 expires_in");
            }

            // 更新缓存（可用于后续获取）
            cachedToken = accessToken;
            cacheExpiry = System.currentTimeMillis() + ((expiresIn - 60) * 1000L); // 提前1分钟过期，避免边界问题

            return accessToken;
        } catch (Exception e) {
            throw new RuntimeException("请求或解析响应失败: " + e.getMessage(), e);
        }
    }

    private String signMessage(JSONObject payload) throws Exception {
        String seed = secret;
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        Signature signature = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
        EdDSAPrivateKeySpec privateKey =
                new EdDSAPrivateKeySpec(seed.getBytes(StandardCharsets.UTF_8), spec);
        PrivateKey sKey = new EdDSAPrivateKey(privateKey);
        String message = payload.getString("event_ts") + payload.getString("plain_token");
        signature.initSign(sKey);
        signature.update(message.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(signature.sign());
    }
}
