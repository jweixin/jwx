package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 永久二维码场景动作
 * 
 * @author Administrator
 *
 */
public class QrLimitSceneAction {
	/**
	 * 二维码类型，QR_LIMIT_SCENE为永久
	 */
	private final String actionName = "QR_LIMIT_SCENE";
	/**
	 * 二维码详细信息
	 */
	private ActionInfo actionInfo;

	public ActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(ActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	public String getActionName() {
		return actionName;
	}

}
