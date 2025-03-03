package com.regulus.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/** 每日持仓表 */
@TableName(value = "position_daily")
@Data
public class PositionDailyDo {
    /** 交易日期 */
    @TableId(value = "trade_date")
    private Date tradeDate;

    /** 持仓信息 */
    @TableField(value = "position")
    private String position;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;
}
