package com.mdd.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liushan
 * @data 2023/4/1 1:22
 */
@Configuration
@ComponentScan(basePackages = {"com.mdd"})
@MapperScan(basePackages = {"com.mdd.*.mapper"})
@EnableTransactionManagement
@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class})
public class VolcanoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VolcanoApplication.class, args);
    }

}