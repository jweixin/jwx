package com.github.jweixin.jwx.message.request.event;

/**
 * 扫码推事件且弹出“消息接收中”提示框的事件推送
 * 
 * @author Administrator
 *
 */
public class InScancodeWaitmsgEvent extends InMenuEvent {
	// 事件类型，scancode_waitmsg
	final private String event = "scancode_waitmsg";
	// 扫描类型，一般是qrcode
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
