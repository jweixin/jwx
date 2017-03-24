package com.github.jweixin.jwx.message.request.event;

/**
 * 扫描信息
 * 
 * @author Administrator
 *
 */
public class ScanCodeInfo {
	// 扫描类型，一般是qrcode
	private String scanType;
	// 扫描结果，即二维码对应的字符串信息
	private String scanResult;

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}

}
