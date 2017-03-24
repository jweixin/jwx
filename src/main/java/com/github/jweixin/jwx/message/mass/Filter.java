package com.github.jweixin.jwx.message.mass;

/**
 * 消息群发过滤器
 * 用于设定图文消息的接收者
 * @author Administrator
 *
 */
public class Filter {
	/**
	 * 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户， 选择false可根据tag_id发送给指定群组的用户
	 */
	private boolean isToAll;
	/**
	 * 群发到的标签的tag_id，参加用户管理中用户分组接口， 若is_to_all值为true，可不填写tag_id
	 */
	private Integer tagId;
	
	public Filter() {
	}
	
	public Filter(Integer tagId) {
		this.isToAll = false;
		this.tagId = tagId;
	}

	public boolean isToAll() {
		return isToAll;
	}

	public void setToAll(boolean isToAll) {
		this.isToAll = isToAll;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}
