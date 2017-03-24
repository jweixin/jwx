package com.github.jweixin.jwx.message.request.event;

import java.util.List;

/**
 * 发送的图片信息
 * 
 * @author Administrator
 *
 */
public class SendPicsInfo {
	// 发送的图片数量
	private int count;
	// 图片列表
	private List<PicListItem> picList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<PicListItem> getPicList() {
		return picList;
	}

	public void setPicList(List<PicListItem> picList) {
		this.picList = picList;
	}

}
