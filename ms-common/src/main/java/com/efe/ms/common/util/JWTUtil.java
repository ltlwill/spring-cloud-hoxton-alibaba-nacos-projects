package com.efe.ms.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JSON web token(JWT) 工具类
 * @author Tianlong Liu
 * @2020年6月2日 下午12:04:28
 */
public final class JWTUtil {
	
	private static final String PUBLIC_SECRET = "default-public-key";
	private static final String ISSUSER = "default-iss";
	private static final long EXPIRE_MILLIS = 1000 * 60 * 60 * 12; //12h
	private static final String USER_ID = "userId";
	private static final String USER_NAME = "userName";
//	private static final long expireMillis = 1000 * 60 * 5; // 15s
	
	/**
	 * 创建token
	 * @param userId
	 * @return
	 * @throws JWTCreationException
	 */
	public static String createToken(String userId,String userName) throws JWTCreationException{
		return createToken(null,userId,userName);
	}
	
	public static String createToken(JWTConfig cfg, String userId,String userName) throws JWTCreationException{
		try{
			String secret = cfg == null || cfg.getSecret() == null ? PUBLIC_SECRET : cfg.getSecret();
			String iss = cfg == null || cfg.getIss() == null ? ISSUSER : cfg.getIss();
			long exp = cfg == null || cfg.getExp() == null ? EXPIRE_MILLIS : cfg.getExp();
			
			long tims = System.currentTimeMillis();
			Date now = new Date(tims);
			Map<String,Object> map = new HashMap<>();
			map.put(USER_ID, userId);
			map.put(USER_NAME, userName);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			Builder builder = JWT.create();
			builder.withIssuer(iss)
			.withExpiresAt(new Date(tims + exp))
			.withJWTId(userId)
			.withKeyId(userId)
			.withHeader(map)
			.withIssuedAt(now)
			.withClaim(USER_ID, userId)
			.withClaim(USER_NAME, userName);
			return builder.sign(algorithm); 
		}catch(JWTCreationException e){
			throw e;
		}
	}
	
	/**
	 * 验证token
	 * @param token
	 * @return
	 * @throws JWTVerificationException
	 */
	public static DecodedJWT verifyToken(String token) throws JWTVerificationException{
		return verifyToken(null,token);
	}
	
	public static DecodedJWT verifyToken(JWTConfig cfg,String token) throws JWTVerificationException{
		try {
			String secret = cfg == null || cfg.getSecret() == null ? PUBLIC_SECRET : cfg.getSecret();
			String iss = cfg == null || cfg.getIss() == null ? ISSUSER : cfg.getIss();
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(iss)
					.build();
			return verifier.verify(token);
		} catch (JWTVerificationException e){
			throw e;
		}
	}
	
	public static boolean verify(String token) throws JWTVerificationException{
		return verify(null,token);
	}
	
	public static boolean verify(JWTConfig cfg,String token) throws JWTVerificationException{
		if(token == null || "".equals(token.trim())) {
			return false;
		}
		try {
			String secret = cfg == null || cfg.getSecret() == null ? PUBLIC_SECRET : cfg.getSecret();
			String iss = cfg == null || cfg.getIss() == null ? ISSUSER : cfg.getIss();
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(iss)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt != null;
		} catch (Exception e){
			return false;
		}
	}
	
	@Setter
	@Getter
	@NoArgsConstructor
	public static class JWTConfig{
		private String secret; // 密匙
		private String iss; // jwt签发者
		private String sub; // jwt所面向的用户
		private String aud; // 接收jwt的一方
		private Long exp;   // jwt的过期时间，这个过期时间必须要大于签发时间(毫秒)
		private String nbf; // 定义在什么时间之前，该jwt都是不可用的
		private String iat; // jwt的签发时间
		private String jti; // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	}
	
}
