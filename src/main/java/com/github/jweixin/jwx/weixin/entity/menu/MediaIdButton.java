package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 图片
 * 下发消息（除文本消息）用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，
 * 永久素材类型可以是图片、音频、视频、图文消息。
 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 * @author Administrator
 *
 */
public class MediaIdButton extends Button {
	/**
	 * 调用新增永久素材接口返回的合法media_id
	 */
	private String mediaId;

	public MediaIdButton(String name) {
		super(name);
		this.type = "media_id";
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
