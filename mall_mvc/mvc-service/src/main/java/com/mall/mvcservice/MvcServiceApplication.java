package com.mall.mvcservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.mall.common.mapper")
@EnableScheduling
@Configurable
public class MvcServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcServiceApplication.class, args);
    }

}
