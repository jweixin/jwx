package com.github.jweixin.jwx;

public class SystemTest {
	/*
	private SystemService systemService = new SystemService();
	
	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter())
			.create();
	
	private String getAccessToken(){
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
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
	public void testWeixinIpList() {
		List<String> ipList = systemService.getWeixinIpList(getAccessToken());
		for(String ip : ipList){
			System.out.println(ip);
		}
	}
	
	@Test
	public void testLong2Short() {
		ShortUrlReturnCode rc = systemService.long2short(getAccessToken(), "http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1");
		System.out.println(gson.toJson(rc));
		System.out.println(rc.getShortUrl());
	}*/
}
