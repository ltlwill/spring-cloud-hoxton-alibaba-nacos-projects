package com.efe.ms.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.efe.ms.common.annotation.UnuseUnifiedResponse;
import com.efe.ms.common.exception.BusinessException;
import com.efe.ms.common.vo.ResponseResult;

/**
 * 统一返回结果处理配置
 * 
 * @author Tianlong Liu
 * @2020年8月11日 上午11:12:32
 */

@Configuration
@EnableWebMvc
public class UnifiedResponseConfig {

	@RestControllerAdvice(basePackages = { "com.efe.ms" })
	@ConditionalOnProperty(prefix = "app", name = { "unified-response-enabled" }, havingValue = "true",matchIfMissing = true)
	static class UnifiedResponseBodyAdvice implements ResponseBodyAdvice<Object> {

		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			// 有@UnuseUnifiedResponse 注解标注的方法不做处理
			if (returnType.hasMethodAnnotation(UnuseUnifiedResponse.class)) {
				return false;
			}
			// 有@UnuseUnifiedResponse 注解标注的类不做处理
			UnuseUnifiedResponse nur = returnType.getDeclaringClass().getAnnotation(UnuseUnifiedResponse.class);
			return nur == null;
		}

		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
				Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
				ServerHttpResponse response) {
			if (body instanceof ResponseResult) {
				return body;
			}
			return ResponseResult.success(body);
		}
		
		/**
		 * 全局异常捕捉处理
		 * 
		 * @param ex
		 * @return
		 */
		@ExceptionHandler(value = Exception.class)
		public ResponseResult<?> errorHandler(Exception ex) {
			return ResponseResult.fail(ex.getMessage());
		}

		/**
		 * 拦截捕捉自定义异常 BusinessException.class
		 * 
		 * @param ex
		 * @return
		 */
		@ExceptionHandler(value = BusinessException.class)
		public ResponseResult<?> businessErrorHandler(BusinessException ex) {
			return ResponseResult.fail(ex.getErrorCode(), ex.getMessage());
		}
	}
}
