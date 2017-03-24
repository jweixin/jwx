package com.github.jweixin.jwx;

public class QrcodeTest {
	/*
	private QrcodeService qrcodeService = new QrcodeService();
	
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
	public void testCreateQrcode() {
		Qrcode qrcode = qrcodeService.create(getAccessToken(), new StrScene("hello"));
		System.out.println(gson.toJson(qrcode));
	}
	
	@Test
	public void testShowQrcode() {
		BytesFile bf =qrcodeService.show("gQH_8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyblY2WlItSk45dl8xMDAwME0wN18AAgSVEbBYAwQAAAAA");
		WeixinInterfaceHelper.getFile(bf.getBytes(), "d:/qrcode.jpg");
	}*/

}
