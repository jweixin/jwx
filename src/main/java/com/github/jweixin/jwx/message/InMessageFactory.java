package com.github.jweixin.jwx.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.github.jweixin.jwx.context.WeixinInMsgHandleException;
import com.github.jweixin.jwx.message.request.InImageMessage;
import com.github.jweixin.jwx.message.request.InLinkMessage;
import com.github.jweixin.jwx.message.request.InLocationMessage;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InPlainMessage;
import com.github.jweixin.jwx.message.request.InShortVideoMessage;
import com.github.jweixin.jwx.message.request.InTextMessage;
import com.github.jweixin.jwx.message.request.InVideoMessage;
import com.github.jweixin.jwx.message.request.InVoiceMessage;
import com.github.jweixin.jwx.message.request.InVoiceRecognitionMessage;
import com.github.jweixin.jwx.message.request.event.CopyrightCheckResult;
import com.github.jweixin.jwx.message.request.event.InAnnualRenewEvent;
import com.github.jweixin.jwx.message.request.event.InClickEvent;
import com.github.jweixin.jwx.message.request.event.InEvent;
import com.github.jweixin.jwx.message.request.event.InLocationEvent;
import com.github.jweixin.jwx.message.request.event.InLocationSelectEvent;
import com.github.jweixin.jwx.message.request.event.InMassSendJobFinishEvent;
import com.github.jweixin.jwx.message.request.event.InNamingVerifyFailEvent;
import com.github.jweixin.jwx.message.request.event.InNamingVerifySuccessEvent;
import com.github.jweixin.jwx.message.request.event.InPicPhotoOrAlbumEvent;
import com.github.jweixin.jwx.message.request.event.InPicSysphotoEvent;
import com.github.jweixin.jwx.message.request.event.InPicWeixinEvent;
import com.github.jweixin.jwx.message.request.event.InQualificationVerifyFailEvent;
import com.github.jweixin.jwx.message.request.event.InQualificationVerifySuccessEvent;
import com.github.jweixin.jwx.message.request.event.InScanEvent;
import com.github.jweixin.jwx.message.request.event.InScanSubscribeEvent;
import com.github.jweixin.jwx.message.request.event.InScancodePushEvent;
import com.github.jweixin.jwx.message.request.event.InScancodeWaitmsgEvent;
import com.github.jweixin.jwx.message.request.event.InSubscribeEvent;
import com.github.jweixin.jwx.message.request.event.InTemplateSendJobFinishEvent;
import com.github.jweixin.jwx.message.request.event.InUnsubscribeEvent;
import com.github.jweixin.jwx.message.request.event.InVerifyExpiredEvent;
import com.github.jweixin.jwx.message.request.event.InViewEvent;
import com.github.jweixin.jwx.message.request.event.PicListItem;
import com.github.jweixin.jwx.message.request.event.ResultListItem;
import com.github.jweixin.jwx.message.request.event.ScanCodeInfo;
import com.github.jweixin.jwx.message.request.event.SendLocationInfo;
import com.github.jweixin.jwx.message.request.event.SendPicsInfo;
import com.github.jweixin.jwx.util.StringUtil;

/**
 * 微信请求消息工厂
 * @author Administrator
 *
 */
public class InMessageFactory {
	/* 文本消息类型 */
	public static final String REQUEST_TEXT_MESSAGE_TYPE = "text";
	/* 图片消息类型 */
	public static final String REQUEST_IMAGE_MESSAGE_TYPE = "image";
	/* 语音消息类型 */
	public static final String REQUEST_VOICE_MESSAGE_TYPE = "voice";
	/* 视频消息类型 */
	public static final String REQUEST_VIDEO_MESSAGE_TYPE = "video";
	/* 小视频消息类型 */
	public static final String REQUEST_SHORT_VIDEO_MESSAGE_TYPE = "shortvideo";
	/* 地理位置消息类型 */
	public static final String REQUEST_LOCATION_MESSAGE_TYPE = "location";
	/* 链接消息类型 */
	public static final String REQUEST_LINK_MESSAGE_TYPE = "link";
	
	/* 事件消息类型 */
	public static final String REQUEST_EVENT_MESSAGE_TYPE = "event";
	/* 关注事件消息类型，如果有EventKey(事件KEY值)，并且以qrscene_为前缀，后面为二维码的参数值，则为用户未关注时，进行关注后的事件推送 */
	public static final String REQUEST_EVENT_SUBSCRIBE_MESSAGE_TYPE = "subscribe";
	/* 取消关注事件消息类型 */
	public static final String REQUEST_EVENT_UNSUBSCRIBE_MESSAGE_TYPE = "unsubscribe";
	/* 用户已关注时的事件消息类型 */
	public static final String REQUEST_EVENT_SCAN_MESSAGE_TYPE = "SCAN";
	/* 上报地理位置事件消息类型 */
	public static final String REQUEST_EVENT_LOCATION_MESSAGE_TYPE = "LOCATION";
	/* 自定义菜单事件消息类型 */
	public static final String REQUEST_EVENT_CLICK_MESSAGE_TYPE = "CLICK";
	/* 点击菜单跳转链接时的事件推送 */
	public static final String REQUEST_EVENT_VIEW_MESSAGE_TYPE = "VIEW";
	/* 弹出地理位置选择器的事件推送 */
	public static final String REQUEST_EVENT_LOCATION_SELECT_MESSAGE_TYPE = "location_select";
	/* 弹出拍照或者相册发图的事件推送 */
	public static final String REQUEST_EVENT_PIC_PHOTO_OR_ALBUM_MESSAGE_TYPE = "pic_photo_or_album";
	/* 弹出系统拍照发图的事件推送 */
	public static final String REQUEST_EVENT_PIC_SYSPHOTO_MESSAGE_TYPE = "pic_sysphoto";
	/* 弹出微信相册发图器的事件推送 */
	public static final String REQUEST_EVENT_PIC_WEIXIN_MESSAGE_TYPE = "pic_weixin";
	/* 扫码推事件的事件推送 */
	public static final String REQUEST_EVENT_SCANCODE_PUSH_MESSAGE_TYPE = "scancode_push";
	/* 扫码推事件且弹出“消息接收中”提示框的事件推送 */
	public static final String REQUEST_EVENT_SCANCODE_WAITMSG_MESSAGE_TYPE = "scancode_waitmsg";
	/* 事件推送群发结果 */
	public static final String REQUEST_EVENT_MASS_SEND_JOB_FINISH_MESSAGE_TYPE = "MASSSENDJOBFINISH";
	/* 事件推送模板消息发送结果 */
	public static final String REQUEST_EVENT_TEMPLATE_SEND_JOB_FINISH_MESSAGE_TYPE = "TEMPLATESENDJOBFINISH";
	/* 资质认证成功事件 */
	public static final String REQUEST_EVENT_QUALIFICATION_VERIFY_SUCCESS_MESSAGE_TYPE = "qualification_verify_success";
	/* 资质认证失败事件 */
	public static final String REQUEST_EVENT_QUALIFICATION_VERIFY_FAIL_MESSAGE_TYPE = "qualification_verify_fail";
	/* 名称认证成功事件 */
	public static final String REQUEST_EVENT_NAMING_VERIFY_SUCCESS_MESSAGE_TYPE = "naming_verify_success";
	/* 名称认证失败事件 */
	public static final String REQUEST_EVENT_NAMING_VERIFY_FAIL_MESSAGE_TYPE = "naming_verify_fail";
	/* 年审通知事件 */
	public static final String REQUEST_EVENT_ANNUAL_RENEW_MESSAGE_TYPE = "annual_renew";
	/* 认证过期失效通知事件 */
	public static final String REQUEST_EVENT_VERIFY_EXPIRED_MESSAGE_TYPE = "verify_expired";

	private static InMessageFactory factory = new InMessageFactory();

	private InMessageFactory() {
	}
	
	public static InMessageFactory getInstance() {
		return factory;
	}
	
	/**
	 * 生成请求消息
	 * @param xmlText
	 * @return
	 * @throws DocumentException
	 * @throws WeixinInMsgHandleException 
	 */
	public InMessage creator(String xmlText) throws DocumentException, WeixinInMsgHandleException{
		// 获取xml文本document对象
		Document document = DocumentHelper.parseText(xmlText);
		// 得到 xml 根元素
		Element root = document.getRootElement();
		String msgType = root.element("MsgType").getTextTrim();
		
		InMessage in = null;
		
		if(REQUEST_EVENT_MESSAGE_TYPE.equals(msgType)){
			in = creatEventMsg(root);
		} else {
			in = creatPlainMsg(root, msgType);
		}
		
		if(in == null){
			throw new WeixinInMsgHandleException("无法识别的消息请求类型");
		}
		
		return in;
	}
	
	/**
	 * 建立普通消息
	 * @param root
	 * @param msgType
	 * @return
	 */
	private InMessage creatPlainMsg(Element root, String msgType) {
		InPlainMessage in = null;
		
		if(REQUEST_TEXT_MESSAGE_TYPE.equals(msgType)){
			InTextMessage text = new InTextMessage();
			text.setContent(root.element("Content").getTextTrim());
			in = text;
		} else if(REQUEST_IMAGE_MESSAGE_TYPE.equals(msgType)){
			InImageMessage image = new InImageMessage();
			image.setMediaId(root.element("MediaId").getTextTrim());
			image.setPicUrl(root.element("PicUrl").getTextTrim());
			in = image;
		} else if(REQUEST_VOICE_MESSAGE_TYPE.equals(msgType)){
			Element recElement = root.element("Recognition");
			if(recElement!=null && !StringUtil.isNull(recElement.getTextTrim())){
				InVoiceRecognitionMessage rec  = new InVoiceRecognitionMessage();
				rec.setMediaId(root.element("MediaId").getTextTrim());
				rec.setFormat(root.element("Format").getTextTrim());
				rec.setRecognition(recElement.getTextTrim());
				in = rec;
			}else{
				InVoiceMessage voice = new InVoiceMessage();
				voice.setMediaId(root.element("MediaId").getTextTrim());
				voice.setFormat(root.element("Format").getTextTrim());
				in = voice;
			}
		} else if(REQUEST_VIDEO_MESSAGE_TYPE.equals(msgType)){
			InVideoMessage video = new InVideoMessage();
			video.setMediaId(root.element("MediaId").getTextTrim());
			video.setThumbMediaId(root.element("ThumbMediaId").getTextTrim());
			in = video;
		} else if(REQUEST_SHORT_VIDEO_MESSAGE_TYPE.equals(msgType)){
			InShortVideoMessage shortVideo = new InShortVideoMessage();
			shortVideo.setMediaId(root.element("MediaId").getTextTrim());
			shortVideo.setThumbMediaId(root.element("ThumbMediaId").getTextTrim());
			in = shortVideo;
		} else if(REQUEST_LOCATION_MESSAGE_TYPE.equals(msgType)){
			InLocationMessage location = new InLocationMessage();
			location.setLocationX(root.element("Location_X").getTextTrim());
			location.setLocationY(root.element("Location_Y").getTextTrim());
			location.setScale(root.element("Scale").getTextTrim());
			location.setLabel(root.element("Label").getTextTrim());
			in = location;
		} else if(REQUEST_LINK_MESSAGE_TYPE.equals(msgType)){
			InLinkMessage link = new InLinkMessage();
			link.setTitle(root.element("Title").getTextTrim());
			link.setDescription(root.element("Description").getTextTrim());
			link.setUrl(root.element("Url").getTextTrim());
			in = link;
		}
		
		if(in != null){
			in.setToUserName(root.element("ToUserName").getTextTrim());
			in.setFromUserName(root.element("FromUserName").getTextTrim());
			in.setCreateTime(Long.parseLong(root.element("CreateTime").getTextTrim()));
			in.setMsgId(Long.parseLong(root.element("MsgId").getTextTrim()));
		}
		
		return in;
	}
	
	/**
	 * 建立事件消息
	 * @param root
	 * @return
	 */
	private InMessage creatEventMsg(Element root) {
		InEvent in = null;
		String event = root.element("Event").getTextTrim();
		
		if(REQUEST_EVENT_SUBSCRIBE_MESSAGE_TYPE.equals(event)){
			Element keyElement = root.element("EventKey");
			if(keyElement != null && keyElement.getTextTrim().startsWith("qrscene_")) {
				InScanSubscribeEvent subscribeScan = new InScanSubscribeEvent();
				subscribeScan.setEventKey(keyElement.getTextTrim());
				subscribeScan.setTicket(root.element("Ticket").getTextTrim());
				in = subscribeScan;
			}else{
				InSubscribeEvent subscribe = new InSubscribeEvent();
				in = subscribe;
			}
		} else if(REQUEST_EVENT_UNSUBSCRIBE_MESSAGE_TYPE.equals(event)){
			InUnsubscribeEvent unsubscribe = new InUnsubscribeEvent();
			in = unsubscribe;
		} else if(REQUEST_EVENT_SCAN_MESSAGE_TYPE.equals(event)){
			InScanEvent scan = new InScanEvent();
			scan.setEventKey(root.element("EventKey").getTextTrim());
			scan.setTicket(root.element("Ticket").getTextTrim());
			in = scan;
		} else if(REQUEST_EVENT_LOCATION_MESSAGE_TYPE.equals(event)){
			InLocationEvent location = new InLocationEvent();
			location.setLatitude(root.element("Latitude").getTextTrim());
			location.setLongitude(root.element("Longitude").getTextTrim());
			location.setPrecision(root.element("Precision").getTextTrim());
			in = location;
		} else if(REQUEST_EVENT_CLICK_MESSAGE_TYPE.equals(event)){
			InClickEvent click = new InClickEvent();
			click.setEventKey(root.element("EventKey").getTextTrim());
			in = click;
		} else if(REQUEST_EVENT_VIEW_MESSAGE_TYPE.equals(event)){
			InViewEvent view = new InViewEvent();
			view.setEventKey(root.element("EventKey").getTextTrim());
			Element meunIdElement = root.element("MenuId");
			if(meunIdElement!=null && !StringUtil.isNull(meunIdElement.getTextTrim())){
				view.setMenuId(Long.parseLong(meunIdElement.getTextTrim()));
			}
			in = view;
		} else if(REQUEST_EVENT_LOCATION_SELECT_MESSAGE_TYPE.equals(event)){
			InLocationSelectEvent locationSelect = new InLocationSelectEvent();
			locationSelect.setEventKey(root.element("EventKey").getTextTrim());
			SendLocationInfo info = new SendLocationInfo();
			Element infoElement = root.element("SendLocationInfo");
			info.setLocationX(infoElement.element("Location_X").getTextTrim());
			info.setLocationY(infoElement.element("Location_Y").getTextTrim());
			info.setScale(infoElement.element("Scale").getTextTrim());
			info.setLabel(infoElement.element("Label").getTextTrim());
			Element poinameElement = infoElement.element("Poiname");
			if(poinameElement!=null && !StringUtil.isNull(poinameElement.getTextTrim())){
				info.setPoiname(poinameElement.getTextTrim());
			}
			locationSelect.setSendLocationInfo(info);
			in = locationSelect;
		} else if(REQUEST_EVENT_PIC_PHOTO_OR_ALBUM_MESSAGE_TYPE.equals(event)){
			InPicPhotoOrAlbumEvent picPhotoOrAlbum = new InPicPhotoOrAlbumEvent();
			picPhotoOrAlbum.setEventKey(root.element("EventKey").getTextTrim());
			Element sendPicsInfoElement = root.element("SendPicsInfo");
			picPhotoOrAlbum.setSendPicsInfo(getSendPicsInfo(sendPicsInfoElement));
			in = picPhotoOrAlbum;
		} else if(REQUEST_EVENT_PIC_SYSPHOTO_MESSAGE_TYPE.equals(event)){
			InPicSysphotoEvent picSysphoto = new InPicSysphotoEvent();
			picSysphoto.setEventKey(root.element("EventKey").getTextTrim());
			Element sendPicsInfoElement = root.element("SendPicsInfo");
			picSysphoto.setSendPicsInfo(getSendPicsInfo(sendPicsInfoElement));
			in = picSysphoto;
		} else if(REQUEST_EVENT_PIC_WEIXIN_MESSAGE_TYPE.equals(event)){
			InPicWeixinEvent picWeixin = new InPicWeixinEvent();
			picWeixin.setEventKey(root.element("EventKey").getTextTrim());
			Element sendPicsInfoElement = root.element("SendPicsInfo");
			picWeixin.setSendPicsInfo(getSendPicsInfo(sendPicsInfoElement));
			in = picWeixin;
		} else if(REQUEST_EVENT_SCANCODE_PUSH_MESSAGE_TYPE.equals(event)){
			InScancodePushEvent scancodePush = new InScancodePushEvent();
			scancodePush.setEventKey(root.element("EventKey").getTextTrim());
			Element scanCodeInfoElement = root.element("ScanCodeInfo");
			scancodePush.setScanCodeInfo(getScanCodeInfo(scanCodeInfoElement));
			in = scancodePush;
		} else if(REQUEST_EVENT_SCANCODE_WAITMSG_MESSAGE_TYPE.equals(event)){
			InScancodeWaitmsgEvent scancodeWaitmsg = new InScancodeWaitmsgEvent();
			scancodeWaitmsg.setEventKey(root.element("EventKey").getTextTrim());
			Element scanCodeInfoElement = root.element("ScanCodeInfo");
			scancodeWaitmsg.setScanCodeInfo(getScanCodeInfo(scanCodeInfoElement));
			in = scancodeWaitmsg;
		} else if(REQUEST_EVENT_MASS_SEND_JOB_FINISH_MESSAGE_TYPE.equals(event)){
			InMassSendJobFinishEvent massSendJobFinish = new InMassSendJobFinishEvent();
			massSendJobFinish.setMsgId(Long.parseLong(root.element("MsgID").getTextTrim()));
			massSendJobFinish.setStatus(root.element("Status").getTextTrim());
			massSendJobFinish.setTotalCount(Integer.parseInt(root.element("TotalCount").getTextTrim()));
			massSendJobFinish.setFilterCount(Integer.parseInt(root.element("FilterCount").getTextTrim()));
			massSendJobFinish.setSentCount(Integer.parseInt(root.element("SentCount").getTextTrim()));
			massSendJobFinish.setErrorCount(Integer.parseInt(root.element("ErrorCount").getTextTrim()));
			Element copyrightCheckResultElement = root.element("CopyrightCheckResult");
			massSendJobFinish.setCopyrightCheckResult(getCopyrightCheckResult(copyrightCheckResultElement));
			in = massSendJobFinish;
		} else if(REQUEST_EVENT_TEMPLATE_SEND_JOB_FINISH_MESSAGE_TYPE.equals(event)){
			InTemplateSendJobFinishEvent templateSendJobFinish = new InTemplateSendJobFinishEvent();
			templateSendJobFinish.setMsgId(Long.parseLong(root.element("MsgID").getTextTrim()));
			templateSendJobFinish.setStatus(root.element("Status").getTextTrim());
			in = templateSendJobFinish;
		} else if(REQUEST_EVENT_QUALIFICATION_VERIFY_SUCCESS_MESSAGE_TYPE.equals(event)){
			InQualificationVerifySuccessEvent qualificationVerifySuccess = new InQualificationVerifySuccessEvent();
			qualificationVerifySuccess.setExpiredTime(Long.parseLong(root.element("ExpiredTime").getTextTrim()));
			in = qualificationVerifySuccess;
		} else if(REQUEST_EVENT_QUALIFICATION_VERIFY_FAIL_MESSAGE_TYPE.equals(event)){
			InQualificationVerifyFailEvent qualificationVerifyFail = new InQualificationVerifyFailEvent();
			qualificationVerifyFail.setFailTime(Long.parseLong(root.element("FailTime").getTextTrim()));
			qualificationVerifyFail.setFailReason(root.element("FailReason").getTextTrim());
			in = qualificationVerifyFail;
		} else if(REQUEST_EVENT_NAMING_VERIFY_SUCCESS_MESSAGE_TYPE.equals(event)){
			InNamingVerifySuccessEvent namingVerifySuccess = new InNamingVerifySuccessEvent();
			namingVerifySuccess.setExpiredTime(Long.parseLong(root.element("ExpiredTime").getTextTrim()));
			in = namingVerifySuccess;
		} else if(REQUEST_EVENT_NAMING_VERIFY_FAIL_MESSAGE_TYPE.equals(event)){
			InNamingVerifyFailEvent namingVerifyFail = new InNamingVerifyFailEvent();
			namingVerifyFail.setFailTime(Long.parseLong(root.element("FailTime").getTextTrim()));
			namingVerifyFail.setFailReason(root.element("FailReason").getTextTrim());
			in = namingVerifyFail;
		} else if(REQUEST_EVENT_ANNUAL_RENEW_MESSAGE_TYPE.equals(event)){
			InAnnualRenewEvent annualRenew = new InAnnualRenewEvent();
			annualRenew.setExpiredTime(Long.parseLong(root.element("ExpiredTime").getTextTrim()));
			in = annualRenew;
		} else if(REQUEST_EVENT_VERIFY_EXPIRED_MESSAGE_TYPE.equals(event)){
			InVerifyExpiredEvent verifyExpired = new InVerifyExpiredEvent();
			verifyExpired.setExpiredTime(Long.parseLong(root.element("ExpiredTime").getTextTrim()));
			in = verifyExpired;
		}
		
		if(in != null){
			in.setToUserName(root.element("ToUserName").getTextTrim());
			in.setFromUserName(root.element("FromUserName").getTextTrim());
			in.setCreateTime(Long.parseLong(root.element("CreateTime").getTextTrim()));
		}
		
		return in;
	}
	
	/**
	 * 生成版权检查结果
	 * @param copyrightCheckResultElement
	 * @return
	 */
	private CopyrightCheckResult getCopyrightCheckResult(Element copyrightCheckResultElement) {
		CopyrightCheckResult result = new CopyrightCheckResult();
		result.setCount(Integer.parseInt(copyrightCheckResultElement.element("Count").getTextTrim()));
		result.setCheckState(Integer.parseInt(copyrightCheckResultElement.element("CheckState").getTextTrim()));
		List<ResultListItem> resultList = new ArrayList<ResultListItem>();
		Element resultListElement = copyrightCheckResultElement.element("ResultList");
		@SuppressWarnings("unchecked")
		Iterator<Element> iter = resultListElement.elements("item").iterator();
		while(iter.hasNext()){
			Element itemElement = iter.next();
			ResultListItem item = new ResultListItem();
			item.setArticleIdx(Integer.parseInt(itemElement.element("ArticleIdx").getTextTrim()));
			item.setUserDeclareState(Integer.parseInt(itemElement.element("UserDeclareState").getTextTrim()));
			item.setAuditState(Integer.parseInt(itemElement.element("AuditState").getTextTrim()));
			item.setOriginalArticleUrl(itemElement.element("OriginalArticleUrl").getTextTrim());
			item.setOriginalArticleType(Integer.parseInt(itemElement.element("OriginalArticleType").getTextTrim()));
			item.setCanReprint(Integer.parseInt(itemElement.element("CanReprint").getTextTrim()));
			item.setNeedReplaceContent(Integer.parseInt(itemElement.element("NeedReplaceContent").getTextTrim()));
			item.setNeedShowReprintSource(Integer.parseInt(itemElement.element("NeedShowReprintSource").getTextTrim()));
			resultList.add(item);
		}
		result.setResultList(resultList);
		return result;
	}

	/**
	 * 生成扫描信息
	 * @param scanCodeInfoElement
	 * @return
	 */
	private ScanCodeInfo getScanCodeInfo(Element scanCodeInfoElement) {
		ScanCodeInfo info = new ScanCodeInfo();
		info.setScanType(scanCodeInfoElement.element("ScanType").getTextTrim());
		info.setScanResult(scanCodeInfoElement.element("ScanResult").getTextTrim());
		return info;
	}

	/**
	 * 生成发送的图片信息
	 * @param sendPicsInfoElement
	 * @return
	 */
	private SendPicsInfo getSendPicsInfo(Element sendPicsInfoElement) {
		SendPicsInfo info = new SendPicsInfo();
		info.setCount(Integer.parseInt(sendPicsInfoElement.element("Count").getTextTrim()));
		List<PicListItem> picList = new ArrayList<PicListItem>();
		Element picListElement = sendPicsInfoElement.element("PicList");
		@SuppressWarnings("unchecked")
		Iterator<Element> iter = picListElement.elements("item").iterator();
		while(iter.hasNext()){
			Element itemElement = iter.next();
			PicListItem item = new PicListItem();
			item.setPicMd5Sum(itemElement.element("PicMd5Sum").getTextTrim());
			picList.add(item);
		}
		info.setPicList(picList);
		return info;
	}

}
