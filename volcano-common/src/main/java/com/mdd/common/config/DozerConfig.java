package com.mdd.common.config;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 17:38
 */
public class DozerConfig {
    @Bean
    public DozerBeanMapper getDozerBean() {
        // 属性转换配置文件，可以有多个，后期如果一个文件过大，可以新增一个文件
        List<String> mappingFiles = Lists.newArrayList("dozer/volcano-info-mapping.xml");
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }
}
