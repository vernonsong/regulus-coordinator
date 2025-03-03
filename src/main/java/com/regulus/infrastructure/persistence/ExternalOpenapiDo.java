package com.regulus.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/** 存储其它服务 OpenAPI URL 及相关信息 */
@TableName(value = "external_openapi")
@Data
public class ExternalOpenapiDo {
    /** 主键，自增 ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 服务名称（如：WeChat, AliPay, GitHub） */
    @TableField(value = "service_name")
    private String serviceName;

    /** API 名称（如：sendMessage, createPayment） */
    @TableField(value = "api_name")
    private String apiName;

    /** OpenAPI URL（如 https://api.example.com/create） */
    @TableField(value = "api_url")
    private String apiUrl;

    /** 状态（1：启用，0：禁用） */
    @TableField(value = "status")
    private Integer status;

    /** 扩展字段（JSON 格式存储更多配置信息） */
    @TableField(value = "extra_config")
    private Object extraConfig;

    /** 创建时间 */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
