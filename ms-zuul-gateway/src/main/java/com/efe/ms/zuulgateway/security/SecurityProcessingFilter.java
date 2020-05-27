package com.efe.ms.zuulgateway.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.efe.ms.common.constant.Constants;

public class SecurityProcessingFilter extends
		AbstractAuthenticationProcessingFilter {


	@Autowired
	protected SecurityProcessingFilter(
			RequestMatcher requestMatcher) {
		super(requestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		String userId = request.getHeader(Constants.Headers.LOGIN_USER_ID);
		String accessToken = request.getHeader(Constants.Headers.ACCESS_TOKEN);
		SignedUsernamePasswordAuthenticationToken authentication = new SignedUsernamePasswordAuthenticationToken(
				userId, accessToken, accessToken, accessToken);
		return getAuthenticationManager().authenticate(authentication);
	}

}
