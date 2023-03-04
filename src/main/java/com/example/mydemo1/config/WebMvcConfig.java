package com.example.mydemo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public RequestMappingHandlerAdapter MyRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();

        List<HttpMessageConverter<?>> converters = adapter.getMessageConverters();

        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        MediaType xmlMedia = new MediaType(MediaType.TEXT_XML, Charset.forName("UTF-8"));
        supportedMediaTypes.add(xmlMedia);

        xmlConverter.setSupportedMediaTypes(supportedMediaTypes);

        converters.add(xmlConverter);

        adapter.setMessageConverters(converters);

        return adapter;
    }
}