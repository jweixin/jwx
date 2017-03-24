package com.github.jweixin.jwx.message.request.event;

/**
 * 事件推送群发结果
 * 
 * @author Administrator
 *
 */
public class InMassSendJobFinishEvent extends InEvent {
	// 事件类型，MASSSENDJOBFINISH
	final private String event = "MASSSENDJOBFINISH";
	//群发的消息ID
	private long msgId;
	//群发的结构，为“send success”或“send fail”或“err(num)”
	private String status;
	//tag_id下粉丝数；或者openid_list中的粉丝数
	private int totalCount;
	//过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount
	private int filterCount;
	//发送成功的粉丝数
	private int sentCount;
	//发送失败的粉丝数
	private int errorCount;
	//版权检查结果
	private CopyrightCheckResult copyrightCheckResult;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public CopyrightCheckResult getCopyrightCheckResult() {
		return copyrightCheckResult;
	}

	public void setCopyrightCheckResult(CopyrightCheckResult copyrightCheckResult) {
		this.copyrightCheckResult = copyrightCheckResult;
	}

	public String getEvent() {
		return event;
	}
}
