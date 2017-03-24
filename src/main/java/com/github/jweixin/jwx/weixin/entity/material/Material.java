package com.github.jweixin.jwx.weixin.entity.material;

/**
 * 微信永久素材
 * 
 * @author Administrator
 *
 */
public class Material {
	// 永久素材的media_id
	private String mediaId;
	// 图片素材的图片URL（仅新增图片素材时会返回该字段）
	private String url;
	//文件名称
	private String name;
	//素材的最后更新时间
	private String updateTime;

	/**
	 * 检查字节流是否合法的素材
	 * 
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
	 * 
	 * @param size
	 *            素材长度
	 * @param filename
	 * @param type
	 * @return
	 */
	public static boolean check(long size, String filename, MediaType type) {
		boolean flag = true;
		if (filename == null) {
			flag = false;
		} else {
			// 文件扩展名
			String ext = filename.indexOf(".") != -1
					? filename.substring(filename.lastIndexOf(".") + 1, filename.length()) : null;
			if (ext == null) {
				flag = false;
			} else {
				ext = ext.toUpperCase();
				if (ext.equals("BMP") || ext.equals("PNG") || ext.equals("JPEG") || ext.equals("JPG")
						|| ext.equals("GIF") || ext.equals("AMR") || ext.equals("MP3") || ext.equals("WMA")
						|| ext.equals("WAV") || ext.equals("MP4")) {
					if (ext.equals("MP4")) {
						if (size > 10000000) {
							flag = false;
						}
					} else if (ext.equals("BMP") || ext.equals("PNG") || ext.equals("JPEG") || ext.equals("GIF")
							|| ext.equals("AMR") || ext.equals("MP3") || ext.equals("WMA") || ext.equals("WAV")) {
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

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
