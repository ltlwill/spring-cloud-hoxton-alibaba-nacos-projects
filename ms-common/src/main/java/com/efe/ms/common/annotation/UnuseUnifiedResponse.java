package com.efe.ms.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不使用统一响应处理的注解，用此注解标记的类、方法将不进行统一响应处理
 * @author Tianlong Liu
 * @2020年8月11日 下午4:10:15
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnuseUnifiedResponse {
	
	String value() default "";
}
