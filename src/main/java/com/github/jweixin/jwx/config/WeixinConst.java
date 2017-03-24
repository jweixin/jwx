package com.github.jweixin.jwx.config;

/**
 * 微信常量配置类
 * @author Administrator
 *
 */
public class WeixinConst {
	/**
	 * 微信方法调度执行最大时长，单位毫秒
	 */
	public final static long WEIXIN_METHOD_INVOCATION_TIMEOUT_THRESHOLD = 3000L;
	/**
	 * 消息key值在缓存中保留的时间阀值，单位为秒
	 */
	public final static int WEIXIN_MESSAGE_KEY_CACHE_TIMEOUT_THRESHOLD = 300;
	/**
	 * 微信access_token刷新时间间隔，单位秒
	 */
	public final static int WEIXIN_ACCESS_TOKEN_TIMEOUT_THRESHOLD = 3600;
	/**
	 * 微信方法执行线程池大小
	 */
	public final static int WEIXIN_METHOD_EXECUTE_THREAD_POOL_SIZE = 10;
	
	/**
	 * 获取access_token链接
	 * http请求方式: GET
	 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 */
	public final static String WEIXIN_ACCESS_TOKEN_LINK = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	/**
	 * 客服接口-发消息
	 * http请求方式: POST
	 * https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
	 */
	public final static String WEIXIN_CUSTOM_MESSAGE_SEND_LINK = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

}
