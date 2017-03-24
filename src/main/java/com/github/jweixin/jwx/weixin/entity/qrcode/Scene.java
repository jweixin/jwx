package com.github.jweixin.jwx.weixin.entity.qrcode;

/**
 * 场景
 * @author Administrator
 *
 */
public class Scene {
	/**
	 * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 */
	private int sceneId;
	
	public Scene() {
	}
	
	public Scene(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

}
