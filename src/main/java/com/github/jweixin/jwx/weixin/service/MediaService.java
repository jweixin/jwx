package com.github.jweixin.jwx.weixin.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.BytesFile;
import com.github.jweixin.jwx.weixin.entity.material.Media;
import com.github.jweixin.jwx.weixin.entity.material.MediaType;
import com.github.jweixin.jwx.weixin.entity.material.VideoMediaURL;

/**
 * 临时素材管理
 * @author Administrator
 *
 */
@Service
public class MediaService {
	/**
	 * 新增临时素材链接
	 */
	public final static String WEIXIN_MEDIA_UPLOAD_LINK = "https://api.weixin.qq.com/cgi-bin/media/upload";
	/**
	 * 获取临时素材链接
	 */
	public final static String WEIXIN_MEDIA_GET_LINK = "https://api.weixin.qq.com/cgi-bin/media/get";
	/**
	 * 高清语音素材获取链接
	 */
	public final static String WEIXIN_MEDIA_GET_JSSDK_LINK = "https://api.weixin.qq.com/cgi-bin/media/get/jssdk";
	
	/**
	 * 从文件新建临时素材
	 * @param accessToken
	 * @param file
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public Media upload(String accessToken, File file, MediaType type) {
		if(Media.check(file, type)){
			String url = WEIXIN_MEDIA_UPLOAD_LINK + "?access_token=" + accessToken + "&type=" + type.type();
			return WeixinInterfaceHelper.upload(url, file, "media", Media.class);
		}
		return null;
	}
	
	/**
	 * 从二进制数字新建临时素材
	 * @param accessToken
	 * @param bytes
	 * @param filename
	 * @param type
	 * @return
	 */
	public Media upload(String accessToken, byte[] bytes, String filename, MediaType type) {
		if(Media.check(bytes, filename, type)){
			String url = WEIXIN_MEDIA_UPLOAD_LINK + "?access_token=" + accessToken + "&type=" + type.type();
			return WeixinInterfaceHelper.upload(url, bytes, "media", filename, Media.class);
		}
		return null;
	}
	
	/**
	 * 获取临时素材
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public BytesFile getMedia(String accessToken, String mediaId) {
		String url = WEIXIN_MEDIA_GET_LINK + "?access_token=" + accessToken + "&media_id=" + mediaId;
		return WeixinInterfaceHelper.download(url);
	}
	
	/**
	 * 获取临时视频素材下载链接
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public String getVideoMediaLink(String accessToken, String mediaId) {
		String url = WEIXIN_MEDIA_GET_LINK + "?access_token=" + accessToken + "&media_id=" + mediaId;
		return WeixinInterfaceHelper.get(url, VideoMediaURL.class).getVideoUrl();
	}
	
	/**
	 * 获取临时视频素材下载
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public BytesFile getVideoMedia(String accessToken, String mediaId) {
		String videoUrl = getVideoMediaLink(accessToken, mediaId);
		return WeixinInterfaceHelper.download(videoUrl);
	}
	
	/**
	 * 获取高清语音素材
	 * 公众号可以使用本接口获取从JSSDK的uploadVoice接口上传的临时语音素材，格式为speex，16K采样率。
	 * 该音频比上文的临时素材获取接口（格式为amr，8K采样率）更加清晰，适合用作语音识别等对音质要求较高的业务。
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public BytesFile getJssdkVoice(String accessToken, String mediaId) {
		String url = WEIXIN_MEDIA_GET_JSSDK_LINK + "?access_token=" + accessToken + "&media_id=" + mediaId;
		return WeixinInterfaceHelper.download(url);
	}

}
