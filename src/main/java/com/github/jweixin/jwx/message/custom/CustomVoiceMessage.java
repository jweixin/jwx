package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Voice;

public class CustomVoiceMessage extends CustomMessage {
	
	private Voice voice;
	
	public CustomVoiceMessage() {
		super();
		this.msgtype = "voice";
	}

	public CustomVoiceMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "voice";
	}

	public CustomVoiceMessage(InMessage inMessage, Voice voice) {
		super(inMessage);
		this.msgtype = "voice";
		this.voice = voice;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
