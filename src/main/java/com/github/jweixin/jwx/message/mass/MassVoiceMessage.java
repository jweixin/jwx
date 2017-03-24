package com.github.jweixin.jwx.message.mass;

import java.util.List;

import com.github.jweixin.jwx.message.response.Voice;

/**
 * 群发语音消息
 * @author Administrator
 *
 */
public class MassVoiceMessage extends MassMessage {
	/**
	 * 语音
	 */
	private Voice voice;
	
	public MassVoiceMessage() {
		super();
		this.msgtype = "voice";
	}
	
	public MassVoiceMessage(Filter filter) {
		super(filter);
		this.msgtype = "voice";
	}
	
	public MassVoiceMessage(Filter filter, String mediaId) {
		super(filter);
		this.msgtype = "voice";
		this.voice = new Voice(mediaId);
	}
	
	public MassVoiceMessage(List<String> touser) {
		super(touser);
		this.msgtype = "voice";
	}
	
	public MassVoiceMessage(List<String> touser, String mediaId) {
		super(touser);
		this.msgtype = "voice";
		this.voice = new Voice(mediaId);
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
