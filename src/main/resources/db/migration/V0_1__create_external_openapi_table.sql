CREATE TABLE external_openapi
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增 ID',
    service_name VARCHAR(50)  NOT NULL COMMENT '服务名称（如：WeChat, AliPay, GitHub）',
    api_name     VARCHAR(100) NOT NULL COMMENT 'API 名称（如：sendMessage, createPayment）',
    api_url      VARCHAR(255) NOT NULL COMMENT 'OpenAPI URL（如 https://api.example.com/create）',
    status       TINYINT(1)   NOT NULL COMMENT '状态（1：启用，0：禁用）',
    extra_config JSON                  DEFAULT NULL COMMENT '扩展字段（JSON 格式存储更多配置信息）',
    create_time  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uniq_service_api_name (service_name, api_name) COMMENT '确保每个服务的 API 名称唯一'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='存储其它服务 OpenAPI URL 及相关信息';