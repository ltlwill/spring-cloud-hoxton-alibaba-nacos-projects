package com.efe.ms.crawlerservice.util;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.efe.ms.common.util.SpringBeanUtil;

/**
 * redis操作工具类
 *
 * @author TianLong Liu
 * @date 2019年11月25日 上午9:44:40
 */
@Component
public final class RedisUtil {

	private static StringRedisTemplate stringRedisTemplate;

	public static void set(String key, String value) {
		getStringRedisTemplate().opsForValue().set(key, value);
	}

	public static void set(String key, String value, Duration timeout) {
		getStringRedisTemplate().opsForValue().set(key, value, timeout);
	}

	public static String get(String key) {
		return getStringRedisTemplate().opsForValue().get(key);
	}

	public static boolean delete(String key) {
		return getStringRedisTemplate().delete(key);
	}

	private static StringRedisTemplate getStringRedisTemplate() {
		if (stringRedisTemplate == null) {
			synchronized (RedisUtil.class) {
				if (stringRedisTemplate == null) {
					stringRedisTemplate = SpringBeanUtil.getBean(StringRedisTemplate.class);
				}
			}
		}
		return stringRedisTemplate;
	}
}
