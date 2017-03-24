package com.github.jweixin.jwx.weixin.entity.material;

import java.util.List;

/**
 * 永久图文消息素材列表
 * 
 * @author Administrator
 *
 */
public class NewsMaterialList {
	// 该类型的素材的总数
	private int totalCount;
	// 本次调用获取的素材的数量
	private int itemCount;
	// 素材项列表
	private List<NewsMaterialListItem> item;

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

	public List<NewsMaterialListItem> getItem() {
		return item;
	}

	public void setItem(List<NewsMaterialListItem> item) {
		this.item = item;
	}

}
