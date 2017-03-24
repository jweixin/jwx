package com.github.jweixin.jwx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.message.custom.CustomImageMessage;
import com.github.jweixin.jwx.message.custom.CustomMusicMessage;
import com.github.jweixin.jwx.message.custom.CustomNewsMessage;
import com.github.jweixin.jwx.message.custom.CustomVideoMessage;
import com.github.jweixin.jwx.message.custom.News;
import com.github.jweixin.jwx.message.response.Article;
import com.github.jweixin.jwx.message.response.Image;
import com.github.jweixin.jwx.message.response.Music;
import com.github.jweixin.jwx.message.response.Video;
import com.github.jweixin.jwx.weixin.entity.material.Media;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonTest {
	
	@Test
	public void testKfImageMessageJson(){		
		Image image = new Image();
		image.setMediaId("MEDIA_ID");
		CustomImageMessage imageMsg = new CustomImageMessage();
		imageMsg.setTouser("OPENID");
		imageMsg.setImage(image);
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		//System.out.println(gson.toJson(imageMsg));
		String str = gson.toJson(imageMsg);
		Assert.assertNotNull(gson.fromJson(str, CustomImageMessage.class));
	}
	
	@Test
	public void testKfVideoMessageJson(){		
		Video video = new Video();
		video.setMediaId("MEDIA_ID");
		video.setThumbMediaId("MEDIA_ID");
		video.setTitle("TITLE");
		video.setDescription("DESCRIPTION");
		CustomVideoMessage videoMsg = new CustomVideoMessage();
		videoMsg.setTouser("OPENID");
		videoMsg.setVideo(video);
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		//System.out.println(gson.toJson(videoMsg));
		String str = gson.toJson(videoMsg);
		Assert.assertNotNull(gson.fromJson(str, CustomVideoMessage.class));
	}
	
	@Test
	public void testKfMusicMessageJson(){		
		Music music = new Music();
		music.setTitle("MUSIC_TITLE");
		music.setDescription("MUSIC_DESCRIPTION");
		music.setMusicurl("MUSIC_URL");
		music.setHqmusicurl("HQ_MUSIC_URL");
		music.setThumbMediaId("THUMB_MEDIA_ID");
		CustomMusicMessage musicMsg = new CustomMusicMessage();
		musicMsg.setTouser("OPENID");
		musicMsg.setMusic(music);
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		//System.out.println(gson.toJson(musicMsg));
		String str = gson.toJson(musicMsg);
		Assert.assertNotNull(gson.fromJson(str, CustomMusicMessage.class));
	}
	
	@Test
	public void testKfNewsMessage(){
		List<Article> articles = new ArrayList<Article>();
		Article a1 = new Article();
		a1.setTitle("Happy Day");
		a1.setDescription("Is Really A Happy Day");
		a1.setUrl("URL");
		a1.setPicurl("PIC_URL");
		articles.add(a1);
		Article a2 = new Article();
		a2.setTitle("Happy Day");
		a2.setDescription("Is Really A Happy Day");
		a2.setUrl("URL");
		a2.setPicurl("PIC_URL");
		articles.add(a2);
		CustomNewsMessage newsMsg = new CustomNewsMessage();
		newsMsg.setTouser("OPENID");
		newsMsg.setNews(new News(articles));
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		//System.out.println(gson.toJson(newsMsg));
		String str = gson.toJson(newsMsg);
		Assert.assertNotNull(gson.fromJson(str, CustomNewsMessage.class));
	}
	
	@Test
	public void testMapJson(){
		Map<String, Media> map = new HashMap<String, Media>();
		Media media = new Media();
		media.setMediaId("mediaId");
		media.setThumbMediaId("thumbMediaId");
		media.setType("news");
		media.setCreatedAt(1892391);
		map.put("template_id_short", media);
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
		//System.out.println(gson.toJson(newsMsg));
		String jsonStr = gson.toJson(map);
		gson.fromJson(jsonStr, HashMap.class);
	}

}
