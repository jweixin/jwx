package com.github.jweixin.jwx.weixin.entity.custom;

/**
 * 客服信息
 * 
 * @author Administrator
 *
 */
public class Kf {
	/**
	 * 完整客服账号，格式为：账号前缀@公众号微信号
	 */
	private String kfAccount;
	/**
	 * 客服昵称
	 */
	private String kfNick;
	/**
	 * 客服工号
	 */
	private String kfId;
	/**
	 * 客服头像url链接
	 */
	private String kfHeadimgurl;

	public String getKfAccount() {
		return kfAccount;
	}

	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}

	public String getKfNick() {
		return kfNick;
	}

	public void setKfNick(String kfNick) {
		this.kfNick = kfNick;
	}

	public String getKfId() {
		return kfId;
	}

	public void setKfId(String kfId) {
		this.kfId = kfId;
	}

	public String getKfHeadimgurl() {
		return kfHeadimgurl;
	}

	public void setKfHeadimgurl(String kfHeadimgurl) {
		this.kfHeadimgurl = kfHeadimgurl;
	}

}
