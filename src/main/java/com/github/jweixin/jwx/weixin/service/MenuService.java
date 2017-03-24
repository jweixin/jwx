package com.github.jweixin.jwx.weixin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractMenu;
import com.github.jweixin.jwx.weixin.entity.menu.ConditionalMenu;
import com.github.jweixin.jwx.weixin.entity.menu.Menu;
import com.github.jweixin.jwx.weixin.entity.menu.MenuIdLabel;
import com.github.jweixin.jwx.weixin.entity.menu.MenuLabel;

/**
 * 菜单管理
 * @author Administrator
 * @version 1.0 2017-2-11
 */
@Service
public class MenuService {
	/**
	 * 自定义菜单创建链接
	 */
	public final static String WEIXIN_MENU_CREATE_LINK = "https://api.weixin.qq.com/cgi-bin/menu/create";
	/**
	 * 自定义菜单查询链接
	 */
	public final static String WEIXIN_MENU_GET_LINK = "https://api.weixin.qq.com/cgi-bin/menu/get";
	/**
	 * 自定义菜单删除链接
	 */
	public final static String WEIXIN_MENU_DELETE_LINK = "https://api.weixin.qq.com/cgi-bin/menu/delete";
	/**
	 * 创建个性化菜单链接
	 */
	public final static String WEIXIN_CONDITIONAL_MENU_ADD_LINK = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
	/**
	 * 删除个性化菜单链接
	 */
	public final static String WEIXIN_CONDITIONAL_MENU_DELETE_LINK = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
	/**
	 * 测试个性化菜单匹配结果链接
	 */
	public final static String WEIXIN_MENU_MATCH_LINK = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
	
	/**
	 * 创建自定义菜单
	 * @param accessToken
	 * @param menu
	 * @return
	 */
	public ReturnCode createMenu(String accessToken, Menu menu) {
		if(menu!=null && menu.check()){
			String url = WEIXIN_MENU_CREATE_LINK + "?access_token=" + accessToken;
			return WeixinInterfaceHelper.post(url, menu, ReturnCode.class);
		}
		return null;
	}
	
	/**
	 * 自定义菜单查询
	 * 无个性化菜单时,返回MenuLabel;有个性化菜单时,返回MenuGroup
	 * @param accessToken
	 * @return
	 */
	public AbstractMenu getMenu(String accessToken) {
		String url = WEIXIN_MENU_GET_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, AbstractMenu.class);
	}
	
	/**
	 * 自定义菜单删除
	 * 注意，在个性化菜单时，调用此接口会删除默认菜单及全部个性化菜单。
	 * @param accessToken
	 * @return
	 */
	public ReturnCode deleteMenu(String accessToken) {
		String url = WEIXIN_MENU_DELETE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, ReturnCode.class);
	}
	
	/**
	 * 创建个性化菜单
	 * @param accessToken
	 * @param conditional
	 * @return 创建的个性化菜单id
	 */
	public Long addConditional(String accessToken, ConditionalMenu conditional) {
		if(conditional.check()){
			String url = WEIXIN_CONDITIONAL_MENU_ADD_LINK + "?access_token=" + accessToken;
			MenuIdLabel label = WeixinInterfaceHelper.post(url, conditional, MenuIdLabel.class);
			return label.getMenuid();
		}
		return null;
	}
	
	/**
	 * 删除个性化菜单
	 * @param accessToken
	 * @param menuId
	 * @return
	 */
	public ReturnCode deleteConditional(String accessToken, long menuId) {
		String url = WEIXIN_CONDITIONAL_MENU_DELETE_LINK + "?access_token=" + accessToken;
		MenuIdLabel label = new MenuIdLabel();
		label.setMenuid(menuId);
		return WeixinInterfaceHelper.post(url, label, ReturnCode.class);
	}
	
	/**
	 * 测试个性化菜单匹配结果
	 * @param accessToken
	 * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号
	 * @return
	 */
	public Menu match(String accessToken, String userId) {
		String url = WEIXIN_MENU_MATCH_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", userId);
		return WeixinInterfaceHelper.post(url, map, MenuLabel.class).getMenu();
	}

}
