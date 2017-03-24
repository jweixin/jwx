package com.github.jweixin.jwx.weixin.entity.user;

import java.util.List;

/**
 * 用户标记列表
 * @author Administrator
 *
 */
public class UserLabelList {
	/**
	 * 用户列表
	 */
	private List<UserLabel> userList;

	public List<UserLabel> getUserList() {
		return userList;
	}

	public void setUserList(List<UserLabel> userList) {
		this.userList = userList;
	}

}
