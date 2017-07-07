package com.github.jweixin.jwx.context;

import java.util.List;

/**
 * 基于spring的xml格式的微信基本配置项设置
 * @author Administrator
 *
 */
public class XmlWeixinParameterConfig implements WeixinParameterConfig {
	/**
	 * 微信公众号设置列表
	 */
	private List<WeixinSetting> weixinSettings;

	@Override
	public List<WeixinSetting> getWeixinSettings() {
		return weixinSettings;
	}

	public void setWeixinSettings(List<WeixinSetting> weixinSettings) {
		this.weixinSettings = weixinSettings;
	}

}
