package com.github.jweixin.jwx.message.request.event;

/**
 * 扫码推事件的事件推送
 * 
 * @author Administrator
 *
 */
public class InScancodePushEvent extends InMenuEvent {
	// 事件类型，scancode_push
	final private String event = "scancode_push";
	// 扫描信息
	private ScanCodeInfo scanCodeInfo;

	public ScanCodeInfo getScanCodeInfo() {
		return scanCodeInfo;
	}

	public void setScanCodeInfo(ScanCodeInfo scanCodeInfo) {
		this.scanCodeInfo = scanCodeInfo;
	}

	public String getEvent() {
		return event;
	}

}
