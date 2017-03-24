package com.github.jweixin.jwx.message.cache;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存清理子线程
 * @author Administrator
 *
 */
public class MapCacheCleanTask implements Runnable {
	
	private ConcurrentHashMap<String, String> cache;
	private int interval;
	
	public MapCacheCleanTask(ConcurrentHashMap<String, String> cache, int interval) {
		this.cache = cache;
		this.interval = interval;
	}

	@Override
	public void run() {
		Iterator<Entry<String, String>> iter = cache.entrySet().iterator();
		long now = new Date().getTime();
		while(iter.hasNext()){
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			long valueTimestamp = Long.parseLong(value.substring(value.length()-13));
			if(valueTimestamp < now-interval*1000){
				cache.remove(key);
			}
		}
	}

}
