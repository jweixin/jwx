package com.github.jweixin.jwx.servlet;

import java.util.Map;

import com.github.jweixin.jwx.context.WeixinContext;

/**
 * 微信上下文帮助类
 * 向web应用暴露微信Servlet定义的微信上下文对象
 * @author Administrator
 * @version 1.0 2017-2-7
 */
public class WeixinContextHelper {
	
	/**
	 * url与微信上下文的映射
	 * 在Servlet初始化完成后配置
	 */
	private static Map<String, WeixinContext> contextMapper;

	/**
	 * 设置帮助类的微信上下文映射
	 * @param contextMapper
	 */
	static void setContextMapper(Map<String, WeixinContext> contextMapper) {
		WeixinContextHelper.contextMapper = contextMapper;
	}
	
	/**
	 * 获取url对应的微信上下文
	 * @param url
	 * @return
	 */
	public static WeixinContext getContext(String url){
		if(contextMapper != null) {
			return contextMapper.get(url);
		}
		return null;
	}
	
	/**
	 * 获取上下文的access_token
	 * @param context
	 * @return
	 */
	public static String getAccessToken(WeixinContext context){
		return context.getAccessToken();
	}
	
	/**
	 * 获取url映射的上下文的access_token
	 * @param url
	 * @return
	 */
	public static String getAccessToken(String url){
		return getAccessToken(getContext(url));
	}
	
	/**
	 * 获取url映射的上下文的appid
	 * @param url
	 * @return
	 */
	public static String getAppid(String url){
		return getContext(url).getAppID();
	}
	
	/**
	 * 获取url映射的上下文的appsecret
	 * @param url
	 * @return
	 */
	public static String getAppSecret(String url){
		return getContext(url).getAppSecret();
	}

}
