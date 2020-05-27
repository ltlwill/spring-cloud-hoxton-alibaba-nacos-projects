package com.efe.ms.crawlerservice.web.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.efe.ms.crawlerservice.util.RequestUtil;
import com.efe.ms.crawlerservice.vo.BusinessResult;
import com.efe.ms.crawlerservice.vo.UserInfoVO;

/**
 * 基础控制器
 * 
 * @author TianLong Liu
 * @date 2019年11月6日 上午11:07:05
 */
public class BaseController {

	protected static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	protected BusinessResult jsonSuccess() {
		return BusinessResult.success();
	}

	protected BusinessResult jsonSuccess(Object data) {
		return BusinessResult.success().data(data);
	}

	protected BusinessResult jsonError(Object data, String message) {
		return BusinessResult.fail().data(data).message(message);
	}

	/**
	 * 得到request对象
	 */
	protected HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	protected String getHeader(String name) {
		HttpServletRequest request = getHttpServletRequest();
		return request == null ? null : request.getHeader(name);
	}

	protected UserInfoVO getUser() {
		return RequestUtil.getUser();
	}

	protected UserInfoVO getUser(String token) {
		return RequestUtil.getUser(token);
	}

}
