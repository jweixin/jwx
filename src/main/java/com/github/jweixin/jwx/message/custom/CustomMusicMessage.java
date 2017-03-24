package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Music;

public class CustomMusicMessage extends CustomMessage {
	
	private Music music;
	
	public CustomMusicMessage() {
		super();
		this.msgtype = "music";
	}

	public CustomMusicMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "music";
	}

	public CustomMusicMessage(InMessage inMessage, Music music) {
		super(inMessage);
		this.msgtype = "music";
		this.music = music;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

}
