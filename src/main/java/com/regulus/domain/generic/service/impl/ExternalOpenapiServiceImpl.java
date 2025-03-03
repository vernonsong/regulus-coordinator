// package com.regulus.domain.generic.service.impl;
//
// import com.regulus.domain.generic.model.ExternalOpenapi;
// import com.regulus.domain.generic.repository.ExternalOpenapiRepository;
// import com.regulus.domain.generic.service.ExternalOpenapiService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import java.util.Optional;
//
/// **
// * @author vernonsong
// * @description 针对表【external_openapi(存储其它服务 OpenAPI URL 及相关信息)】的数据库操作Service实现
// * @createDate 2025-01-19 20:53:45
// */
// @Service
// public class ExternalOpenapiServiceImpl implements ExternalOpenapiService {
//
// @Autowired
// private ExternalOpenapiRepository externalOpenapiRepository;
//
// /**
// * 获取指定第三方API的URL
// *
// * @param serviceName 服务名
// * @param apiName API名
// * @return API URL
// */
// public String getApiUrl(String serviceName, String apiName) {
// Optional<ExternalOpenapi> result =
// externalOpenapiRepository.findByServiceAndApiName(serviceName, apiName);
// // 校验结果并返回
// if (result.isEmpty()) {
// throw new IllegalArgumentException("未查询到指定的 API 信息："
// + "serviceName=" + serviceName + ", apiName=" + apiName);
// }
// return result.get().getApiUrl();
// }
// }
//
//
//
//
