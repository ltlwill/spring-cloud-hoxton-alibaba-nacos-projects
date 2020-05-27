package com.efe.ms.zuulgateway.security;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SuppressWarnings("serial")
@Setter
@Getter
public class SignedUsernamePasswordAuthenticationToken extends
		UsernamePasswordAuthenticationToken {
	
	private String userId;
	private String accessToken;

	public SignedUsernamePasswordAuthenticationToken(Object principal, 
			Object credentials) {
		super(principal, credentials);
	}
	public SignedUsernamePasswordAuthenticationToken(String userId,String accessToken,Object principal, 
			Object credentials) {
		super(principal, credentials);
		this.userId = userId;
		this.accessToken = accessToken;
	}
}
