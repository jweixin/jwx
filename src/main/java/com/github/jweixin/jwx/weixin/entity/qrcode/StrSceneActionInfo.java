package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 字符串形式二维码详细信息
 * @author Administrator
 *
 */
public class StrSceneActionInfo {
	/**
	 * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 */
	private StrScene scene;
	
	public StrSceneActionInfo() {
	}
	
	public StrSceneActionInfo(StrScene scene) {
		this.scene = scene;
	}

	public StrScene getScene() {
		return scene;
	}

	public void setScene(StrScene scene) {
		this.scene = scene;
	}
}
