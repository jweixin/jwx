package com.github.jweixin.jwx.message.response;

import java.util.Iterator;
import java.util.List;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复图文消息
 * 
 * @author Administrator
 *
 */
public class OutArticleMessage extends OutMessage {
	// 图文消息个数，限制为8条以内;多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
	private List<Article> articles;

	public OutArticleMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "news";
	}

	public OutArticleMessage() {
		super();
		this.msgType = "news";
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String getMsgType() {
		return msgType;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (articles == null || articles.size() == 0) {
			throw new NullPointerException("响应的图文消息不能为空");
		}
		int size = articles.size() > 8 ? 8 : articles.size();
		sb.append("<ArticleCount>").append(size).append("</ArticleCount>\n");
		sb.append("<Articles>\n");
		Iterator<Article> iter = articles.iterator();
		int i = 0;
		while (iter.hasNext()) {
			Article article = iter.next();
			if (i < size) {
				sb.append("<item>\n");
				sb.append("<Title><![CDATA[").append(nullToBlank(article.getTitle())).append("]]></Title>\n");
				sb.append("<Description><![CDATA[").append(nullToBlank(article.getDescription())).append("]]></Description>\n");
				sb.append("<PicUrl><![CDATA[").append(nullToBlank(article.getPicurl())).append("]]></PicUrl>\n");
				sb.append("<Url><![CDATA[").append(nullToBlank(article.getUrl())).append("]]></Url>\n");
				sb.append("</item>\n");
			} else {
				break;
			}
			i++;
		}
		sb.append("</Articles>\n");
	}

}
