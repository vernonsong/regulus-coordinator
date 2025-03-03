/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** HTTP请求 工具类 */
@Component
@Slf4j
public class HttpUtil {
    // 默认超时时间（从 Spring 配置文件中加载）
    @Value("${regulus.http.timeout.read:300}")
    private long defaultReadTimeout;

    // HttpClient（默认全局创建）
    private final HttpClient httpClient;

    /**
     * 生成http客户端（单例）
     *
     * @param defaultConnectTimeout 默认超市时间
     */
    public HttpUtil(@Value("${regulus.http.timeout.connect:10}") long defaultConnectTimeout) {
        this.httpClient = createHttpClient(defaultConnectTimeout);
    }

    /** 创建一个 HttpClient，忽略 HTTPS 证书校验 */
    private HttpClient createHttpClient(long defaultConnectTimeout) {
        try {
            // 创建一个 "不安全" 的 TrustManager，去信任所有证书
            TrustManager[] trustAllCerts =
                    new TrustManager[] {
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] chain, String authType) {
                                // 不校验客户端证书
                            }

                            @Override
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] chain, String authType) {
                                // 不校验服务端证书
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[0];
                            }
                        }
                    };

            // 设置 SSLContext 使用上述的 TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 创建 HttpClient 并设置自定义 SSLContext 和禁用主机名验证
            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .sslParameters(new SSLParameters())
                    .connectTimeout(Duration.ofSeconds(defaultConnectTimeout))
                    .version(HttpClient.Version.HTTP_2) // 使用
                    // HTTP/2
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("创建忽略 SSL 校验的 HttpClient 失败", e);
        }
    }

    /**
     * GET 请求（同步）
     *
     * @param url url
     * @param headers 请求头（默认为json格式请求头）
     * @param params 请求参数
     * @return 返回消息体
     * @throws Exception 异常
     */
    public String get(String url, Map<String, String> headers, Map<String, String> params)
            throws Exception {
        log.info("同步 GET 请求开始，URL: {}, Headers: {}, Params: {}", url, headers, params);

        // 构造 URL 带参数
        String fullUrl = buildUrlWithParams(url, params);

        HttpRequest.Builder requestBuilder =
                HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .GET()
                        .timeout(Duration.ofSeconds(defaultReadTimeout));

        // 添加请求头
        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest request = requestBuilder.build();

        // 发送请求并获取响应
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 日志记录
        log.info("GET 请求成功，URL: {}, 响应状态码: {}, 响应体: {}", url, response.statusCode(), response.body());

        // 返回响应
        return handleResponse(response);
    }

    /**
     * POST 请求（同步）
     *
     * @param url url
     * @param headers 请求头（默认为json格式请求头）
     * @param body 请求体
     * @return 返回消息体
     */
    public String post(String url, Map<String, String> headers, String body) {
        log.info("同步 POST 请求开始，URL: {}, Headers: {}, Body: {}", url, headers, body);

        HttpRequest.Builder requestBuilder =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .timeout(Duration.ofSeconds(defaultReadTimeout))
                        .header("Content-Type", "application/json"); // 默认请求头

        // 添加自定义请求头
        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest request = requestBuilder.build();

        // 发送请求并获取响应
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("请求异常", e);
            throw new RuntimeException(e);
        }

        // 日志记录
        log.info("POST 请求成功，URL: {}, 响应状态码: {}, 响应体: {}", url, response.statusCode(), response.body());

        // 返回响应
        return handleResponse(response);
    }

    /** 通用响应处理 */
    private String handleResponse(HttpResponse<String> response) {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            log.error("HTTP 请求失败，状态码: {}, 响应体: {}", response.statusCode(), response.body());
            throw new RuntimeException(
                    "HTTP 请求失败，状态码: " + response.statusCode() + ", 响应体: " + response.body());
        }
    }

    /** 构造带查询参数的 URL */
    private String buildUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }

        StringBuilder urlBuilder = new StringBuilder(url);
        if (!url.contains("?")) {
            urlBuilder.append("?");
        }
        params.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // 删除末尾的 “&”
        return urlBuilder.toString();
    }

    /**
     * GET 请求（同步）- 返回字节数组
     *
     * @param url url
     * @param headers 请求头（默认为json格式请求头）
     * @param params 请求参数
     * @return 二进制返回
     */
    public byte[] getAsBytes(String url, Map<String, String> headers, Map<String, String> params)
            throws Exception {
        log.info("同步 GET 请求（二进制）开始，URL: {}, Headers: {}, Params: {}", url, headers, params);

        String fullUrl = buildUrlWithParams(url, params);
        HttpRequest.Builder requestBuilder =
                HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .GET()
                        .timeout(Duration.ofSeconds(defaultReadTimeout));

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<byte[]> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

        log.info("GET 二进制请求成功，URL: {}, 响应状态码: {}", url, response.statusCode());
        return handleBytesResponse(response); // 需要新增 handleBytesResponse 方法
    }

    /** 处理二进制响应 */
    private byte[] handleBytesResponse(HttpResponse<byte[]> response) {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            log.error("HTTP 二进制请求失败，状态码: {}", response.statusCode());
            throw new RuntimeException("HTTP 请求失败，状态码: " + response.statusCode());
        }
    }
}
