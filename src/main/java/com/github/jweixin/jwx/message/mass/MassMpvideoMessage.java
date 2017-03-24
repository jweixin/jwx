package com.github.jweixin.jwx.message.mass;

import java.util.List;

/**
 * 群发视频消息
 * @author Administrator
 *
 */
public class MassMpvideoMessage extends MassMessage {
	/**
	 * 视频
	 */
	private Mpvideo mpvideo;
	
	public MassMpvideoMessage() {
		super();
		this.msgtype = "mpvideo";
	}
	
	public MassMpvideoMessage(Filter filter) {
		super(filter);
		this.msgtype = "mpvideo";
	}
	
	public MassMpvideoMessage(Filter filter, String mediaId) {
		super(filter);
		this.msgtype = "mpvideo";
		this.mpvideo = new Mpvideo(mediaId);
	}
	
	public MassMpvideoMessage(List<String> touser) {
		super(touser);
		this.msgtype = "mpvideo";
	}
	
	public MassMpvideoMessage(List<String> touser, String mediaId) {
		super(touser);
		this.msgtype = "mpvideo";
		this.mpvideo = new Mpvideo(mediaId);
	}

	public Mpvideo getMpvideo() {
		return mpvideo;
	}

	public void setMpvideo(Mpvideo mpvideo) {
		this.mpvideo = mpvideo;
	}

}
