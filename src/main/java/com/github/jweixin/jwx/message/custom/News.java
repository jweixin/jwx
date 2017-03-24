package com.github.jweixin.jwx.message.custom;

import java.util.List;

import com.github.jweixin.jwx.message.response.Article;

public class News {

	private List<Article> articles;

	public News() {
	}

	public News(List<Article> articles) {
		this.articles = articles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
