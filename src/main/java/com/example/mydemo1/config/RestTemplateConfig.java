package com.example.mydemo1.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @desc:
 * @since: 2022/7/22
 */
@Configuration
public class RestTemplateConfig extends RestTemplate{

//    /**
//     * rest 模板
//     *
//     * @param restClientHttpRequestFactory
//     * @return
//     */
//    @Bean
//    public RestTemplate restTemplate( ClientHttpRequestFactory restClientHttpRequestFactory) {
//        return new RestTemplate(restClientHttpRequestFactory);
//    }
//
//    /**
//     * 请求连接池的配置信息
//     *
//     * @return
//     */
//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(15000);
//        factory.setReadTimeout(5000);
//        return factory;
//    }

}
