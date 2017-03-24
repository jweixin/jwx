package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 场景动作信息
 * @author Administrator
 *
 */
public class ActionInfo {
	/**
	 * 场景
	 */
	private Scene scene;
	
	public ActionInfo() {
	}
	
	public ActionInfo(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

}
