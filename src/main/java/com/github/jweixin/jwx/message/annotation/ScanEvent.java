package com.github.jweixin.jwx.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 二维码扫描事件注解
 * 用户已关注时的事件推送
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanEvent {
	/**
	 * 场景值
	 * @return
	 */
	String value() default "";
	/**
	 * 场景值正则表达式匹配
	 * @return
	 */
	String scenePattern() default "";
}
