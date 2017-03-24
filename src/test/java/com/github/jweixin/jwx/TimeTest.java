package com.github.jweixin.jwx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;

public class TimeTest {
	
	@Test
	public void testTimeStamp(){
		long now = new Date().getTime();
		String s = "" + now;
		Assert.assertEquals(s.length(), 13);
		String random = UUID.randomUUID().toString() + "-" + now;
		Assert.assertEquals(now, Long.parseLong(random.substring(random.length()-13)));
	}
	
	@Test
	public void testMapClean() throws InterruptedException{
		ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
		long lastCleanTimestamp = new Date().getTime();
		int cleanInterval = 2;
		cache.put("key1", "foo-" + new Date().getTime());
		Thread.sleep(500);
		cache.put("key2", "foo-" + new Date().getTime());
		Assert.assertEquals(cache.size(), 2);
		Thread.sleep(1700);
		long now = new Date().getTime();
		if((now-lastCleanTimestamp)/1000>=cleanInterval){
			lastCleanTimestamp = now;
			Iterator<Entry<String, String>> iter = cache.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				long valueTimestamp = Long.parseLong(value.substring(value.length()-13));
				if(valueTimestamp < now-cleanInterval*1000){
					cache.remove(key);
				}
			}
		}
		Assert.assertEquals(cache.size(), 1);
	}
	
	@Test
	public void testPutifAbsent(){
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("key1", "value1");
		String v2 = map.putIfAbsent("key2", "value2");
		Assert.assertNull(v2);
		String v1 = map.putIfAbsent("key1", "otherValue");
		Assert.assertEquals(v1, "value1");;
	}
	
	@Test
	public void testTimestamp(){
		int createTime = 1487919375;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		System.out.println(sdf.format(new Date(createTime * 1000L)));
	}

}
