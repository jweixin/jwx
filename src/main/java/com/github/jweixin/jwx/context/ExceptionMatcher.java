package com.github.jweixin.jwx.context;

import com.github.jweixin.jwx.message.annotation.ExceptionHandler;

/**
 * 异常适配器
 * 
 * @author Administrator
 *
 */
public class ExceptionMatcher {
	/**
	 * 异常类型
	 */
	private Class<? extends Throwable> exceptionType;
	/**
	 * 适配成功后微信执行的方法
	 */
	private WeixinMethod method;
	/**
	 * 微信异常注解
	 */
	private ExceptionHandler exceptionAnnotation;

	public Class<? extends Throwable> getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(Class<? extends Throwable> exceptionType) {
		this.exceptionType = exceptionType;
	}

	public WeixinMethod getMethod() {
		return method;
	}

	public void setMethod(WeixinMethod method) {
		this.method = method;
	}

	public ExceptionHandler getExceptionAnnotation() {
		return exceptionAnnotation;
	}

	public void setExceptionAnnotation(ExceptionHandler exceptionAnnotation) {
		this.exceptionAnnotation = exceptionAnnotation;
	}

}
