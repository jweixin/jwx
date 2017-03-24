package com.github.jweixin.jwx;

public class MassTest {
	/*
	private MassMsgService massMsgService = new MassMsgService();
	
	private MediaService mediaService = new MediaService();
	
	//private MaterialService materialService = new MaterialService();
	
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
	public void testUploadNewsImg(){
		String token = getAccessToken();
		Media main = mediaService.upload(token, WeixinInterfaceHelper.getBytes("D:/tmp/1.jpg"), "1.jpg", MediaType.THUMB);
		System.out.println(gson.toJson(main));
		System.out.println(main.getMediaId());
		Media sub1 = mediaService.upload(token, WeixinInterfaceHelper.getBytes("D:/tmp/2.jpg"), "2.jpg", MediaType.THUMB);
		System.out.println(gson.toJson(sub1));
		System.out.println(sub1.getMediaId());
		Media sub2 = mediaService.upload(token, WeixinInterfaceHelper.getBytes("D:/tmp/3.jpg"), "3.jpg", MediaType.THUMB);
		System.out.println(gson.toJson(sub2));
		System.out.println(sub2.getMediaId());
	}
	
	@Test
	public void testThumbMedia(){
		Media media = mediaService.upload(getAccessToken(), WeixinInterfaceHelper.getBytes("D:/tmp/2.jpg"), "2.jpg", MediaType.THUMB);
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
	}
	
	@Test
	public void testUploadNews(){
		List<NewsArticle> articles = new ArrayList<NewsArticle>();
		NewsArticle main = new NewsArticle("四海八荒的女神们，请收下这份礼物清单！");
		main.setAuthor("test");
		main.setContent("图文消息内容");
		main.setDigest("图文消息摘要");
		main.setContentSourceUrl("http://nalan_weixin.tunnel.qydev.com/weixin/index.jsp");
		main.setThumbMediaId("ip3zpUd_TKEPijguNMnZ5tlGEf_Kt2b5zM6aRa4y-PHMJRUzMq3ZbujvostAuXpT");
		main.setShowCoverPic(1);
		articles.add(main);
		
		NewsArticle sub1 = new NewsArticle("四海八荒的女神们，请收下这份礼物清单！");
		main.setAuthor("test");
		main.setContent("图文消息内容");
		main.setDigest("图文消息摘要");
		main.setContentSourceUrl("http://nalan_weixin.tunnel.qydev.com/weixin/index.jsp");
		main.setThumbMediaId("zEwjfJwx-UZcYJK06hWbE95Q_0Afk2j_Orbo4-jnMJRcysQAjojMRHKc3dYuuKbz");
		main.setShowCoverPic(0);
		articles.add(sub1);
		Media media = massMsgService.uploadNews(getAccessToken(), articles);
		System.out.println(gson.toJson(media));
		System.out.println(media.getMediaId());
	}
	
	@Test
	public void testMassSend(){
		List<String> tousers = new ArrayList<String>();
		tousers.add("obfFfv7qNx063AW9x449u1LmZvxw");
		tousers.add("obfFfv70_Ei4P9a8wuE-lUH0fds4");
		MassMpnewsMessage message = new MassMpnewsMessage(tousers);
		//Filter filter = new Filter(100);
		//message.setFilter(filter);
		message.setMpnews(new Mpnews("T8SVHOXo4j63ZeAe0He5hqZ7U6B-mQgpyeGqLrbjzmfAF0QIU08MYBy_6mVg4Laq"));
		message.setSendIgnoreReprint(0);
		MassReturnCode mrc = massMsgService.send(getAccessToken(), message);
		System.out.println(gson.toJson(mrc));
	}
	
	@Test
	public void testPreview(){
		List<String> tousers = new ArrayList<String>();
		tousers.add("obfFfv7qNx063AW9x449u1LmZvxw");
		tousers.add("obfFfv70_Ei4P9a8wuE-lUH0fds4");
		MassMpnewsMessage message = new MassMpnewsMessage(tousers);
		//Filter filter = new Filter(100);
		//message.setFilter(filter);
		message.setMpnews(new Mpnews("T8SVHOXo4j63ZeAe0He5hqZ7U6B-mQgpyeGqLrbjzmfAF0QIU08MYBy_6mVg4Laq"));
		message.setSendIgnoreReprint(0);
		MassReturnCode mrc = massMsgService.preview(getAccessToken(), message, "obfFfv7qNx063AW9x449u1LmZvxw", null);
		System.out.println(gson.toJson(mrc));
	}
	
	@Test
	public void testGetStatus(){
		MsgStatus status = massMsgService.status(getAccessToken(), 3147483650L);
		System.out.println(gson.toJson(status));
	}
	
	@Test
	public void testDelete(){
		ReturnCode rc = massMsgService.delete(getAccessToken(), 3147483650L);
		System.out.println(gson.toJson(rc));
	}
	*/
}
