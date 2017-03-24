package com.github.jweixin.jwx;

public class MaterialTest {
	/*
	private MaterialService materialService = new MaterialService();
	private MediaService mediaService = new MediaService();
	
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
	public void testAdd(){
		Material material = materialService.addMaterial(getAccessToken(), WeixinInterfaceHelper.getBytes("D:/tmp/voice_recognition_scan.png"), "voice_recognition_scan.png", MediaType.IMAGE);
		System.out.println(gson.toJson(material));
		VideoMaterialDescription desc = new VideoMaterialDescription();
		desc.setTitle("测试视频");
		desc.setIntroduction("一小段测试视频");
		Material vm = materialService.addVideoMaterial(getAccessToken(), WeixinInterfaceHelper.getBytes("D:/tmp/测试视频.mp4"), "ceshi.mp4", desc);
		System.out.println(gson.toJson(vm));
	}
	
	@Test
	public void testAddNews(){
		String token = getAccessToken();
		Media media = mediaService.upload(token, WeixinInterfaceHelper.getBytes("D:/tmp/1.jpg"), "1.jpg", MediaType.THUMB);
		String p1 = media.getThumbMediaId();
		System.out.println(p1);
		media = mediaService.upload(token, WeixinInterfaceHelper.getBytes("D:/tmp/2.jpg"), "2.jpg", MediaType.THUMB);
		String p2 = media.getThumbMediaId();
		System.out.println(p2);
				
		List<NewsArticle> articles = new ArrayList<NewsArticle>();
		NewsArticle main = new NewsArticle("四海八荒的女神们，请收下这份礼物清单！");
		main.setAuthor("test");
		main.setContent("图文消息内容");
		main.setDigest("图文消息摘要");
		main.setContentSourceUrl("http://nalan_weixin.tunnel.qydev.com/weixin/index.jsp");
		main.setThumbMediaId(p1);
		main.setShowCoverPic(1);
		articles.add(main);
		
		NewsArticle sub1 = new NewsArticle("四海八荒的女神们，请收下这份礼物清单！");
		main.setAuthor("test");
		main.setContent("图文消息内容");
		main.setDigest("图文消息摘要");
		main.setContentSourceUrl("http://nalan_weixin.tunnel.qydev.com/weixin/index.jsp");
		main.setThumbMediaId(p2);
		main.setShowCoverPic(0);
		articles.add(sub1);
		
		Material nm = materialService.addNewsMaterial(token, articles);
		System.out.println(nm.getMediaId());
	}
	
	@Test
	public void testGetMaterialCount(){
		System.out.println(gson.toJson(materialService.getMaterialCount(getAccessToken())));
		
	}*/

}
