package com.github.jweixin.jwx.weixin.entity.material;

import java.io.File;

/**
 * 微信临时素材
 * 
 * @author Administrator
 * @version 1.0 2017-2-6
 */
public class Media {
	/**
	 * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息（news）
	 */
	private String type;
	/**
	 * 媒体文件/图文消息上传后获取的唯一标识
	 */
	private String mediaId;
	/**
	 * 缩略图上传后，获取标识
	 */
	private String thumbMediaId;
	/**
	 * 媒体文件上传时间
	 */
	private long createdAt;
	
	/**
	 * 检查素材文件是否合法
	 * @param file
	 * @param type
	 * @return
	 */
	public static boolean check(File file, MediaType type) {
		if(file!=null && file.exists() && file.isFile()){
			return check(file.length(), file.getName(), type);
		}
		return false;
	}
	
	/**
	 * 检查字节流是否合法的素材
	 * @param bytes
	 * @param filename
	 * @param type
	 * @return
	 */
	public static boolean check(byte[] bytes, String filename, MediaType type) {
		return check(bytes.length, filename, type);
	}
	
	/**
	 * 检查素材长度是否合法
	 * @param size 素材长度
	 * @param filename
	 * @param type
	 * @return
	 */
	public static boolean check(long size, String filename, MediaType type){
		boolean flag = true;
		if(filename == null){
			flag = false;
		} else{
			// 文件扩展名
			String ext = filename.indexOf(".") != -1 ? filename.substring(filename.lastIndexOf(".") + 1, filename.length()) : null;
			if (ext == null) {
				flag = false;
			} else {
				ext = ext.toUpperCase();
				if (ext.equals("PNG") || ext.equals("JPEG") || ext.equals("JPG") || ext.equals("GIF") || ext.equals("AMR")
						|| ext.equals("MP3") || ext.equals("MP4")) {
					if (ext.equals("MP4")) {
						if (size > 10000000) {
							flag = false;
						}
					} else if (ext.equals("PNG") || ext.equals("JPEG") || ext.equals("GIF") || ext.equals("AMR")
							|| ext.equals("MP3")) {
						if (size > 2000000) {
							flag = false;
						}
					} else if (ext.equals("JPG")) {
						if (MediaType.THUMB.equals(type)) {
							if (size > 64000) {
								flag = false;
							}
						} else {
							if (size > 2000000) {
								flag = false;
							}
						}
					}
				} else {
					flag = false;
				}
			}
		}
		
		return flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

}
