package com.github.jweixin.jwx.config;

import java.util.List;

import com.github.jweixin.jwx.context.WeixinParameterConfig;
import com.github.jweixin.jwx.message.cache.ConcurrentHashMapMessageKeyCache;
import com.github.jweixin.jwx.message.cache.MessageKeyCache;

/**
 * 微信配置类
 * @author Administrator
 *
 */
public class WeixinConfigurer {
	/**
	 * 微信控制器类所在的包名，用于servlet启动时建立微信上下文对象
	 */
	private List<String> packages;
	/**
	 * 微信key值缓存，用于微信消息重排
	 */
	private MessageKeyCache messageKeyCache = new ConcurrentHashMapMessageKeyCache();
	/**
	 * 微信公众基本配置
	 */
	private WeixinParameterConfig weixinParameterConfig;
	/**
	 * 配置线程池大小
	 */
	private int threadPoolSize = WeixinConst.WEIXIN_METHOD_EXECUTE_THREAD_POOL_SIZE;
	/**
	 * 微信方法调用超时阀值，毫秒
	 */
	private long weixinMethodTimeoutThreshold = WeixinConst.WEIXIN_METHOD_INVOCATION_TIMEOUT_THRESHOLD;

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	public MessageKeyCache getMessageKeyCache() {
		return messageKeyCache;
	}

	public void setMessageKeyCache(MessageKeyCache messageKeyCache) {
		this.messageKeyCache = messageKeyCache;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public long getWeixinMethodTimeoutThreshold() {
		return weixinMethodTimeoutThreshold;
	}

	public void setWeixinMethodTimeoutThreshold(long weixinMethodTimeoutThreshold) {
		this.weixinMethodTimeoutThreshold = weixinMethodTimeoutThreshold;
	}

	public WeixinParameterConfig getWeixinParameterConfig() {
		return weixinParameterConfig;
	}

	public void setWeixinParameterConfig(WeixinParameterConfig weixinParameterConfig) {
		this.weixinParameterConfig = weixinParameterConfig;
	}

}
