package com.github.jweixin.jwx.message.request.event;

/**
 * 名称认证成功（即命名成功）事件
 * @author Administrator
 *
 */
public class InNamingVerifySuccessEvent extends InEvent {
	/**
	 * 事件类型，naming_verify_success
	 */
	final private String event = "naming_verify_success";
	/**
	 * 有效期 (整形)，指的是时间戳，将于该时间戳认证过期
	 */
	private long expiredTime;

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getEvent() {
		return event;
	}

}
