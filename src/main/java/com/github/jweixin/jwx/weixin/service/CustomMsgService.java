package com.github.jweixin.jwx.weixin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.config.WeixinConst;
import com.github.jweixin.jwx.message.custom.CustomMessage;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.custom.Kf;
import com.github.jweixin.jwx.weixin.entity.custom.KfAccount;
import com.github.jweixin.jwx.weixin.entity.custom.KfList;

/**
 * 微信客服管理
 * @author Administrator
 *
 */
@Service
public class CustomMsgService {
	/**
	 * 添加客服帐号
	 * 开发者可以通过本接口为公众号添加客服账号，每个公众号最多添加10个客服账号。
	 * http请求方式: POST
	 * https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
	 */
	public final static String WEIXIN_KF_ACCOUNT_ADD_LINK = "https://api.weixin.qq.com/customservice/kfaccount/add";
	/**
	 * 修改客服帐号
	 * http请求方式: POST
	 * https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN
	 */
	public final static String WEIXIN_KF_ACCOUNT_UPDATE_LINK = "https://api.weixin.qq.com/customservice/kfaccount/update";
	/**
	 * 删除客服帐号
	 * http请求方式: GET
	 * https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN
	 */
	public final static String WEIXIN_KF_ACCOUNT_DEL_LINK = "https://api.weixin.qq.com/customservice/kfaccount/del";
	/**
	 * 设置客服帐号的头像
	 * http请求方式: POST/FORM
	 * http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
	 */
	public final static String WEIXIN_KF_ACCOUNT_UPLOADHEADIMG_LINK = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";
	/**
	 * 获取所有客服账号
	 * 获取所有客服账号
	 * https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
	 */
	public final static String WEIXIN_KF_ACCOUNT_LIST_LINK = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
	
	/**
	 * 添加客服帐号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public ReturnCode addAccount(String accessToken, KfAccount account) {
		String url = WEIXIN_KF_ACCOUNT_ADD_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, account, ReturnCode.class);
	}
	
	/**
	 * 修改客服帐号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public ReturnCode updateAccount(String accessToken, KfAccount account) {
		String url = WEIXIN_KF_ACCOUNT_UPDATE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, account, ReturnCode.class);
	}
	
	/**
	 * 删除客服帐号
	 * @param accessToken
	 * @param account
	 * @return
	 */
	public ReturnCode deleteAccount(String accessToken, KfAccount account) {
		String url = WEIXIN_KF_ACCOUNT_DEL_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, account, ReturnCode.class);
	}
	
	/**
	 * 设置客服帐号的头像
	 * @param accessToken
	 * @param bytes
	 * @param filename
	 * @param kfAccount
	 * @return
	 */
	public ReturnCode setKfHeadimg(String accessToken, byte[] bytes, String filename, String kfAccount) {
		String url = WEIXIN_KF_ACCOUNT_UPLOADHEADIMG_LINK + "?access_token=" + accessToken + "&kf_account=" + kfAccount;
		return WeixinInterfaceHelper.upload(url, bytes, "media", filename, ReturnCode.class);
	}
	
	/**
	 * 获取所有客服账号
	 * @param accessToken
	 * @return
	 */
	public List<Kf> getKfList(String accessToken) {
		String url = WEIXIN_KF_ACCOUNT_LIST_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, KfList.class).getKfList();
	}
	
	/**
	 * 发送客服消息
	 * @param accessToken
	 * @param customMessage
	 * @return
	 */
	public ReturnCode send(String accessToken, CustomMessage customMessage) {
		String url = WeixinConst.WEIXIN_CUSTOM_MESSAGE_SEND_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, customMessage, ReturnCode.class);
	}

}
