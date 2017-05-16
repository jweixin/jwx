package com.github.jweixin.jwx;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.config.WeixinConst;
import com.github.jweixin.jwx.util.WeixinInterfaceException;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.AccessToken;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractButton;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractButtonAdapter;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractMenu;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractMenuAdapter;
import com.github.jweixin.jwx.weixin.entity.menu.Button;
import com.github.jweixin.jwx.weixin.entity.menu.ButtonAdapter;
import com.github.jweixin.jwx.weixin.entity.menu.ClickButton;
import com.github.jweixin.jwx.weixin.entity.menu.ConditionalMenu;
import com.github.jweixin.jwx.weixin.entity.menu.LocationSelectButton;
import com.github.jweixin.jwx.weixin.entity.menu.MatchRule;
import com.github.jweixin.jwx.weixin.entity.menu.Menu;
import com.github.jweixin.jwx.weixin.entity.menu.MenuGroup;
import com.github.jweixin.jwx.weixin.entity.menu.MenuLabel;
import com.github.jweixin.jwx.weixin.entity.menu.PicPhotoOrAlbumButton;
import com.github.jweixin.jwx.weixin.entity.menu.PicSysphotoButton;
import com.github.jweixin.jwx.weixin.entity.menu.PicWeixinButton;
import com.github.jweixin.jwx.weixin.entity.menu.ScancodePushButton;
import com.github.jweixin.jwx.weixin.entity.menu.ScancodeWaitmsgButton;
import com.github.jweixin.jwx.weixin.entity.menu.SubMenu;
import com.github.jweixin.jwx.weixin.entity.menu.ViewButton;
import com.github.jweixin.jwx.weixin.service.MenuService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MenuTest {
	
	private MenuService menuService = new MenuService();

	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter()).create();

	private String getAccessToken() {
		String appID = "wxe2f578fc6340979d";
		String appSecret = "5697cf4b123083507a04518b1f0e2e45";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = null;
		try {
			at = WeixinInterfaceHelper.get(url, AccessToken.class);
		} catch (WeixinInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at.getAccessToken();
	}

	@Test
	public void testMenu() {
		Menu menu = new Menu();
		ClickButton b1 = new ClickButton("今日歌曲");
		b1.setKey("V1001_TODAY_MUSIC");
		menu.getButton().add(b1);
		SubMenu sm = new SubMenu("菜单");
		ViewButton b2 = new ViewButton("搜索");
		b2.setUrl("http://www.soso.com/");
		sm.getSubButton().add(b2);
		ViewButton b3 = new ViewButton("视频");
		b3.setUrl("http://v.qq.com/");
		sm.getSubButton().add(b3);
		ClickButton b4 = new ClickButton("赞一下我们");
		b4.setKey("V1001_GOOD");
		sm.getSubButton().add(b4);
		menu.getButton().add(sm);
		// Gson gson = new
		// GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		Assert.assertNotNull(menu);
	}

	@Test
	public void testJsonDeserializer() {
		Menu menu = new Menu();

		SubMenu sm1 = new SubMenu("扫码");
		menu.getButton().add(sm1);
		ScancodeWaitmsgButton b1 = new ScancodeWaitmsgButton("扫码带提示");
		b1.setKey("rselfmenu_0_0");
		sm1.getSubButton().add(b1);
		ScancodePushButton b2 = new ScancodePushButton("扫码推事件");
		b2.setKey("rselfmenu_0_1");
		sm1.getSubButton().add(b2);

		SubMenu sm2 = new SubMenu("发图");
		menu.getButton().add(sm2);
		PicSysphotoButton b3 = new PicSysphotoButton("系统拍照发图");
		b3.setKey("rselfmenu_1_0");
		sm2.getSubButton().add(b3);
		PicPhotoOrAlbumButton b4 = new PicPhotoOrAlbumButton("拍照或者相册发图");
		b4.setKey("rselfmenu_1_1");
		sm2.getSubButton().add(b4);
		PicWeixinButton b5 = new PicWeixinButton("微信相册发图");
		b5.setKey("rselfmenu_1_2");
		sm2.getSubButton().add(b5);

		SubMenu sm3 = new SubMenu("其他");
		menu.getButton().add(sm3);
		LocationSelectButton b6 = new LocationSelectButton("发送位置");
		b6.setKey("rselfmenu_2_0");
		sm3.getSubButton().add(b6);
//		
//		 * MediaIdButton b7 = new MediaIdButton("图片");
//		 * b7.setMediaId("MEDIA_ID1"); sm3.getSubButton().add(b7);
//		 * ViewLimitedButton b8 = new ViewLimitedButton("图文消息");
//		 * b8.setMediaId("MEDIA_ID2"); sm3.getSubButton().add(b8);
//		 
		System.out.println(gson.toJson(menuService.createMenu(getAccessToken(), menu)));

	}

	@Test
	public void testGetMenu() {

		AbstractMenu am = menuService.getMenu(getAccessToken());
		if (am instanceof MenuLabel) {
			MenuLabel label = (MenuLabel) am;
			Menu menu = label.getMenu();
			System.out.println(gson.toJson(menu));
		}
		if (am instanceof MenuGroup) {
			MenuGroup group = (MenuGroup) am;
			System.out.println(gson.toJson(group));
		}

	}

	@Test
	public void testCreateMenu() {
		Menu menu = new Menu();
		ClickButton b1 = new ClickButton("今日歌曲");
		b1.setKey("V1001_TODAY_MUSIC");
		menu.getButton().add(b1);
		SubMenu sm = new SubMenu("菜单");
		ViewButton b2 = new ViewButton("搜索");
		b2.setUrl("http://www.soso.com/");
		sm.getSubButton().add(b2);
		ViewButton b3 = new ViewButton("视频");
		b3.setUrl("http://v.qq.com/");
		sm.getSubButton().add(b3);
		ClickButton b4 = new ClickButton("赞一下我们");
		b4.setKey("V1001_GOOD");
		sm.getSubButton().add(b4);
		menu.getButton().add(sm);
		ReturnCode rc = menuService.createMenu(getAccessToken(), menu);
		System.out.println(rc.getErrcode()+rc.getErrmsg());
	}
	
	@Test
	public void testCreateConditional(){
		ConditionalMenu menu = new ConditionalMenu();

		SubMenu sm1 = new SubMenu("扫码");
		menu.getButton().add(sm1);
		ScancodeWaitmsgButton b1 = new ScancodeWaitmsgButton("扫码带提示");
		b1.setKey("rselfmenu_0_0");
		sm1.getSubButton().add(b1);
		ScancodePushButton b2 = new ScancodePushButton("扫码推事件");
		b2.setKey("rselfmenu_0_1");
		sm1.getSubButton().add(b2);

		SubMenu sm2 = new SubMenu("发图");
		menu.getButton().add(sm2);
		PicSysphotoButton b3 = new PicSysphotoButton("系统拍照发图");
		b3.setKey("rselfmenu_1_0");
		sm2.getSubButton().add(b3);
		PicPhotoOrAlbumButton b4 = new PicPhotoOrAlbumButton("拍照或者相册发图");
		b4.setKey("rselfmenu_1_1");
		sm2.getSubButton().add(b4);
		PicWeixinButton b5 = new PicWeixinButton("微信相册发图");
		b5.setKey("rselfmenu_1_2");
		sm2.getSubButton().add(b5);

		SubMenu sm3 = new SubMenu("其他");
		menu.getButton().add(sm3);
		LocationSelectButton b6 = new LocationSelectButton("发送位置");
		b6.setKey("rselfmenu_2_0");
		sm3.getSubButton().add(b6);
		
		MatchRule rule = new MatchRule();
		rule.setTagId(100);
		
		menu.setMatchrule(rule);
		
		System.out.println("menu_id:" + menuService.addConditional(getAccessToken(), menu));
	}

	@Test
	public void testDeleteConditional() {

		ReturnCode rc = menuService.deleteConditional(getAccessToken(), 412451153);
		System.out.println(gson.toJson(rc));

	}

	@Test
	public void testDeleteMenu() {

		ReturnCode rc = menuService.deleteMenu(getAccessToken());
		System.out.println(gson.toJson(rc));

	}
	
	@Test
	public void testMatch(){
		Menu menu = menuService.match(getAccessToken(), "obfFfv70_Ei4P9a8wuE-lUH0fds4");
		System.out.println(gson.toJson(menu));
	}

}
