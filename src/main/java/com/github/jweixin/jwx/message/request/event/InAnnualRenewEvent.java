package com.github.jweixin.jwx.message.request.event;

/**
 * 年审通知事件
 * @author Administrator
 *
 */
public class InAnnualRenewEvent extends InEvent {
	/**
	 * 事件类型，annual_renew
	 */
	final private String event = "annual_renew";
	/**
	 * 有效期 (整形)，指的是时间戳，将于该时间戳认证过期，需尽快年审
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
