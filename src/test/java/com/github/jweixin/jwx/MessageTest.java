package com.github.jweixin.jwx;

public class MessageTest {
	/*
	@Test
	public void testCustomMessage() {
		CustomTextMessage text = new CustomTextMessage();
		text.setTouser("obfFfv7qNx063AW9x449u1LmZvxw");
		text.setText(new Text("你好"));
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = WeixinInterfaceHelper.get(url, AccessToken.class);
		String accessToken = at.getAccessToken();
		ReturnCode r = WeixinInterfaceHelper.post(
				WeixinConst.WEIXIN_CUSTOM_MESSAGE_SEND_LINK + "?access_token=" + accessToken, text, ReturnCode.class);
		Assert.assertEquals(r.getErrmsg(), "ok");
	}

	@Test
	public void testSubList() {
		List<String> list = new ArrayList<String>();
		list.add("JavaWeb编程词典"); // 向列表中添加数据
		list.add("Java编程词典"); // 向列表中添加数据
		list.add("C#编程词典"); // 向列表中添加数据
		list.add("ASP.NET编程词典"); // 向列表中添加数据
		list.add("VC编程词典"); // 向列表中添加数据
		list.add("SQL编程词典");
		Assert.assertEquals(list.subList(0, 5).size(), 5);
	}
	
	@Test
	public void testMassPreview(){
		MassMessage msg = new MassMpnewsMessage(new Filter(2), "123dsdajkasd231jhksad");
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		String msgText = gson.toJson(msg);
		System.out.println(msgText);
		@SuppressWarnings("unchecked")
		Map<String, Object> msgMap = gson.fromJson(msgText, HashMap.class);
		
		if(msgMap.get("filter")!=null){
			msgMap.remove("filter");
		}
		if(msgMap.get("touser")!=null){
			msgMap.remove("touser");
		}
		if(msgMap.get("send_ignore_reprint")!=null){
			System.out.println("now remove");
			msgMap.remove("send_ignore_reprint");
		}
		msgMap.put("towxname", "zhangzhixiang");
		System.out.println(gson.toJson(msgMap));
	}*/

}
