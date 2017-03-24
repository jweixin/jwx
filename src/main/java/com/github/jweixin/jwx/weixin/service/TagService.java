package com.github.jweixin.jwx.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.util.StringUtil;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.user.Tag;
import com.github.jweixin.jwx.weixin.entity.user.TagIdLabel;
import com.github.jweixin.jwx.weixin.entity.user.TagLabel;
import com.github.jweixin.jwx.weixin.entity.user.TagList;
import com.github.jweixin.jwx.weixin.entity.user.TagUserList;

/**
 * 标签服务
 * @author Administrator
 *
 */
@Service
public class TagService {
	/**
	 * 创建标签链接
	 */
	public final static String WEIXIN_TAG_CREATE_LINK = "https://api.weixin.qq.com/cgi-bin/tags/create";
	/**
	 * 获取公众号已创建的标签链接
	 */
	public final static String WEIXIN_TAGS_GET_LINK = "https://api.weixin.qq.com/cgi-bin/tags/get";
	/**
	 * 编辑标签链接
	 */
	public final static String WEIXIN_TAG_UPDATE_LINK = "https://api.weixin.qq.com/cgi-bin/tags/update";
	/**
	 * 删除标签链接
	 */
	public final static String WEIXIN_TAG_DELETE_LINK = "https://api.weixin.qq.com/cgi-bin/tags/delete";
	/**
	 * 获取标签下粉丝列表链接
	 */
	public final static String WEIXIN_TAG_USER_GET_LINK = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
	/**
	 * 批量为用户打标签链接
	 */
	public final static String WEIXIN_TAG_USER_BATCH_TAGGING_LINK = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
	/**
	 * 批量为用户取消标签链接
	 */
	public final static String WEIXIN_TAG_USER_BATCH_UNTAGGING_LINK = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
	/**
	 * 获取用户身上的标签列表链接
	 */
	public final static String WEIXIN_USER_TAGID_LIST_LINK = "https://api.weixin.qq.com/cgi-bin/tags/getidlist";
	
	/**
	 * 创建标签
	 * 一个公众号，最多可以创建100个标签。
	 * @param accessToken
	 * @param tag
	 * @return
	 */
	public Tag createTag(String accessToken, Tag tag) {
		String url = WEIXIN_TAG_CREATE_LINK + "?access_token=" + accessToken;
		TagLabel label = new TagLabel();
		label.setTag(tag);
		return WeixinInterfaceHelper.post(url, label, TagLabel.class).getTag();
	}
	
	/**
	 * 获取公众号已创建的标签
	 * @param accessToken
	 * @return
	 */
	public List<Tag> getTags(String accessToken) {
		String url = WEIXIN_TAGS_GET_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, TagList.class).getTags();
	}
	
	/**
	 * 编辑标签
	 * @param accessToken
	 * @param tag
	 * @return
	 */
	public ReturnCode updateTag(String accessToken, Tag tag) {
		String url = WEIXIN_TAG_UPDATE_LINK + "?access_token=" + accessToken;
		TagLabel label = new TagLabel();
		label.setTag(tag);
		return WeixinInterfaceHelper.post(url, label, ReturnCode.class);
	}
	
	/**
	 * 删除标签
	 * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。
	 * @param accessToken
	 * @param tag
	 * @return
	 */
	public ReturnCode deleteTag(String accessToken, int tagId) {
		String url = WEIXIN_TAG_DELETE_LINK + "?access_token=" + accessToken;
		Tag tag = new Tag();
		tag.setId(tagId);
		TagLabel label = new TagLabel();
		label.setTag(tag);
		return WeixinInterfaceHelper.post(url, label, ReturnCode.class);
	}
	
	/**
	 * 获取标签下粉丝列表
	 * @param accessToken
	 * @param tagId
	 * @param nextOpenid
	 * @return
	 */
	public TagUserList getTagUserList(String accessToken, int tagId, String nextOpenid) {
		if(nextOpenid==null){
			nextOpenid = "";
		}
		String url = WEIXIN_TAG_USER_GET_LINK + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tagid", tagId);
		map.put("next_openid", nextOpenid);
		return WeixinInterfaceHelper.post(url, map, TagUserList.class);
	}
	
	/**
	 * 批量为用户打标签
	 * @param accessToken
	 * @param tagId
	 * @param openIdList
	 * @return
	 */
	public ReturnCode batchTagging(String accessToken, int tagId, List<String> openIdList) {
		if(openIdList==null || openIdList.size()<1){
			throw new IllegalArgumentException("用户的openIdList不能为空。");
		}
		String url = WEIXIN_TAG_USER_BATCH_TAGGING_LINK + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tagid", tagId);
		map.put("openid_list", openIdList);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 为用户打标签
	 * @param accessToken
	 * @param tagId
	 * @param openId
	 * @return
	 */
	public ReturnCode userTagging(String accessToken, int tagId, String openId) {
		List<String> openIdList = new ArrayList<String>();
		openIdList.add(openId);
		return batchTagging(accessToken, tagId, openIdList);
	}
	
	/**
	 * 批量为用户取消标签
	 * @param accessToken
	 * @param tagId
	 * @param openIdList
	 * @return
	 */
	public ReturnCode batchUntagging(String accessToken, int tagId, List<String> openIdList) {
		if(openIdList==null || openIdList.size()<1){
			throw new IllegalArgumentException("用户的openIdList不能为空。");
		}
		String url = WEIXIN_TAG_USER_BATCH_UNTAGGING_LINK + "?access_token=" + accessToken;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tagid", tagId);
		map.put("openid_list", openIdList);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 为用户取消标签
	 * @param accessToken
	 * @param tagId
	 * @param openId
	 * @return
	 */
	public ReturnCode userUntagging(String accessToken, int tagId, String openId) {
		List<String> openIdList = new ArrayList<String>();
		openIdList.add(openId);
		return batchUntagging(accessToken, tagId, openIdList);
	}
	
	/**
	 * 获取用户身上的标签列表
	 * @param accessToken
	 * @param openId
	 * @return 用户标签数组
	 */
	public int[] getUserTagId(String accessToken, String openId) {
		if(StringUtil.isNull(openId)){
			throw new IllegalArgumentException("用户的openId不能为空。");
		}
		String url = WEIXIN_USER_TAGID_LIST_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openId);
		return WeixinInterfaceHelper.post(url, map, TagIdLabel.class).getTagidList();
	}

}
