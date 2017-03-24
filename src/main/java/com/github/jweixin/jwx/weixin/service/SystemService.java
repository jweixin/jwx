package com.github.jweixin.jwx.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.IpList;
import com.github.jweixin.jwx.weixin.entity.ShortUrlReturnCode;

/**
 * 系统服务
 * @author Administrator
 *
 */
@Service
public class SystemService {
	/**
	 * 长链接转短链接
	 */
	public final static String WEIXIN_SHORTURL_LINK = "https://api.weixin.qq.com/cgi-bin/shorturl";
	/**
	 * 获取微信服务器IP地址链接
	 */
	public final static String WEIXIN_CALLBACK_IP_GET_LINK = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
	
	/**
	 * 将一条长链接转成短链接
	 * @param accessToken
	 * @param longUrl
	 * @return
	 */
	public ShortUrlReturnCode long2short(String accessToken, String longUrl) {
		String url = WEIXIN_SHORTURL_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "long2short");
		map.put("long_url", longUrl);
		return WeixinInterfaceHelper.post(url, map, ShortUrlReturnCode.class);
	}
	
	/**
	 * 获取微信服务器IP地址列表
	 * @param accessToken
	 * @return
	 */
	public List<String> getWeixinIpList(String accessToken) {
		String url = WEIXIN_CALLBACK_IP_GET_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, IpList.class).getIpList();
	}

}
