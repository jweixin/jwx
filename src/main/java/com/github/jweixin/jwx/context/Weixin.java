package com.github.jweixin.jwx.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 微信注解
 * 所有配置项要与微信公众里面的设置一致
 * @author Administrator
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Weixin {
	/* 微信上下文url，不包括web应用的上下文部分，不能为空，是识别微信上下文的唯一凭证 */
	String value();
	/* 微信令牌值，用于微信签名认证，与公众号里面的token值要一致，要不然签名会失败 */
	String token() default "";
	/* 消息密钥，与公众号要一致 */
	String encodingAESKey() default "";
	/* 应用ID */
	String appID() default "";
	/* 应用密钥 */
	String appSecret() default "";
}
