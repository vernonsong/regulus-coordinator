CREATE TABLE stock_price_daily
(
    stock_code VARCHAR(20)    NOT NULL COMMENT '股票代码（如：600519.SH）',
    trade_date DATE           NOT NULL COMMENT '交易日期',
    open       DECIMAL(20, 3) DEFAULT NULL COMMENT '开盘价',
    close      DECIMAL(20, 3) DEFAULT NULL COMMENT '收盘价',
    high       DECIMAL(20, 3) DEFAULT NULL COMMENT '最高价',
    low        DECIMAL(20, 3) DEFAULT NULL COMMENT '最低价',
    volume     BIGINT         NOT NULL COMMENT '成交量',
    money      DECIMAL(15, 3) NOT NULL COMMENT '成交金额',
    PRIMARY KEY (stock_code, trade_date),
    INDEX      idx_trade_date (trade_date)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT='股票每日行情数据表';