package com.github.jweixin.jwx.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 媒体消息类型注解
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MediaMsg {
	/**
	 * 媒体类型枚举
	 * @return
	 */
	MediaEnum value();
}
