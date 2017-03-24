package com.github.jweixin.jwx.message.request.event;

/**
 * 图片列表
 * @author Administrator
 *
 */
public class PicListItem {
	// 图片的MD5值，开发者若需要，可用于验证接收到图片
	private String picMd5Sum;

	public String getPicMd5Sum() {
		return picMd5Sum;
	}

	public void setPicMd5Sum(String picMd5Sum) {
		this.picMd5Sum = picMd5Sum;
	}

}
