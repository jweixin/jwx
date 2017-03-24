package com.github.jweixin.jwx.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.user.UserInfo;
import com.github.jweixin.jwx.weixin.entity.user.UserInfoList;
import com.github.jweixin.jwx.weixin.entity.user.UserLabelList;
import com.github.jweixin.jwx.weixin.entity.user.UserList;

/**
 * 微信用户服务
 * @author Administrator
 *
 */
@Service
public class UserService {
	/**
	 * 设置用户备注名链接
	 */
	public final static String WEIXIN_USER_INFO_REMARK_LINK = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
	/**
	 * 获取用户基本信息链接
	 */
	public final static String WEIXIN_USER_INFO_GET_LINK = "https://api.weixin.qq.com/cgi-bin/user/info";
	/**
	 * 批量获取用户基本信息链接
	 */
	public final static String WEIXIN_USER_INFO_BATCH_GET_LINK = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
	/**
	 * 获取用户列表链接
	 */
	public final static String WEIXIN_USER_LIST_LINK = "https://api.weixin.qq.com/cgi-bin/user/get";
	/**
	 * 获取公众号的黑名单列表链接
	 */
	public final static String WEIXIN_BLACK_LIST_LINK = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist";
	/**
	 * 拉黑用户链接
	 */
	public final static String WEIXIN_BLACK_BATCH_LINK = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist";
	/**
	 * 取消拉黑用户链接
	 */
	public final static String WEIXIN_UNBLACK_BATCH_LINK = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist";
	
	/**
	 * 设置用户备注名
	 * @param accessToken
	 * @param openid
	 * @param remark
	 * @return
	 */
	public ReturnCode remarkUser(String accessToken, String openid, String remark) {
		String url = WEIXIN_USER_INFO_REMARK_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("remark", remark);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * @param accessToken
	 * @param openid
	 * @param lang
	 * @return
	 */
	public UserInfo getUserInfo(String accessToken, String openid, String lang) {
		String url = WEIXIN_USER_INFO_GET_LINK + "?access_token=" + accessToken + "&openid=" + openid;
		if(lang != null){
			url = url + "&lang=" + lang;
		}
		return WeixinInterfaceHelper.get(url, UserInfo.class);
	}
	
	/**
	 * 批量获取用户基本信息
	 * @param accessToken
	 * @param userList
	 * @return
	 */
	public List<UserInfo> batchGetUserInfo(String accessToken, UserLabelList userList) {
		String url = WEIXIN_USER_INFO_GET_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, userList, UserInfoList.class).getUserInfoList();
	}
	
	/**
	 * 获取用户列表
	 * @param accessToken
	 * @param nextOpenid
	 * @return
	 */
	public UserList getUserList(String accessToken, String nextOpenid) {
		String url = WEIXIN_USER_LIST_LINK + "?access_token=" + accessToken;
		if(nextOpenid != null){
			url = url + "&next_openid=" + nextOpenid;
		}
		return WeixinInterfaceHelper.get(url, UserList.class);
	}
	
	/**
	 * 获取公众号的黑名单列表
	 * @param accessToken
	 * @param beginOpenid
	 * @return
	 */
	public UserList getBlackList(String accessToken, String beginOpenid) {
		String url = WEIXIN_BLACK_LIST_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		if(beginOpenid == null){
			map.put("begin_openid", "");
		}else{
			map.put("begin_openid", beginOpenid);
		}
		return WeixinInterfaceHelper.post(url, map, UserList.class);
	}
	
	/**
	 * 批量拉黑用户
	 * @param accessToken
	 * @param openedList
	 * @return
	 */
	public ReturnCode blackUser(String accessToken, List<String> openedList) {
		if(openedList==null || openedList.size()<1){
			throw new IllegalArgumentException("拉黑的用户列表不能为空");
		}
		String url = WEIXIN_BLACK_BATCH_LINK + "?access_token=" + accessToken;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("opened_list", openedList);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 拉黑用户
	 * @param accessToken
	 * @param opened
	 * @return
	 */
	public ReturnCode blackUser(String accessToken, String opened) {
		List<String> openedList = new ArrayList<String>();
		openedList.add(opened);
		return blackUser(accessToken, openedList);
	}
	
	/**
	 * 批量取消拉黑用户
	 * @param accessToken
	 * @param openedList
	 * @return
	 */
	public ReturnCode unblackUser(String accessToken, List<String> openedList) {
		if(openedList==null || openedList.size()<1){
			throw new IllegalArgumentException("取消拉黑的用户列表不能为空");
		}
		String url = WEIXIN_UNBLACK_BATCH_LINK + "?access_token=" + accessToken;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("opened_list", openedList);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 取消拉黑用户
	 * @param accessToken
	 * @param opened
	 * @return
	 */
	public ReturnCode unblackUser(String accessToken, String opened) {
		List<String> openedList = new ArrayList<String>();
		openedList.add(opened);
		return unblackUser(accessToken, openedList);
	}

}
