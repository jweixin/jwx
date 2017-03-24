package com.github.jweixin.jwx.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单事件注解
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuEvent {
	/**
	 * 菜单类型
	 * @return
	 */
	MenuType value();
	/**
	 * 事件key值
	 * @return
	 */
	String eventKey() default "";
}
