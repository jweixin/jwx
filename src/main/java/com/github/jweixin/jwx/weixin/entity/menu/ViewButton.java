package com.github.jweixin.jwx.weixin.entity.menu;

public class ViewButton extends Button {
	/**
	 * 网页链接，用户点击菜单可打开链接，不超过1024字节
	 */
	private String url;

	public ViewButton(String name) {
		super(name);
		this.type = "view";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
