// package com.regulus.infrastructure.repository;
//
// import com.regulus.domain.generic.model.ExternalOpenapi;
// import com.regulus.domain.generic.repository.ExternalOpenapiRepository;
// import com.regulus.infrastructure.repository.mapper.ExternalOpenapiMapper;
// import jakarta.annotation.Resource;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.stereotype.Service;
//
// import java.util.Optional;
//
//
// @Service
// public class CachedExternalOpenapiRepository implements
// ExternalOpenapiRepository {
// @Resource
// private ExternalOpenapiMapper externalOpenapiMapper;
//
// @Override
// @Cacheable(value = "24HoursLocalCache", key = "#serviceName + ':' +
// #apiName")
// public Optional<ExternalOpenapi> findByServiceAndApiName(String serviceName,
// String apiName) {
// return
// Optional.ofNullable(externalOpenapiMapper.selectByServiceNameAndApiName(serviceName,
// apiName));
// }
// }
//
//
//
//
