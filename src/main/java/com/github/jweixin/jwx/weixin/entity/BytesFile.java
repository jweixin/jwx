package com.github.jweixin.jwx.weixin.entity;

/**
 * 字节数组文件
 * 
 * @author Administrator
 *
 */
public class BytesFile {
	/**
	 * 文件字节数组
	 */
	private byte[] bytes;
	/**
	 * 文件名
	 */
	private String filename;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
