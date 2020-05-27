package com.efe.ms.zuulgateway.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 自定义认证
 * @author TianLong Liu
 * @date 2019年9月10日 下午3:01:34
 */

//@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		System.out.println("-------------------");
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
