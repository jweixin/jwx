package com.github.jweixin.jwx.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户未关注时，进行二维码扫描关注后的事件推送注解
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanSubscribeEvent {
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
