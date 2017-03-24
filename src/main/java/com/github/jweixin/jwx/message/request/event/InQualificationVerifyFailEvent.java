package com.github.jweixin.jwx.message.request.event;

/**
 * 资质认证失败事件
 * 
 * @author Administrator
 *
 */
public class InQualificationVerifyFailEvent extends InEvent {
	/**
	 * 事件类型，qualification_verify_fail
	 */
	final private String event = "qualification_verify_fail";
	/**
	 * 失败发生时间 (整形)，时间戳
	 */
	private long failTime;
	/**
	 * 认证失败的原因
	 */
	private String failReason;

	public long getFailTime() {
		return failTime;
	}

	public void setFailTime(long failTime) {
		this.failTime = failTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getEvent() {
		return event;
	}

}
