package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 图文消息
 * 跳转图文消息URL用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。
 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 * @author Administrator
 *
 */
public class ViewLimitedButton extends Button {
	/**
	 * 调用新增永久素材接口返回的合法media_id
	 */
	private String mediaId;

	public ViewLimitedButton(String name) {
		super(name);
		this.type = "view_limited";
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
