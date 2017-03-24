package com.github.jweixin.jwx.weixin.entity.user;

/**
 * 标签粉丝列表
 * 
 * @author Administrator
 *
 */
public class TagUserList {
	/**
	 * 这次获取的粉丝数量
	 */
	private int count;
	/**
	 * 粉丝列表
	 */
	private OpenIdList data;
	/**
	 * 拉取列表最后一个用户的openid,不填默认从头开始拉取
	 */
	private String nextOpenid;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public OpenIdList getData() {
		return data;
	}

	public void setData(OpenIdList data) {
		this.data = data;
	}

	public String getNextOpenid() {
		return nextOpenid;
	}

	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}

}
