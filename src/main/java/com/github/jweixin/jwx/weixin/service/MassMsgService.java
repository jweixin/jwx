package com.github.jweixin.jwx.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jweixin.jwx.message.mass.Filter;
import com.github.jweixin.jwx.message.mass.MassMessage;
import com.github.jweixin.jwx.message.mass.MassReturnCode;
import com.github.jweixin.jwx.message.mass.MsgStatus;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.material.Media;
import com.github.jweixin.jwx.weixin.entity.material.NewsArticle;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 消息群发服务
 * @author Administrator
 *
 */
public class MassMsgService {
	
	/**
	 * 上传图文消息素材链接
	 */
	public final static String WEIXIN_MASS_NEWS_MEDIA_UPLOAD_LINK = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	/**
	 * 根据标签进行群发链接
	 */
	public final static String WEIXIN_MASS_MESSAGE_SEND_ALL_LINK = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	/**
	 * 根据OpenID列表群发链接
	 */
	public final static String WEIXIN_MASS_MESSAGE_SEND_LINK = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
	/**
	 * 删除群发链接
	 */
	public final static String WEIXIN_MASS_MESSAGE_DELETE_LINK = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
	/**
	 * 查询群发消息发送状态链接
	 */
	public final static String WEIXIN_MASS_MESSAGE_GET_LINK = "https://api.weixin.qq.com/cgi-bin/message/mass/get";
	/**
	 * 群发消息预览接口链接
	 */
	public final static String WEIXIN_MASS_MESSAGE_PREVIEW_LINK = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";
	
	/**
	 * 上传图文消息素材
	 * 订阅号与服务号认证后均可用
	 * @param accessToken
	 * @param articles
	 * @return
	 */
	public Media uploadNews(String accessToken, List<NewsArticle> articles) {
		if(articles==null || articles.size()<1){
			return null;
		}
		if(articles.size()>8){
			articles = articles.subList(0, 8);
		}
		String url = WEIXIN_MASS_NEWS_MEDIA_UPLOAD_LINK + "?access_token=" + accessToken;
		Map<String, List<NewsArticle>> map = new HashMap<String, List<NewsArticle>>();
		map.put("articles", articles);
		return WeixinInterfaceHelper.post(url, map, Media.class);
	}
	
	/**
	 * 消息群发
	 * @param accessToken
	 * @param message
	 * @return
	 */
	public MassReturnCode send(String accessToken, MassMessage message) {
		Filter filter = message.getFilter();
		List<String> touser = message.getTouser();
		if((filter==null && touser==null) || (filter!=null && touser!=null)){
			throw new IllegalArgumentException("群发消息参数不符合要求,Filter和Touser必须有且仅有一个。");
		}
		if(touser!=null && touser.size()<2){
			throw new IllegalArgumentException("群发消息参数不符合要求,touser列表中的OPENID不能少于2个。");
		}
		String url = null;
		if(filter!=null){
			url = WEIXIN_MASS_MESSAGE_SEND_ALL_LINK + "?access_token=" + accessToken;
		}
		if(touser!=null){
			url = WEIXIN_MASS_MESSAGE_SEND_LINK + "?access_token=" + accessToken;
		}
		return WeixinInterfaceHelper.post(url, message, MassReturnCode.class);
	}
	
	/**
	 * 删除群发
	 * 订阅号与服务号认证后均可用
	 * @param accessToken
	 * @param msgId 发送出去的消息ID
	 * @return
	 */
	public ReturnCode delete(String accessToken, long msgId) {
		String url = WEIXIN_MASS_MESSAGE_DELETE_LINK + "?access_token=" + accessToken;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("msg_id", msgId);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 查询群发消息发送状态
	 * 订阅号与服务号认证后均可用
	 * @param accessToken
	 * @param msgId
	 * @return
	 */
	public MsgStatus status(String accessToken, long msgId) {
		String url = WEIXIN_MASS_MESSAGE_GET_LINK + "?access_token=" + accessToken;
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("msg_id", msgId);
		return WeixinInterfaceHelper.post(url, map, MsgStatus.class);
	}
	
	/**
	 * 群发消息预览
	 * @param accessToken
	 * @param message
	 * @param touser 公众号的openid
	 * @param towxname 微信号
	 * @return
	 */
	public MassReturnCode preview(String accessToken, MassMessage message, String touser, String towxname) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		String msgText = gson.toJson(message);
		@SuppressWarnings("unchecked")
		Map<String, Object> msgMap = gson.fromJson(msgText, HashMap.class);
		
		if(msgMap.get("filter")!=null){
			msgMap.remove("filter");
		}
		if(msgMap.get("touser")!=null){
			msgMap.remove("touser");
		}
		if(msgMap.get("send_ignore_reprint")!=null){
			msgMap.remove("send_ignore_reprint");
		}
		if(towxname!=null){
			msgMap.put("towxname", towxname);
		}else if(touser!=null){
			msgMap.put("touser", touser);
		}else{
			throw new IllegalArgumentException("群发消息预览参数不符合要求,touser和towxname不能都为空。");
		}
		
		String url = WEIXIN_MASS_MESSAGE_PREVIEW_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, msgMap, MassReturnCode.class);
	}

}
