package com.github.jweixin.jwx.context;

import java.lang.annotation.Annotation;

/**
 * 消息适配器
 * 在初始化扫描微信方法注解时生成，用于接收消息时匹配对应消息的方法
 * @author Administrator
 *
 */
public class MsgMatcher {
	/**
	 * 适配值
	 */
	private String matchValue;
	/**
	 * 适配成功后微信执行的方法
	 */
	private WeixinMethod method;
	/**
	 * 微信注解
	 */
	private Annotation wxAnnotation;

	public Annotation getWxAnnotation() {
		return wxAnnotation;
	}

	public void setWxAnnotation(Annotation wxAnnotation) {
		this.wxAnnotation = wxAnnotation;
	}

	public String getMatchValue() {
		return matchValue;
	}

	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}

	public WeixinMethod getMethod() {
		return method;
	}

	public void setMethod(WeixinMethod method) {
		this.method = method;
	}

}
