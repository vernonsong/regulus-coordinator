ALTER TABLE strategy_daily ADD COLUMN score VARCHAR(200) COMMENT '策略打分' AFTER content;
ALTER TABLE strategy_daily MODIFY COLUMN content TEXT NULL;