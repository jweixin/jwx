package com.github.jweixin.jwx.message.custom;

import java.util.List;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Article;

public class CustomNewsMessage extends CustomMessage {
	
	private News news;
	
	public CustomNewsMessage() {
		super();
		this.msgtype = "news";
	}

	public CustomNewsMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "news";
	}

	public CustomNewsMessage(InMessage inMessage, News news) {
		super(inMessage);
		this.msgtype = "news";
		this.news = news;
	}
	
	public CustomNewsMessage(InMessage inMessage, List<Article> articles) {
		super(inMessage);
		this.msgtype = "news";
		this.news = new News(articles);
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}
