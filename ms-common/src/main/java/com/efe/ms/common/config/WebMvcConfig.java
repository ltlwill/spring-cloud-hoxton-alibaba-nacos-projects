//package com.efe.ms.common.config;
//
//import java.util.List;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurerAdapter{
//	
//	 @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//       super.configureMessageConverters(converters);
//       converters.add(0, new MappingJackson2HttpMessageConverter());
//    }
//}
