package com.github.jweixin.jwx.context;

import java.util.List;

/**
 * 微信参数设置接口
 * @author Administrator
 *
 */
public interface WeixinParameterConfig {
	
	/**
	 * 获取微信基本设置配置项
	 * @return 微信上下文基本配置项列表
	 */
	public List<WeixinSetting> getWeixinSettings();

}
