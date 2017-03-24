package com.github.jweixin.jwx.message.cache;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.github.jweixin.jwx.config.WeixinConst;

/**
 * 采用ConcurrentHashMap作为消息key值缓存的实现类
 * @author Administrator
 *
 */
public class ConcurrentHashMapMessageKeyCache implements MessageKeyCache {
	/**
	 * key值缓存容器
	 */
	private ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
	/**
	 * 上次清理的时间戳
	 */
	private volatile long lastCleanTimestamp = new Date().getTime();
	/**
	 * 缓存清理间隔
	 */
	private int interval = WeixinConst.WEIXIN_MESSAGE_KEY_CACHE_TIMEOUT_THRESHOLD;

	@Override
	public boolean hasMessageKey(String key) {
		long now = new Date().getTime();
		String random = UUID.randomUUID().toString();
		String value = key + "-" + random + "-" + now;
		//原子动作，如果不存在key，则putIfAbsent返回null；否则返回map中已经存在的key的值
		String mapValue = cache.putIfAbsent(key, value);
		//如果超过清理时间阀值，则做缓存清理
		if((now-lastCleanTimestamp)/1000 >= interval){
			synchronized (this) {
				if((now-lastCleanTimestamp)/1000 >= interval){
					//将清理时间设置为当前时间
					lastCleanTimestamp = now;
					//启动子线程处理缓存清理
					MapCacheCleanTask task = new MapCacheCleanTask(cache, interval);
					Thread t = new Thread(task);
					t.start();
				}
			}
		}
		//判断putIfAbsent返回值是否为null，如果为空，说明map里面不存在这个key，则返回false；
		//如果不为空，说明map里面已经有了值，则返回true。
		return mapValue != null;
	}
	
	/**
	 * 设置缓存清理时间间隔
	 * @param cleanInterval
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

}
