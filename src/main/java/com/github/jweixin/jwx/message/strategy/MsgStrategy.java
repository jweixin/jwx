package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;

import javax.servlet.ServletException;

import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 消息策略
 * @author Administrator
 *
 */
public interface MsgStrategy {
	/**
	 * 建立消息注解与方法的映射
	 * @param context
	 * @param method
	 * @param anno
	 * @throws ServletException
	 */
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno) throws ServletException;
	
	/**
	 * 获取消息对应的执行方法
	 * @param context
	 * @param in
	 * @return
	 */
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in);

}
