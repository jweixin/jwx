package com.github.jweixin.jwx;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;


public class HttpClientTest {
	
	@Test
	public void testStatus(){
		Assert.assertEquals(HttpStatus.SC_OK, 200);
	}
	/*
	@Test
	public void testGetAccessToken() {
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = WeixinInterfaceHelper.get(url, AccessToken.class);
		Assert.assertNotNull(at.getAccessToken());
		Assert.assertEquals(at.getExpiresIn(), 7200);
	}
	
	
	@Test
	public void testMediaUpload() throws FileNotFoundException{
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = WeixinHttpHelper.doGetJsonObject(url, AccessToken.class);
		File file = new File("D:/tmp/tmpFiles/test.jpg");
		String mediaUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + at.getAccessToken() + "&type=image";
		Media media = WeixinHttpHelper.doPostMediaFile(mediaUrl, file, "media", "test.jpg", Media.class);
		System.out.println("media_id:" + media.getMediaId() + ",type:" + media.getType() + ",create_at:" + media.getCreatedAt());
	}*/

}
