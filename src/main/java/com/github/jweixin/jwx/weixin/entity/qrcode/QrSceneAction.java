package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 临时二维码场景动作
 * 
 * @author Administrator
 *
 */
public class QrSceneAction {
	/**
	 * 二维码类型，QR_SCENE为临时
	 */
	private final String actionName = "QR_SCENE";
	/**
	 * 二维码详细信息
	 */
	private ActionInfo actionInfo;
	/**
	 * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 */
	private Integer expireSeconds;

	public ActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(ActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	public Integer getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getActionName() {
		return actionName;
	}

}
