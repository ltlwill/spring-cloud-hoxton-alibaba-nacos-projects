//package com.efe.ms.zuulgateway.config;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//import com.efe.ms.common.vo.ResponseResult;
//
///**
// * 为所有路由提供默认回退
// * @author Tianlong Liu
// * @2020年8月14日 上午11:34:14
// */
//@Component
//public class DefaultFallbackProvider implements FallbackProvider {
//	@Override
//	public String getRoute() {
//		return "*";
//	}
//
//	@Override
//	public ClientHttpResponse fallbackResponse(String route, Throwable throwable) {
//		return new ClientHttpResponse() {
//			@Override
//			public HttpStatus getStatusCode() throws IOException {
//				return HttpStatus.INTERNAL_SERVER_ERROR;
//			}
//
//			@Override
//			public int getRawStatusCode() throws IOException {
//				return 200;
//			}
//
//			@Override
//			public String getStatusText() throws IOException {
//				return "OK";
//			}
//
//			@Override
//			public void close() {
//
//			}
//
//			@Override
//			public InputStream getBody() throws IOException {
//				return new ByteArrayInputStream(JSON.toJSONBytes(ResponseResult.fail("fallback")));
//			}
//
//			@Override
//			public HttpHeaders getHeaders() {
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_JSON);
//				return headers;
//			}
//		};
//	}
//}