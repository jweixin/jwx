package com.github.jweixin.jwx;

public class MediaTest {
	/*
	private MediaService mediaService = new MediaService();
	private MaterialService materialService = new MaterialService();

	private String getAccessToken() {
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = WeixinInterfaceHelper.get(url, AccessToken.class);
		return at.getAccessToken();
	}
	
	@Test
	public void testUpload(){
		
		Media media = mediaService.upload(getAccessToken(), new File("D:/tmp/1.jpg"), MediaType.IMAGE);
		System.out.println("upload image:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		media = mediaService.upload(getAccessToken(), new File("D:/tmp/2.jpg"), MediaType.IMAGE);
		System.out.println("upload image:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		media = mediaService.upload(getAccessToken(), new File("D:/tmp/3.jpg"), MediaType.IMAGE);
		System.out.println("upload image:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		/*
		media = mediaService.upload(getAccessToken(), new File("D:/tmp/1.mp4"), MediaType.VIDEO);
		System.out.println("upload video:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		media = mediaService.upload(getAccessToken(), new File("D:/tmp/arm格式.amr"), MediaType.VOICE);
		System.out.println("upload voice:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		media = mediaService.upload(getAccessToken(), new File("D:/tmp/音乐测试.mp3"), MediaType.VOICE);
		System.out.println("upload voice:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
		
		Media media = mediaService.upload(getAccessToken(), new File("D:/tmp/11321R0G-1.jpg"), MediaType.THUMB);
		System.out.println("upload thumb:");
		System.out.println(media.getMediaId());
		System.out.println(media.getThumbMediaId());
		System.out.println(media.getType());
		System.out.println(media.getCreatedAt());
		System.out.println();
	}
	
	@Test
	public void testDownload(){
		String token = getAccessToken();
		String path = "D:/download";
		System.out.println("download image:");
		BytesFile bf = mediaService.getMedia(token, "pHAsRvv46oeYUxFgMxYty3aq-CiIvRcHnHN6dSswAyC6LjQu5SOjY2-wwS_LtEQ9");
		System.out.println(bf.getFilename());
		WeixinInterfaceHelper.getFile(bf.getBytes(), path + File.separator + bf.getFilename());
		
		System.out.println("download voice:");
		bf = mediaService.getMedia(token, "JYyGOKR4_B0upnCy-OClNsYgfD-qVsixPuePOzLbAzlWP916lkLu_eLzS5YIeejj");
		System.out.println(bf.getFilename());
		WeixinInterfaceHelper.getFile(bf.getBytes(), path + File.separator + bf.getFilename());
		
		System.out.println("download voice:");
		bf = mediaService.getMedia(token, "H9v02AFbLvHHWzhcKDUmppBCfj_6fL1KXj_OwaiQUA-Zn3lLm4tHYotvTEk_P-iJ");
		System.out.println(bf.getFilename());
		WeixinInterfaceHelper.getFile(bf.getBytes(), path + File.separator + bf.getFilename());
		
		System.out.println("download video:");
		String link = mediaService.getVideoMediaLink(token, "yZdj11KA8slZZez1W8FpYRwQkiiqr0O1GWU-6XECkP_COA-GwMVF85dGZnxJs8-E");
		bf = WeixinInterfaceHelper.download(link);
		System.out.println(bf.getFilename());
		WeixinInterfaceHelper.getFile(bf.getBytes(), path + File.separator + bf.getFilename());
	}
	
	@Test
	public void testUploadMeterial(){
		Material material = materialService.addMaterial(getAccessToken(), WeixinInterfaceHelper.getBytes("D:/tmp/3s3s.jpg"), "3s3s.jpg", MediaType.THUMB);
		System.out.println(material.getMediaId());
		System.out.println(material.getUrl());;
		
	}*/

}
