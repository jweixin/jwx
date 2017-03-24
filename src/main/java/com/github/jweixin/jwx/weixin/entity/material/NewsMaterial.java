package com.github.jweixin.jwx.weixin.entity.material;

import java.util.List;

/**
 * 永久图文获取素材
 * @author Administrator
 *
 */
public class NewsMaterial {
	// 获取图片素材列表
	private List<NewsArticle> newsItem;

	public List<NewsArticle> getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(List<NewsArticle> newsItem) {
		this.newsItem = newsItem;
	}

}
