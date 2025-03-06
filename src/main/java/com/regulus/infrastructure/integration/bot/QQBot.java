// package com.regulus.infrastructure.integration.bot;
//
// import com.alibaba.fastjson2.JSONObject;
//
// import com.regulus.domain.core.chat.service.Bot;
// import com.regulus.domain.generic.service.ExternalOpenapiService;
// import com.regulus.infrastructure.util.HttpUtil;
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.Resource;
// import lombok.extern.slf4j.Slf4j;
// import net.i2p.crypto.eddsa.EdDSAEngine;
// import net.i2p.crypto.eddsa.EdDSAPrivateKey;
// import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
// import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
// import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import
// org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.stereotype.Service;
// import org.apache.commons.codec.binary.Hex;
//
// import java.nio.charset.StandardCharsets;
// import java.security.MessageDigest;
// import java.security.PrivateKey;
// import java.security.Signature;
// import java.util.HashMap;
// import java.util.Map;
//
// @Slf4j
// @ConditionalOnProperty(name = "bot.type", havingValue = "QQ")
// @Service
// public class QQBot implements Bot {
// private final static String QQ = "QQ";
//
// private final static String SEND_MESSAGE_TO_USER = "sendMessageToUser";
//
// private final static String GET_TOKEN = "getToken";
//
// private String cachedToken = null; // 缓存的 Token
// private long cacheExpiry = 0; // 缓存过期时间（时间戳）
//
// @Value( "${bot.appId:}")
// private String appId;
//
// @Value( "${bot.secret:}")
// private String secret;
//
// @Value( "${bot.openId:}")
// private String openId;
//
// @Resource
// private ExternalOpenapiService externalOpenapiService;
//
// @Autowired
// private HttpUtil httpUtil;
//
// @Override
// public void sendMessage(String message) {
// try {
// // 获取 Token
// String token = getToken();
//
// // 构建请求 URL
// String url = externalOpenapiService.getApiUrl(QQ, SEND_MESSAGE_TO_USER);
// url = url.replace("{openid}", openId); // 将 URL 占位符替换为 `openId`
//
// // 构建请求体
// JSONObject requestBody = new JSONObject();
// requestBody.put("content", message);
// requestBody.put("msg_type", 0);
//
// // 设置 HTTP 请求头
// Map<String, String> headers = new HashMap<>();
// headers.put("Content-Type", "application/json");
// headers.put("Authorization", "QQBot " + token);
//
// // 同步发送 POST 请求
// String response = httpUtil.post(url, headers, requestBody.toJSONString());
//
// // 日志记录成功信息
// log.info("发送消息成功: {}", response);
// } catch (Exception e) {
// // 发生异常时记录日志
// log.error("发送消息失败: {}", e.getMessage(), e);
// }
// }
//
// @Override
// public JSONObject receiveEvent(JSONObject payload, Map<String, String>
// header) {
// try {
// String signature = signMessage(payload.getJSONObject("d"));
// JSONObject result = new JSONObject();
// result.put("plain_token",
// payload.getJSONObject("d").getString("plain_token"));
// result.put("signature", signature);
// return result;
// } catch (Exception e) {
// throw new RuntimeException(e);
// }
// }
//
// /**
// * 初始化时加载 `cachedTokenMono`，并动态设置缓存时间
// */
// @PostConstruct
// private void initializeTokenCache() {
// cachedToken = fetchTokenWithCache(); // 分离逻辑，处理动态缓存时间
// }
//
// /**
// * 获取 Token，如果缓存有效则直接使用，否则重新获取
// */
// public synchronized String getToken() {
// long now = System.currentTimeMillis();
//
// // 判断缓存是否过期
// if (cachedToken != null && cacheExpiry > now) {
// return cachedToken;
// }
//
// // 如果过期，调用阻塞式方法获取新的 Token
// return fetchTokenWithCache();
// }
//
// /**
// * 动态获取 Token，同时获取到有效期并更新缓存时间
// */
// private String fetchTokenWithCache() {
// String url = externalOpenapiService.getApiUrl(QQ, GET_TOKEN);
//
// // 构建请求体
// JSONObject requestBody = new JSONObject();
// requestBody.put("appId", appId);
// requestBody.put("clientSecret", secret);
//
// try {
// // 发送同步 HTTP POST 请求
// String response = httpUtil.post(url, null, requestBody.toJSONString());
//
// // 解析响应数据
// JSONObject responseJson = JSONObject.parseObject(response);
// String accessToken = responseJson.getString("access_token");
// Integer expiresIn = responseJson.getInteger("expires_in");
//
// // 检查响应字段是否齐全
// if (accessToken == null || expiresIn == null) {
// throw new RuntimeException("响应数据缺少 access_token 或 expires_in");
// }
//
// // 更新缓存（可用于后续获取）
// cachedToken = accessToken;
// cacheExpiry = System.currentTimeMillis() + ((expiresIn - 60) * 1000L); // 提前
// 1
// 分钟过期，避免边界问题
//
// return accessToken; // 直接返回 Token
// } catch (Exception e) {
// throw new RuntimeException("请求或解析响应失败: " + e.getMessage(), e);
// }
// }
//
// private String signMessage(JSONObject payload) throws Exception {
// String seed = secret;
// EdDSAParameterSpec spec =
// EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
// Signature signature = new
// EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
// EdDSAPrivateKeySpec privateKey = new
// EdDSAPrivateKeySpec(seed.getBytes(StandardCharsets.UTF_8), spec);
// PrivateKey sKey = new EdDSAPrivateKey(privateKey);
// String message = payload.getString("event_ts") +
// payload.getString("plain_token");
// signature.initSign(sKey);
// signature.update(message.getBytes(StandardCharsets.UTF_8));
// return Hex.encodeHexString(signature.sign());
// }
// }
