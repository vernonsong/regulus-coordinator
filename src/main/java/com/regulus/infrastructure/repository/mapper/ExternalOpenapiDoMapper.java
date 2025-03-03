package com.regulus.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.infrastructure.persistence.ExternalOpenapiDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/** 针对表【external_openapi】的数据库操作Mapper */
public interface ExternalOpenapiDoMapper extends BaseMapper<ExternalOpenapiDo> {
    /**
     * 根据服务名和API名获取API信息
     *
     * @param serviceName 服务名
     * @param apiName API名
     * @return ExternalOpenapiDo
     */
    @Select(
            "SELECT * FROM external_openapi WHERE service_name = #{serviceName} AND api_name = #{apiName}")
    ExternalOpenapiDo selectByServiceNameAndApiName(
            @Param("serviceName") String serviceName, @Param("apiName") String apiName);
}
