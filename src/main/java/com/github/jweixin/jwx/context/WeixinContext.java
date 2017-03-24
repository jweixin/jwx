package com.github.jweixin.jwx.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.aes.WXBizMsgCrypt;
import com.github.jweixin.jwx.config.WeixinConst;
import com.github.jweixin.jwx.util.StringUtil;
import com.github.jweixin.jwx.util.WeixinInterfaceException;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.AccessToken;

/**
 * 微信上下文
 * 一个微信上下文对应一个微信公众号，封装了公众号的基本信息以及消息处理机制
 * @author Administrator
 *
 */
public class WeixinContext {
	// 服务器地址
	private String url;
	// 令牌
	private String token;
	// EncodingAESKey
	private String encodingAESKey;
	// 开发者ID -> 应用ID
	private String appID;
	// 开发者ID -> 应用密钥
	private String appSecret;
	//消息加密
	WXBizMsgCrypt pc;
	/**
	 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
	 * access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效
	 */
	private volatile String accessToken;
	/**
	 * 上次更新access_token时间戳
	 */
	private volatile long lastUpdateAccessTokenTimestamp;
	/**
	 * access_token更新间隔
	 */
	private int interval = WeixinConst.WEIXIN_ACCESS_TOKEN_TIMEOUT_THRESHOLD;
	/**
	 * access_token失效时长
	 */
	private int expire = 7200;
	
	/**
	 * 消息类型与消息适配器列表的关联
	 */
	private Map<String, List<MsgMatcher>> messageMethodMappper = new HashMap<String, List<MsgMatcher>>();
	/**
	 * 异常处理适配器列表
	 */
	private List<ExceptionMatcher> exceptionMatchers = new ArrayList<ExceptionMatcher>();

	private static Logger logger = Logger.getLogger(WeixinContext.class);
	
	/**
	 * 获取微信上下文的access_token
	 * @return
	 * @throws NoAccessTokenException 
	 */
	public String getAccessToken() {
		Throwable ex = null;
		long now = new Date().getTime();
		if(accessToken == null || (now-lastUpdateAccessTokenTimestamp)/1000>=interval){
			synchronized (this) {
				if(accessToken == null || (now-lastUpdateAccessTokenTimestamp)/1000>=interval){
					//更新access_token
					String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
					AccessToken at = null;
					try {
						at = WeixinInterfaceHelper.get(url, AccessToken.class);
					} catch (WeixinInterfaceException e) {
						logger.warn("获取微信access_token失败:" + e.getMessage());
						ex = e.getRootCause();
					}
					if(at != null && !StringUtil.isNull(at.getAccessToken())) {
						accessToken = at.getAccessToken();
						lastUpdateAccessTokenTimestamp = now;
						expire = at.getExpiresIn();
					}
				}
			}
		}
		//access_token不为空且没有过期，则返回accessToken；否则抛出异常
		if(accessToken != null && (now-lastUpdateAccessTokenTimestamp)/1000<expire){
			return accessToken;
		} else {
			throw new NoAccessTokenException("无法获取微信上下文(" + url + ")的access_token", ex);
		}
	}

	public Map<String, List<MsgMatcher>> getMessageMethodMappper() {
		return messageMethodMappper;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public WXBizMsgCrypt getPc() {
		return pc;
	}

	public void setPc(WXBizMsgCrypt pc) {
		this.pc = pc;
	}

	public List<ExceptionMatcher> getExceptionMatchers() {
		return exceptionMatchers;
	}
}
