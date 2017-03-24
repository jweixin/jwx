package com.github.jweixin.jwx.message.request;

public class InVoiceRecognitionMessage extends InVoiceMessage {
	// 语音识别结果，UTF8编码
	private String recognition;

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
