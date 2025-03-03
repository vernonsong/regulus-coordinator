/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/** 每日消息表 */
@TableName(value = "message_daily")
@Data
public class MessageDailyDo {
    /** 唯一标识符 */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 正文内容（文字类型） */
    @TableField(value = "content")
    private String content;

    /** 图片存储路径（图片类型） */
    @TableField(value = "img_path")
    private String imgPath;

    /** 交易日期 */
    @TableField(value = "trade_date")
    private Date tradeDate;

    /** 发送时段：早晨（morning）、中午（noon）、晚间（evening） */
    @TableField(value = "send_period")
    private Object sendPeriod;

    /** 消息来源（默认为证券经理） */
    @TableField(value = "source")
    private String source;

    /** 创建时间 */
    @TableField(value = "create_time")
    private Date createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private Date updateTime;
}
