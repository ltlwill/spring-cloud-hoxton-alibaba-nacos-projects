package com.efe.ms.common.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import okhttp3.ConnectionPool;

/**
 * 将 feign请求的底层实现换成 okhttp实现（注：需要配置：feign.okhttp.enabled=true 和 feign.httpclient.enabled=false）
 * 解决 通过feign调用服务，如果目标服务中抛出异常时，feign读取的结果是乱码的问题
 * 
 * @author Tianlong Liu
 * @date 2020年9月18日 下午3:15:43
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new okhttp3.OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS) 
            .connectTimeout(180, TimeUnit.SECONDS) 
            .writeTimeout(180, TimeUnit.SECONDS) 
            .connectionPool(new ConnectionPool())
            .build();
    }
}