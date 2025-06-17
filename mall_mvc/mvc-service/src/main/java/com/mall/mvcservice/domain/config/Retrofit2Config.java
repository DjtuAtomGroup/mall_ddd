package com.mall.mvcservice.domain.config;


import com.mall.mvcservice.service.WechatApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@Slf4j
public class Retrofit2Config {

    private static final String BASE_URL = "https://api.weixin.qq.com/";

    /**
     * 创建Retrofit实例
     * @return
     */
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


    @Bean
    public WechatApiService wechatApiService() {
        return retrofit().create(WechatApiService.class);
    }
}
