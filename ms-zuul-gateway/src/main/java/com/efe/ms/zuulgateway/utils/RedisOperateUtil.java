package com.efe.ms.zuulgateway.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.efe.ms.common.util.SpringBeanUtil;

/**
 * redis操作工具类
 *
 * @author TianLong Liu
 * @date 2019年11月25日 上午9:44:40
 */
@Component
public final class RedisOperateUtil {

	private static StringRedisTemplate stringRedisTemplate;

	public static void set(String key, String value) {
		opsForValue().set(key, value);
	}

	public static void set(String key, String value, long timeout,TimeUnit unit) {
		opsForValue().set(key, value, timeout, unit);
	}

	public static String get(String key) {
		return opsForValue().get(key);
	}

	public static void delete(String key) {
		getStringRedisTemplate().delete(key);
	}
	
	public static ValueOperations<String, String> opsForValue(){
		return getStringRedisTemplate().opsForValue();
	}

	public static StringRedisTemplate getStringRedisTemplate() {
		if (stringRedisTemplate == null) {
			synchronized (RedisOperateUtil.class) {
				if (stringRedisTemplate == null) {
					stringRedisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
				}
			}
		}
		return stringRedisTemplate;
	}
}
