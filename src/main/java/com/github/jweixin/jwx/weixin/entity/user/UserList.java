package com.github.jweixin.jwx.weixin.entity.user;

/**
 * 用户列表
 * 
 * @author Administrator
 *
 */
public class UserList {
	/**
	 * 关注该公众账号的总用户数
	 */
	private int total;
	/**
	 * 拉取的OPENID个数，最大值为10000
	 */
	private int count;
	/**
	 * 列表数据，OPENID的列表
	 */
	private OpenIdList data;
	/**
	 * 拉取列表的最后一个用户的OPENID
	 */
	private String nextOpenid;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

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
