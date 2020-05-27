package com.efe.ms.zuulgateway.utils;

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

/**
 * JSON web token 工具类
 * @author TianLong Liu
 * @date 2019年9月6日 下午3:27:18
 */
public final class JWTUtil {
	
	private static final String PUBLIC_SECRET = "public-key";
	private static final String ISSUSER = "hengzhiyi.cn";
//	private static final long expireMillis = 1000 * 60 * 60 * 12; //12h
	private static final long expireMillis = 1000 * 15; // 15s
	
	/**
	 * 创建token
	 * @param userId
	 * @return
	 * @throws JWTCreationException
	 */
	public static String createToken(String userId) throws JWTCreationException{
		try{
			Map<String,Object> map = new HashMap<>();
			map.put("a", "xaxa");
			map.put("b", "sax222");
			map.put("roles","admin,user");
			Algorithm algorithm = Algorithm.HMAC256(PUBLIC_SECRET);
			Builder builder = JWT.create();
			builder.withIssuer(ISSUSER)
	        	.withExpiresAt(new Date(System.currentTimeMillis() + expireMillis))
	        	.withJWTId(userId)
	        	.withKeyId(userId)
	        	.withHeader(map)
	        	.withClaim("xxx", "909121212")
	        	.withArrayClaim("iii", new String[]{"ooo","111xxx"}); 
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
	public static DecodedJWT verifyToken(String userId,String token) throws JWTVerificationException{
		try {
		    Algorithm algorithm = Algorithm.HMAC256(PUBLIC_SECRET);
		    JWTVerifier verifier = JWT.require(algorithm)
		    		.withIssuer(ISSUSER)
		    		.withJWTId(userId)
		    		.build();
		    return verifier.verify(token);
		} catch (JWTVerificationException e){
		    throw e;
		}
	}
	
}
