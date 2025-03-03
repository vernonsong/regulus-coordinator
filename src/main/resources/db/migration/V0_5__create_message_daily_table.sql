CREATE TABLE message_daily
(
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一标识符',
    content     TEXT COMMENT '正文内容（文字类型）',
    img_path    VARCHAR(200) COMMENT '图片存储路径（图片类型）',
    trade_date  DATE                                NOT NULL COMMENT '交易日期',
    send_period ENUM ('morning', 'noon', 'evening') NOT NULL COMMENT '发送时段：早晨（morning）、中午（noon）、晚间（evening）',
    source      VARCHAR(100)                        NOT NULL DEFAULT '证券经理' COMMENT '消息来源（默认为证券经理）',
    create_time TIMESTAMP                           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP                           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_trade_date (trade_date),
    INDEX idx_trade_date_period (trade_date, send_period)
);