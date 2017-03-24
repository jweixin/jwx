package com.github.jweixin.jwx.weixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.UncategorizedWeixinException;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.WebAuthAccessToken;
import com.github.jweixin.jwx.weixin.entity.WebAuthScope;
import com.github.jweixin.jwx.weixin.entity.user.UserInfo;

/**
 * 微信网页授权服务
 * @author Administrator
 *
 */
@Service
public class WebAuthService {
	/**
	 * 用户同意授权，获取code链接
	 */
	public final static String WEIXIN_CONNECT_OAUTH2_AUTHORIZE_LINK = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/**
	 * 通过code换取网页授权access_token链接
	 */
	public final static String WEIXIN_SNS_OAUTH2_ACCESS_TOKEN_LINK = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/**
	 * 刷新access_token链接
	 */
	public final static String WEIXIN_SNS_REFRESH_TOKEN_LINK = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	/**
	 * 检验授权凭证（access_token）是否有效链接
	 */
	public final static String WEIXIN_SNS_CHECK_ACCESS_TOKEN_LINK = "https://api.weixin.qq.com/sns/auth";
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)链接
	 */
	public final static String WEIXIN_SNS_GET_USERINFO_LINK = "https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * 返回微信网页授权的url链接
	 * @param redirectUri
	 * @param appid
	 * @param scope
	 * @param state
	 * @return
	 */
	public String getAuthorizeUrl(String redirectUri, String appid, WebAuthScope scope, String state) {
		try {
			return WEIXIN_CONNECT_OAUTH2_AUTHORIZE_LINK + "?appid=" + appid + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") 
			+ "&response_type=code&scope=" + scope.type() + "&state=" + state + "#wechat_redirect";
		} catch (UnsupportedEncodingException e) {
			throw new UncategorizedWeixinException("URLEncoder编码异常", e);
		}
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * @param code
	 * @param appid
	 * @param secret
	 * @return
	 */
	public WebAuthAccessToken getAccessToken(String code, String appid, String secret) {
		String url = WEIXIN_SNS_OAUTH2_ACCESS_TOKEN_LINK + "?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
		return WeixinInterfaceHelper.get(url, WebAuthAccessToken.class);
	}
	
	/**
	 * 刷新access_token
	 * @param refreshToken
	 * @param appid
	 * @return
	 */
	public WebAuthAccessToken refreshAccessToken(String refreshToken, String appid) {
		String url = WEIXIN_SNS_REFRESH_TOKEN_LINK + "?appid=" + appid + "&grant_type=refresh_token&refresh_token=" + refreshToken;
		return WeixinInterfaceHelper.get(url, WebAuthAccessToken.class);
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openid 用户的唯一标识
	 * @return
	 */
	public ReturnCode checkAccessToken(String accessToken, String openid) {
		String url = WEIXIN_SNS_CHECK_ACCESS_TOKEN_LINK + "?access_token=" + accessToken + "&openid=" + openid;
		return WeixinInterfaceHelper.get(url, ReturnCode.class);
	}
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openid 用户的唯一标识
	 * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return
	 */
	public UserInfo getUserInfo(String accessToken, String openid, String lang) {
		if(lang==null){
			lang = "zh_CN";
		}
		String url = WEIXIN_SNS_GET_USERINFO_LINK + "?access_token=" + accessToken + "&openid=" + openid + "&lang=" + lang;
		return WeixinInterfaceHelper.get(url, UserInfo.class);
	}

}
