INSERT INTO regulus.external_openapi
(service_name, api_name, api_url, status, extra_config, create_time, update_time)
VALUES
    ('QQ', 'getToken', 'https://bots.qq.com/app/getAppAccessToken', 1, null, NOW(), NOW()),
    ('QQ', 'sendMessageToUser', '/v2/users/{openid}/messages', 1, null, NOW(), NOW());

