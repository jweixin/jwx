package com.github.jweixin.jwx.message.response;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复语音消息
 * @author Administrator
 *
 */
public class OutVoiceMessage extends OutMessage {
	
	private Voice voice;
	
	public OutVoiceMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "voice";
	}
	
	public OutVoiceMessage(InMessage inMessage, Voice voice) {
		super(inMessage);
		this.msgType = "voice";
		this.voice = voice;
	}
	
	public OutVoiceMessage() {
		super();
		this.msgType = "voice";
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public String getMsgType() {
		return msgType;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (voice == null || null == voice.getMediaId()) {
			throw new NullPointerException("响应的语音消息mediaId不能为空");
		}
		sb.append("<Voice>\n");
		sb.append("<MediaId><![CDATA[").append(voice.getMediaId()).append("]]></MediaId>\n");
		sb.append("</Voice>\n");
	}

}
