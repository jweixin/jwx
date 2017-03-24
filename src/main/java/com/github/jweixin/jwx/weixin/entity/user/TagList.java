package com.github.jweixin.jwx.weixin.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * 公众号标签列表
 * @author Administrator
 *
 */
public class TagList {
	
	private List<Tag> tags = new ArrayList<Tag>();

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
