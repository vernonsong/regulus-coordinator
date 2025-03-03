package com.regulus.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.infrastructure.persistence.ExternalOpenapiDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author vernonsong
 * @description 针对表【external_openapi(存储其它服务 OpenAPI URL 及相关信息)】的数据库操作Mapper
 * @createDate 2025-01-19 20:53:45 @Entity generator.domain.ExternalOpenapi
 */
public interface ExternalOpenapiDoMapper extends BaseMapper<ExternalOpenapiDo> {
    @Select("SELECT * FROM external_openapi WHERE service_name = #{serviceName} AND api_name = #{apiName}")
    ExternalOpenapiDo selectByServiceNameAndApiName(@Param("serviceName") String serviceName,
            @Param("apiName") String apiName);
}
