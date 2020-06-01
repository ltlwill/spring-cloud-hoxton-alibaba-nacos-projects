package com.efe.ms.serviceconsumer.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * 自定义拦截器（需要注册，继承WebMvcConfigurerAdapter中注册）
 * 
 * @author liutianlong
 *
 */
public class ApplicationInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationInterceptor.class);

	// private final static String HEADER_AJAX_NAME = "X-Requested-With";
	// private final static String HEADER_AJAX_VALUE = "XMLHttpRequest";
	private final static String HEADER_REFERER_NAME = "Referer";
	private final static String HEADER_ORIGIN_NAME = "Origin";
	private final static String DEFAULT_METHOD_NAME = "process";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		logger.debug("WebsiteInterceptor 处理请求完成之后，视图渲染之后执行...");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		logger.debug("WebsiteInterceptor 处理请求完成之后，视图渲染之前...");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		logger.debug("WebsiteInterceptor 处理请求之前执行...");

//		// 如果不是 ajax请求
//		if ((arg2 instanceof ParameterizableViewController)) {
//			return true;
//		} // 如果是ajax请求(不能以请求头：X-Requested-With=XMLHttpRequest)来判断，因ajax请求也不一定有这个请求头
//		if (arg2 instanceof HandlerMethod) {
//			MethodParameter[] params = ((HandlerMethod) arg2).getMethodParameters();
//			if (params != null && params.length > 0 && DEFAULT_METHOD_NAME.equals(params[0].getMethod().getName())) {
//				return true;
//			}
//		}
//		EnvironmentProperties props = ApplicationContextUtil.getApplicationContext()
//				.getBean(EnvironmentProperties.class);
//		String origin = request.getHeader(HEADER_ORIGIN_NAME), referer = request.getHeader(HEADER_REFERER_NAME),
//				url = request.getRequestURI();
//		if ("".equals(url) || "/".equals(url) || props == null || !props.getEnableValidRequestReferer()
//				|| props.getAllowRequestReferers() == null) {
//			return true;
//		}
//		List<String> allowReferers = props.getAllowRequestReferers();
//		boolean pass = origin != null && referer != null && allowReferers.contains(origin)
//				&& referer.startsWith(origin);
//		if (!pass) {
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		}
//		return pass; // true:继续执行，false:取消当前请求
		return true;
	}

}
