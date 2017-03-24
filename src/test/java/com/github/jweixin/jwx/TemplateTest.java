package com.github.jweixin.jwx;

public class TemplateTest {
	/*
	private TemplateService tmService = new TemplateService();
	
	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter()).create();

	private String getAccessToken() {
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
	public void testGetIndustry(){
		Industry industry = tmService.getIndustry(getAccessToken());
		System.out.println(gson.toJson(industry));
	}
	
	@Test
	public void testSetIndustry(){
		ReturnCode rc = tmService.setIndustry(getAccessToken(), "2", "3");
		System.out.println(gson.toJson(rc));
	}
	
	@Test
	public void testGetAllTemplate(){
		List<Template> list = tmService.getAllTemplate(getAccessToken());
		for(Template t : list){
			System.out.println(t.getTemplateId());
			System.out.println(t.getTitle());
			System.out.println(t.getContent());
			System.out.println(t.getExample());
			System.out.println(t.getPrimaryIndustry());
			System.out.println(t.getDeputyIndustry());
		}
	}
	
	@Test
	public void testSendTemplateMsg(){
		TemplateMessage tm = new TemplateMessage();
		tm.setTemplateId("kNDUFUZN8z8jn0E0ME63hd75LvpLUk4g_Yr7wu1mHVE");
		tm.setTouser("obfFfv7qNx063AW9x449u1LmZvxw");
		tm.setUrl("http://nalan_weixin.tunnel.qydev.com/weixin/index.jsp");
		Map<String, TemplateData> map = new HashMap<String, TemplateData>();
		TemplateData data = new TemplateData("这是模板消息的提示行");
		map.put("first", data);
		data = new TemplateData("测试消息1内容部分", "#173177");
		map.put("msg1", data);
		data = new TemplateData("测试消息2内容部分", "#173177");
		map.put("msg2", data);
		data = new TemplateData("如有疑问，请联系", "#173177");
		map.put("remark", data);
		tm.setData(map);
		TemplateMessageReturnCode  rc = tmService.sendMessage(getAccessToken(), tm);
		System.out.println(gson.toJson(rc));
	}
	
	@Test
	public void testDeleteTemplate(){
		ReturnCode rc = tmService.delTemplate(getAccessToken(), "A62jeRW7cgoQaEgEg-g5ONIoELE95Oqys7_aqa8aYK8");
		System.out.println(gson.toJson(rc));
	}
	
	@Test
	public void testQueryTemplateId(){
		System.out.println(tmService.queryTemplateId(getAccessToken(), "TM00015"));
	}*/

}
