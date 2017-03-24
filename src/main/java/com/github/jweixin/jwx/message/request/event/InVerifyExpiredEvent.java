package com.github.jweixin.jwx.message.request.event;

/**
 * 认证过期失效通知事件
 * @author Administrator
 *
 */
public class InVerifyExpiredEvent extends InEvent {
	/**
	 * 事件类型，verify_expired
	 */
	final private String event = "verify_expired";
	/**
	 * 有效期 (整形)，指的是时间戳，表示已于该时间戳认证过期，需要重新发起微信认证
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
