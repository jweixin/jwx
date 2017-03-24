package com.github.jweixin.jwx.message.cache;

/**
 * 消息的key值缓存接口
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次。
 * 用于剔除重试的消息排重
 * @author Administrator
 *
 */
public interface MessageKeyCache {
	/**
	 * 检查缓存中是否存在指定的key值
	 * key值添加微信上下文前缀，用于防止不同上下文key值冲突
	 * 对于不存在的key，需要将key添加到缓存并且返回false，方法同时需要完成过期key值的清理。
	 * @param key 接收普通消息，key为contextUrl + MsgId;接收事件推送，key值为contextUrl + FromUserName + CreateTime
	 * @return
	 */
	public boolean hasMessageKey(String key);

}
