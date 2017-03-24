package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 字符串场景
 * 仅支持永久二维码
 * @author Administrator
 *
 */
public class StrScene {
	/**
	 * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
	 */
	private String sceneStr;
	
	public StrScene() {
	}
	
	public StrScene(String sceneStr) {
		this.sceneStr = sceneStr;
	}

	public String getSceneStr() {
		return sceneStr;
	}

	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}

}
