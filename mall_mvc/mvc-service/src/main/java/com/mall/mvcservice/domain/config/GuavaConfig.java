package com.mall.mvcservice.domain.config;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.EventBus;
import com.mall.mvcservice.listener.OrderPaySuccessListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {


    @Bean(name = "wechatAccessToken")
    public Cache<String, String> wechatAccessToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }



    @Bean(name = "openidToken")
    public Cache<String, String> openidToken() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }



    @Bean
    public EventBus eventBusBuilder(OrderPaySuccessListener listener) {
        EventBus bus = new EventBus();
        bus.register(listener);
        return bus;
    }
}
