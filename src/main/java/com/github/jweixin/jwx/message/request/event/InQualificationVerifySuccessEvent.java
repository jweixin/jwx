package com.github.jweixin.jwx.message.request.event;

/**
 * 资质认证成功事件
 * @author Administrator
 *
 */
public class InQualificationVerifySuccessEvent extends InEvent {
	/**
	 * 事件类型，qualification_verify_success
	 */
	final private String event = "qualification_verify_success";
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
