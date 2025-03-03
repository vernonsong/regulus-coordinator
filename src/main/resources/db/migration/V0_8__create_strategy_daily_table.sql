CREATE TABLE strategy_daily
(
    trade_date  DATE                                NOT NULL COMMENT '交易日期',
    content     TEXT                                NOT NULL COMMENT '策略正文',
    create_time TIMESTAMP                           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP                           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (trade_date)
);