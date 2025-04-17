   CREATE TABLE `stock_factory_daily` (
  -- 核心标识字段
  `stock_code` VARCHAR(20)    NOT NULL COMMENT '股票代码（如：600519.SH）',
  `trade_date` DATE           NOT NULL COMMENT '交易日期',
   -- 技术因子（每个因子独立JSON字段）
  `macd` VARCHAR(200) COMMENT 'MACD指标',
  `kdj` VARCHAR(200) COMMENT 'KDJ指标',
   `boll` VARCHAR(200) COMMENT '布林线',
  `phy` VARCHAR(200) COMMENT 'PHY指标',
  `vr` VARCHAR(200) COMMENT 'VR指标',
  `vrsi` VARCHAR(200) COMMENT 'VRSI指标',
  `alligat` VARCHAR(200) COMMENT 'ALLIGAT指标',
  -- 主键与索引
    PRIMARY KEY (stock_code, trade_date),
    INDEX      idx_trade_date (trade_date)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='股票每日技术因子表';