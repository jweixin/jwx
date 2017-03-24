package com.github.jweixin.jwx.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.message.mass.Mpvideo;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.BytesFile;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.material.Material;
import com.github.jweixin.jwx.weixin.entity.material.MaterialCount;
import com.github.jweixin.jwx.weixin.entity.material.MaterialList;
import com.github.jweixin.jwx.weixin.entity.material.Media;
import com.github.jweixin.jwx.weixin.entity.material.MediaType;
import com.github.jweixin.jwx.weixin.entity.material.NewsArticle;
import com.github.jweixin.jwx.weixin.entity.material.NewsMaterial;
import com.github.jweixin.jwx.weixin.entity.material.NewsMaterialImageURL;
import com.github.jweixin.jwx.weixin.entity.material.NewsMaterialList;
import com.github.jweixin.jwx.weixin.entity.material.VideoMaterialDescription;

/**
 * 永久素材管理
 * @author Administrator
 *
 */
@Service
public class MaterialService {
	/**
	 * 新增永久素材链接
	 */
	public final static String WEIXIN_MATERIAL_ADD_LINK = "https://api.weixin.qq.com/cgi-bin/material/add_material";
	/**
	 * 新增永久图文素材链接
	 */
	public final static String WEIXIN_NEWS_MATERIAL_ADD_LINK = "https://api.weixin.qq.com/cgi-bin/material/add_news";
	/**
	 * 获取永久素材链接
	 */
	public final static String WEIXIN_MATERIAL_GET_LINK = "https://api.weixin.qq.com/cgi-bin/material/get_material";
	/**
	 * 删除永久素材链接
	 */
	public final static String WEIXIN_MATERIAL_DEL_LINK = "https://api.weixin.qq.com/cgi-bin/material/del_material";
	/**
	 * 修改永久图文素材链接
	 */
	public final static String WEIXIN_NEWS_MATERIAL_UPDATE_LINK = "https://api.weixin.qq.com/cgi-bin/material/update_news";
	/**
	 * 获取素材总数链接
	 */
	public final static String WEIXIN_MATERIAL_COUNT_LINK = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
	/**
	 * 获取素材列表链接
	 */
	public final static String WEIXIN_MATERIAL_LIST_LINK = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
	/**
	 * 上传图文消息内的图片链接
	 */
	public final static String WEIXIN_NEWS_MATERIAL_IMAGE_UPLOAD_LINK = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	/**
	 * 获取群发视频消息mediaId链接
	 */
	public final static String WEIXIN_VIDEO_MEDIA_UPLOAD_LINK = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";
	
	/**
	 * 新增永久素材
	 * 不能通过这个方法新增永久视频素材
	 * @param accessToken
	 * @param bytes
	 * @param filename
	 * @param type
	 * @return
	 */
	public Material addMaterial(String accessToken, byte[] bytes, String filename, MediaType type) {
		String url = WEIXIN_MATERIAL_ADD_LINK + "?access_token=" + accessToken + "&type=" + type.type();
		if (MediaType.VIDEO.equals(type)) {
			return null;
		}
		if (Material.check(bytes, filename, type)) {
			return WeixinInterfaceHelper.upload(url, bytes, "media", filename, Material.class);
		}
		return null;
	}

	/**
	 * 新增永久视频素材
	 * @param accessToken
	 * @param bytes
	 * @param filename
	 * @param description
	 * @return
	 */
	public Material addVideoMaterial(String accessToken, byte[] bytes, String filename,
			VideoMaterialDescription description) {
		String url = WEIXIN_MATERIAL_ADD_LINK + "?access_token=" + accessToken + "&type=" + MediaType.VIDEO.type();
		if (Material.check(bytes, filename, MediaType.VIDEO)) {
			return WeixinInterfaceHelper.upload(url, bytes, "media", filename, description, "description",
					Material.class);
		}
		return null;
	}
	
	/**
	 * 新增永久图文素材
	 * @param accessToken
	 * @param articles
	 * @return
	 */
	public Material addNewsMaterial(String accessToken, List<NewsArticle> articles) {
		String url = WEIXIN_NEWS_MATERIAL_ADD_LINK + "?access_token=" + accessToken;
		Map<String, List<NewsArticle>> map = new HashMap<String, List<NewsArticle>>();
		map.put("articles", articles);
		return WeixinInterfaceHelper.post(url, map, Material.class);
	}
	
	/**
	 * 新增永久单图文素材
	 * @param accessToken
	 * @param article
	 * @return
	 */
	public Material addNewsMaterial(String accessToken, NewsArticle article) {
		String url = WEIXIN_NEWS_MATERIAL_ADD_LINK + "?access_token=" + accessToken;
		List<NewsArticle> articles = new ArrayList<NewsArticle>();
		articles.add(article);
		Map<String, List<NewsArticle>> map = new HashMap<String, List<NewsArticle>>();
		map.put("articles", articles);
		return WeixinInterfaceHelper.post(url, map, Material.class);
	}
	
	/**
	 * 获取其他类型的素材消息，响应的直接为素材的内容
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public BytesFile getMaterial(String accessToken, String mediaId) {
		String url = WEIXIN_MATERIAL_GET_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("media_id", mediaId);
		return WeixinInterfaceHelper.download(url, map);
	}
	
	/**
	 * 获取视频消息素材
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public VideoMaterialDescription getVideoMaterial(String accessToken, String mediaId) {
		String url = WEIXIN_MATERIAL_GET_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("media_id", mediaId);
		return WeixinInterfaceHelper.post(url, map, VideoMaterialDescription.class);
	}
	
	/**
	 * 获取图文素材
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public NewsMaterial getNewsMaterial(String accessToken, String mediaId) {
		String url = WEIXIN_MATERIAL_GET_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("media_id", mediaId);
		return WeixinInterfaceHelper.post(url, map, NewsMaterial.class);
	}
	
	/**
	 * 删除永久素材
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public ReturnCode delMaterial(String accessToken, String mediaId) {
		String url = WEIXIN_MATERIAL_DEL_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("media_id", mediaId);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 修改永久图文素材
	 * @param accessToken
	 * @param mediaId
	 * @param index
	 * @param article
	 * @return
	 */
	public ReturnCode updateNewsMaterial(String accessToken, String mediaId, int index, NewsArticle article) {
		String url = WEIXIN_NEWS_MATERIAL_UPDATE_LINK + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("media_id", mediaId);
		map.put("index", index);
		map.put("articles", article);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 修改单图文消息素材
	 * @param accessToken
	 * @param mediaId
	 * @param article
	 * @return
	 */
	public ReturnCode updateNewsMaterial(String accessToken, String mediaId, NewsArticle article) {
		return updateNewsMaterial(accessToken, mediaId, 0, article);
	}
	
	/**
	 * 获取素材总数
	 * @param accessToken
	 * @return
	 */
	public MaterialCount getMaterialCount(String accessToken) {
		String url = WEIXIN_MATERIAL_COUNT_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, MaterialCount.class);
	}
	
	/**
	 * 获取其他类型（图片、语音、视频）的素材列表
	 * @param accessToken
	 * @param offset
	 * @param count
	 * @param type
	 * @return
	 */
	public MaterialList getMaterialList(String accessToken, int offset, int count, MediaType type) {
		if(MediaType.IMAGE.equals(type) || MediaType.VIDEO.equals(type) || MediaType.VOICE.equals(type)){
			String url = WEIXIN_MATERIAL_LIST_LINK + "?access_token=" + accessToken;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", type.type());
			map.put("offset", offset);
			map.put("count", count);
			return WeixinInterfaceHelper.post(url, map, MaterialList.class);
		}
		return null;
	}
	
	/**
	 * 获取永久图文消息素材列表
	 * @param accessToken
	 * @param offset
	 * @param count
	 * @return
	 */
	public NewsMaterialList getNewsMaterialList(String accessToken, int offset, int count) {
		String url = WEIXIN_MATERIAL_LIST_LINK + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "news");
		map.put("offset", offset);
		map.put("count", count);
		return WeixinInterfaceHelper.post(url, map, NewsMaterialList.class);
	}
	
	/**
	 * 上传图文消息内的图片获取URL
	 * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。
	 * 图片仅支持jpg/png格式，大小必须在1MB以下。
	 * @param accessToken
	 * @param bytes
	 * @param filename
	 * @return 图片的url链接
	 */
	public String uploadNewsMaterialImage(String accessToken, byte[] bytes, String filename) {
		long size = bytes.length;
		if(size>10000000){
			return null;
		}
		// 文件扩展名
		String ext = filename.indexOf(".") != -1 ? filename.substring(filename.lastIndexOf(".") + 1, filename.length()) : null;
		if (ext == null) {
			return null;
		}
		ext = ext.toUpperCase();
		if (ext.equals("JPG") || ext.equals("PNG")){
			String url = WEIXIN_NEWS_MATERIAL_IMAGE_UPLOAD_LINK + "?access_token=" + accessToken;
			return WeixinInterfaceHelper.upload(url, bytes, "media", filename, NewsMaterialImageURL.class).getUrl();
		}
		return null;
	}
	
	/**
	 * 获取群发视频消息mediaId
	 * @param accessToken
	 * @param mpvideo
	 * @return
	 */
	public Media getVideoMedia(String accessToken, Mpvideo mpvideo) {
		String url = WEIXIN_VIDEO_MEDIA_UPLOAD_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, mpvideo, Media.class);
	}
	
}
