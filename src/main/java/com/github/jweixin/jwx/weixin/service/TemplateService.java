package com.github.jweixin.jwx.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.jweixin.jwx.message.template.Industry;
import com.github.jweixin.jwx.message.template.Template;
import com.github.jweixin.jwx.message.template.TemplateList;
import com.github.jweixin.jwx.message.template.TemplateMessage;
import com.github.jweixin.jwx.message.template.TemplateMessageReturnCode;
import com.github.jweixin.jwx.message.template.TemplateReturnCode;
import com.github.jweixin.jwx.util.IncorrectWeixinReturnCodeException;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 模板消息服务
 * @author Administrator
 *
 */
@Service
public class TemplateService {
	/**
	 * 设置所属行业链接
	 */
	public final static String WEIXIN_TEMPLATE_SET_INDUSTRY_LINK = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
	/**
	 * 获取设置的行业信息链接
	 */
	public final static String WEIXIN_TEMPLATE_GET_INDUSTRY_LINK = "https://api.weixin.qq.com/cgi-bin/template/get_industry";
	/**
	 * 获得模板ID链接
	 */
	public final static String WEIXIN_TEMPLATE_QUERY_TEMPLATE_ID_LINK = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";
	/**
	 * 获取模板列表链接
	 */
	public final static String WEIXIN_TEMPLATE_GET_ALL_TEMPLATE_LINK = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template";
	/**
	 * 删除模板链接
	 */
	public final static String WEIXIN_TEMPLATE_DEL_TEMPLATE_LINK = "https://api.weixin.qq.com/cgi-bin/template/del_private_template";
	/**
	 * 发送模板消息链接
	 */
	public final static String WEIXIN_TEMPLATE_SEND_MESSAGE_LINK = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	/**
	 * 设置所属行业
	 * @param accessToken
	 * @param industryId1 模板消息所属行业编号1
	 * @param industryId2 模板消息所属行业编号2
	 * @return
	 */
	public ReturnCode setIndustry(String accessToken, String industryId1, String industryId2) {
		if(industryId1 == null){
			throw new IllegalArgumentException("第1个公众号模板消息所属行业编号不能为空。");
		}
		String url = WEIXIN_TEMPLATE_SET_INDUSTRY_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("industry_id1", industryId1);
		if(industryId2 != null){
			map.put("industry_id2", industryId2);
		}
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 获取设置的行业信息
	 * @param accessToken
	 * @return
	 */
	public Industry getIndustry(String accessToken) {
		String url = WEIXIN_TEMPLATE_GET_INDUSTRY_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, Industry.class);
	}
	
	/**
	 * 获得模板ID
	 * @param accessToken
	 * @param templateIdShort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return
	 */
	public String queryTemplateId(String accessToken, String templateIdShort) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("template_id_short", templateIdShort);
		String url = WEIXIN_TEMPLATE_QUERY_TEMPLATE_ID_LINK + "?access_token=" + accessToken;
		TemplateReturnCode trc = WeixinInterfaceHelper.post(url, map, TemplateReturnCode.class);
		if(trc.check()){
			return trc.getTemplateId();
		} else {
			throw new IncorrectWeixinReturnCodeException("根据模板库中的模板编号" + templateIdShort + "获取模板id失败", trc);
		}
	}
	
	/**
	 * 获取模板列表
	 * @param accessToken
	 * @return
	 */
	public List<Template> getAllTemplate(String accessToken) {
		String url = WEIXIN_TEMPLATE_GET_ALL_TEMPLATE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.get(url, TemplateList.class).getTemplateList();
	}
	
	/**
	 * 删除模板
	 * @param accessToken
	 * @param templateId
	 * @return
	 */
	public ReturnCode delTemplate(String accessToken, String templateId) {
		String url = WEIXIN_TEMPLATE_DEL_TEMPLATE_LINK + "?access_token=" + accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("template_id", templateId);
		return WeixinInterfaceHelper.post(url, map, ReturnCode.class);
	}
	
	/**
	 * 发送模板消息
	 * @param accessToken
	 * @param templateMessage
	 * @return
	 */
	public TemplateMessageReturnCode sendMessage(String accessToken, TemplateMessage templateMessage) {
		String url = WEIXIN_TEMPLATE_SEND_MESSAGE_LINK + "?access_token=" + accessToken;
		return WeixinInterfaceHelper.post(url, templateMessage, TemplateMessageReturnCode.class);
	}

}
