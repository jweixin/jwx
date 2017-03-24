package com.github.jweixin.jwx.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 微信方法执行任务
 * 调度子线程执行微信方法，返回方法的执行结果
 * @author Administrator
 *
 */
public class WeixinMethodInvocationTask implements Callable<Object>  {
	/**
	 * 微信执行的对象
	 */
	private Object wxObj;
	/**
	 * 执行的微信方法
	 */
	private Method method;
	/**
	 * 方法参数
	 */
	private Object[] args;
	
	public WeixinMethodInvocationTask(Object wxObj, Method method, Object[] args) {
		this.wxObj = wxObj;
		this.method = method;
		this.args = args;
	}

	@Override
	public Object call() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//执行微信方法，并且返回执行结果
		return method.invoke(wxObj, args);
	}

}
