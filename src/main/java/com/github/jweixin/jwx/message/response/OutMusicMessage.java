package com.github.jweixin.jwx.message.response;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复音乐消息
 * @author Administrator
 *
 */
public class OutMusicMessage extends OutMessage {
	// 音乐
	private Music music;
	
	public OutMusicMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "music";
	}
	
	public OutMusicMessage(InMessage inMessage, Music music) {
		super(inMessage);
		this.msgType = "music";
		this.music = music;
	}
	
	public OutMusicMessage() {
		super();
		this.msgType = "music";
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public String getMsgType() {
		return msgType;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (music == null || music.getThumbMediaId() == null) {
			throw new NullPointerException("响应的音乐消息thumbMediaId不能为空");
		}
		sb.append("<Music>\n");
		sb.append("<Title><![CDATA[").append(nullToBlank(music.getTitle())).append("]]></Title>\n");
		sb.append("<Description><![CDATA[").append(nullToBlank(music.getDescription())).append("]]></Description>\n");
		sb.append("<MusicUrl><![CDATA[").append(nullToBlank(music.getMusicurl())).append("]]></MusicUrl>\n");
		sb.append("<HQMusicUrl><![CDATA[").append(nullToBlank(music.getHqmusicurl())).append("]]></HQMusicUrl>\n");
		sb.append("<ThumbMediaId><![CDATA[").append(music.getThumbMediaId()).append("]]></ThumbMediaId>\n");
		sb.append("</Music>\n");
	}

}
