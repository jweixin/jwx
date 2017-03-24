package com.github.jweixin.jwx.message.request;

public class InVoiceMessage extends InPlainMessage {
	// 消息类型
	protected final String msgType = "voice";
	// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	protected String mediaId;
	// 语音格式，如amr，speex等
	protected String format;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMsgType() {
		return msgType;
	}

}
