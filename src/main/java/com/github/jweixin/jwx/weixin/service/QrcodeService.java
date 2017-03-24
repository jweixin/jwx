package com.github.jweixin.jwx.weixin.service;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.BytesFile;
import com.github.jweixin.jwx.weixin.entity.qrcode.ActionInfo;
import com.github.jweixin.jwx.weixin.entity.qrcode.QrLimitSceneAction;
import com.github.jweixin.jwx.weixin.entity.qrcode.QrLimitStrSceneAction;
import com.github.jweixin.jwx.weixin.entity.qrcode.QrSceneAction;
import com.github.jweixin.jwx.weixin.entity.qrcode.Qrcode;
import com.github.jweixin.jwx.weixin.entity.qrcode.Scene;
import com.github.jweixin.jwx.weixin.entity.qrcode.StrScene;
import com.github.jweixin.jwx.weixin.entity.qrcode.StrSceneActionInfo;

@Service
public class QrcodeService {
	/**
	 * 创建二维码链接
	 */
	public final static String WEIXIN_QRCODE_CREATE_LINK = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	/**
	 * 用ticket换取二维码图片链接
	 */
	public final static String WEIXIN_QRCODE_SHOW_LINK = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	
	/**
	 * 创建临时二维码
	 * @param accessToken
	 * @param action
	 * @return
	 */
	public Qrcode create(String accessToken, QrSceneAction action) {
		String url = WEIXIN_QRCODE_CREATE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, action, Qrcode.class);
	}
	
	/**
	 * 创建永久二维码
	 * @param accessToken
	 * @param action
	 * @return
	 */
	public Qrcode create(String accessToken, QrLimitSceneAction action) {
		String url = WEIXIN_QRCODE_CREATE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, action, Qrcode.class);
	}
	
	/**
	 * 字符串形式的二维码参数创建永久二维码
	 * @param accessToken
	 * @param action
	 * @return
	 */
	public Qrcode create(String accessToken, QrLimitStrSceneAction action) {
		String url = WEIXIN_QRCODE_CREATE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, action, Qrcode.class);
	}
	
	/**
	 * 创建永久二维码
	 * @param accessToken
	 * @param scene 场景
	 * @return
	 */
	public Qrcode create(String accessToken, Scene scene) {
		QrLimitSceneAction action = new QrLimitSceneAction();
		action.setActionInfo(new ActionInfo(scene));
		return create(accessToken, action);
	}
	
	/**
	 * 创建临时二维码
	 * @param accessToken
	 * @param scene 场景
	 * @param expireSeconds
	 * @return
	 */
	public Qrcode create(String accessToken, Scene scene, int expireSeconds) {
		QrSceneAction action = new QrSceneAction();
		action.setExpireSeconds(expireSeconds);
		action.setActionInfo(new ActionInfo(scene));
		return create(accessToken, action);
	}
	
	/**
	 * 创建字符串场景永久二维码
	 * @param accessToken
	 * @param scene 字符串场景
	 * @return
	 */
	public Qrcode create(String accessToken, StrScene scene) {
		QrLimitStrSceneAction action = new QrLimitStrSceneAction();
		action.setActionInfo(new StrSceneActionInfo(scene));
		return create(accessToken, action);
	}
	
	/**
	 * 换取二维码图片
	 * @param qrcode
	 * @return
	 */
	public BytesFile show(Qrcode qrcode) {
		String url = WEIXIN_QRCODE_SHOW_LINK + "?ticket=" + qrcode.getTicket();
		return WeixinInterfaceHelper.download(url);
	}
	
	/**
	 * 用ticket换取二维码图片
	 * @param ticket
	 * @return
	 */
	public BytesFile show(String ticket) {
		String url = WEIXIN_QRCODE_SHOW_LINK + "?ticket=" + ticket;
		return WeixinInterfaceHelper.download(url);
	}
	
}
