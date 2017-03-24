package com.github.jweixin.jwx.weixin.entity.material;

import java.util.List;

/**
 * 素材列表 
 * 图片、语音、视频素材列表提取
 * 
 * @author Administrator
 *
 */
public class MaterialList {
	// 该类型的素材的总数
	private int totalCount;
	// 本次调用获取的素材的数量
	private int itemCount;
	// 素材项列表
	private List<Material> item;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public List<Material> getItem() {
		return item;
	}

	public void setItem(List<Material> item) {
		this.item = item;
	}

}
