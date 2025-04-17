package com.regulus.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/** 每日策略表 */
@TableName(value = "strategy_daily")
@Data
public class StrategyDailyDo {
    /** 交易日期 */
    @TableId(value = "trade_date")
    private Date tradeDate;

    /** 策略正文 */
    @TableField(value = "content")
    private String content;

    /** 策略打分 */
    @TableField(value = "score")
    private String score;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;
}
