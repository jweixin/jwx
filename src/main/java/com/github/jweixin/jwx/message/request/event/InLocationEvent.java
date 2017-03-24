package com.github.jweixin.jwx.message.request.event;

/**
 * 上报地理位置事件
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。
 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
 * 
 * @author Administrator
 */
public class InLocationEvent extends InEvent {
	// 事件类型，LOCATION
	final private String event = "LOCATION";
	// 地理位置纬度
	private String latitude;
	// 地理位置经度
	private String longitude;
	// 地理位置精度
	private String precision;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getEvent() {
		return event;
	}

}
