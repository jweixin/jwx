package com.github.jweixin.jwx.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文本消息注解
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TextMsg {
	/**
	 * 文本匹配
	 * @return
	 */
	String value() default "";
	/**
	 * 正则表达式匹配
	 * @return
	 */
	String pattern() default "";
	/**
	 * 文本匹配是否忽略大小写敏感，缺省是不能忽略；该配置对正则表达式无效。
	 * @return
	 */
	boolean ignoreCase() default false;
}
