package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 永久二维码字符串形式场景动作
 * @author Administrator
 *
 */
public class QrLimitStrSceneAction {
	/**
	 * 二维码类型，QR_LIMIT_STR_SCENE为永久的字符串参数值
	 */
	private final String actionName = "QR_LIMIT_STR_SCENE";
	/**
	 * 字符串场景二维码信息
	 */
	private StrSceneActionInfo actionInfo;

	public StrSceneActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(StrSceneActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	public String getActionName() {
		return actionName;
	}

}
