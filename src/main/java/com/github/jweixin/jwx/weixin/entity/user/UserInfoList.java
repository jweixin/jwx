package com.github.jweixin.jwx.weixin.entity.user;

import java.util.List;

/**
 * 用户信息列表
 * @author Administrator
 *
 */
public class UserInfoList {
	/**
	 * 用户信息列表
	 */
	private List<UserInfo> userInfoList;

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

}
