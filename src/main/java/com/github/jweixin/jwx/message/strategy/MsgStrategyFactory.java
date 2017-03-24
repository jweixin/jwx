package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.github.jweixin.jwx.message.annotation.ClickEvent;
import com.github.jweixin.jwx.message.annotation.CopyrightEvent;
import com.github.jweixin.jwx.message.annotation.DefaultHandler;
import com.github.jweixin.jwx.message.annotation.JobFinishEvent;
import com.github.jweixin.jwx.message.annotation.LinkMsg;
import com.github.jweixin.jwx.message.annotation.LocationEvent;
import com.github.jweixin.jwx.message.annotation.LocationMsg;
import com.github.jweixin.jwx.message.annotation.MediaMsg;
import com.github.jweixin.jwx.message.annotation.MenuEvent;
import com.github.jweixin.jwx.message.annotation.ScanEvent;
import com.github.jweixin.jwx.message.annotation.ScanSubscribeEvent;
import com.github.jweixin.jwx.message.annotation.SubscribeEvent;
import com.github.jweixin.jwx.message.annotation.TextMsg;
import com.github.jweixin.jwx.message.annotation.UnsubscribeEvent;
import com.github.jweixin.jwx.message.annotation.ViewEvent;
import com.github.jweixin.jwx.message.request.InImageMessage;
import com.github.jweixin.jwx.message.request.InLinkMessage;
import com.github.jweixin.jwx.message.request.InLocationMessage;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InShortVideoMessage;
import com.github.jweixin.jwx.message.request.InTextMessage;
import com.github.jweixin.jwx.message.request.InVideoMessage;
import com.github.jweixin.jwx.message.request.InVoiceMessage;
import com.github.jweixin.jwx.message.request.InVoiceRecognitionMessage;
import com.github.jweixin.jwx.message.request.event.InAnnualRenewEvent;
import com.github.jweixin.jwx.message.request.event.InClickEvent;
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

/**
 * 微信消息策略工厂
 * @author Administrator
 *
 */
public class MsgStrategyFactory {

	private static MsgStrategyFactory factory = new MsgStrategyFactory();

	private MsgStrategyFactory() {
	}
	/**
	 * 消息处理策略map
	 */
	private static Map<String, MsgStrategy> strategyMap = new HashMap<String, MsgStrategy>();
	/**
	 * 异常处理策略
	 */
	private static ExceptionHandlerStrategy exceptionHandlerStrategy = new ExceptionHandlerStrategy();
	
	static {
		// 文本消息策略
		TextMsgStrategy textMsgStrategy = new TextMsgStrategy();
		strategyMap.put(TextMsg.class.getName(), textMsgStrategy);
		strategyMap.put(InTextMessage.class.getName(), textMsgStrategy);
		
		//点击菜单事件策略
		ClickEventStrategy clickEventStrategy = new ClickEventStrategy();
		strategyMap.put(ClickEvent.class.getName(), clickEventStrategy);
		strategyMap.put(InClickEvent.class.getName(), clickEventStrategy);
		
		//点击菜单跳转链接时的事件策略
		ViewEventStrategy viewEventStrategy = new ViewEventStrategy();
		strategyMap.put(ViewEvent.class.getName(), viewEventStrategy);
		strategyMap.put(InViewEvent.class.getName(), viewEventStrategy);
		
		// 唯一消息或事件策略
		SoleStrategy soleStrategy = new SoleStrategy();
		strategyMap.put(LocationMsg.class.getName(), soleStrategy);
		strategyMap.put(InLocationMessage.class.getName(), soleStrategy);
		
		strategyMap.put(LinkMsg.class.getName(), soleStrategy);
		strategyMap.put(InLinkMessage.class.getName(), soleStrategy);
		
		strategyMap.put(UnsubscribeEvent.class.getName(), soleStrategy);
		strategyMap.put(InUnsubscribeEvent.class.getName(), soleStrategy);
		
		strategyMap.put(SubscribeEvent.class.getName(), soleStrategy);
		strategyMap.put(InSubscribeEvent.class.getName(), soleStrategy);
		
		strategyMap.put(LocationEvent.class.getName(), soleStrategy);
		strategyMap.put(InLocationEvent.class.getName(), soleStrategy);
		
		// 媒体消息策略
		MediaMsgStrategy mediaStrategy = new MediaMsgStrategy();
		strategyMap.put(MediaMsg.class.getName(), mediaStrategy);
		strategyMap.put(InImageMessage.class.getName(), mediaStrategy);
		strategyMap.put(InVoiceMessage.class.getName(), mediaStrategy);
		strategyMap.put(InVoiceRecognitionMessage.class.getName(), mediaStrategy);
		strategyMap.put(InVideoMessage.class.getName(), mediaStrategy);
		strategyMap.put(InShortVideoMessage.class.getName(), mediaStrategy);
		
		// 版权事件策略
		CopyrightEventStrategy copyrightEventStrategy = new CopyrightEventStrategy();
		strategyMap.put(CopyrightEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InQualificationVerifySuccessEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InQualificationVerifyFailEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InNamingVerifySuccessEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InNamingVerifyFailEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InAnnualRenewEvent.class.getName(), copyrightEventStrategy);
		strategyMap.put(InVerifyExpiredEvent.class.getName(), copyrightEventStrategy);
		
		//任务完成事件推送处理策略
		JobFinishEventStrategy jobFinishEventStrategy = new JobFinishEventStrategy();
		strategyMap.put(JobFinishEvent.class.getName(), jobFinishEventStrategy);
		strategyMap.put(InMassSendJobFinishEvent.class.getName(), jobFinishEventStrategy);
		strategyMap.put(InTemplateSendJobFinishEvent.class.getName(), jobFinishEventStrategy);
		
		//菜单事件策略
		MenuEventStrategy menuEventStrategy = new MenuEventStrategy();
		strategyMap.put(MenuEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InPicPhotoOrAlbumEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InPicSysphotoEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InPicWeixinEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InScancodePushEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InScancodeWaitmsgEvent.class.getName(), menuEventStrategy);
		strategyMap.put(InLocationSelectEvent.class.getName(), menuEventStrategy);
		
		//扫描事件策略
		ScanEventStrategy scanEventStrategy = new ScanEventStrategy();
		strategyMap.put(ScanEvent.class.getName(), scanEventStrategy);
		strategyMap.put(ScanSubscribeEvent.class.getName(), scanEventStrategy);
		strategyMap.put(InScanEvent.class.getName(), scanEventStrategy);
		strategyMap.put(InScanSubscribeEvent.class.getName(), scanEventStrategy);
		
		//缺省消息或事件策略
		DefaultStrategy defaultStrategy = new DefaultStrategy();
		strategyMap.put(DefaultHandler.class.getName(), defaultStrategy);
	}

	/**
	 * 获取微信方法注解的策略处理器
	 * @param annotationType 注解类型 
	 * @return
	 */
	public MsgStrategy annotationStrategyCreator(Class<? extends Annotation> annotationType) {
		return strategyMap.get(annotationType.getName());
	}
	
	/**
	 * 获取请求消息的策略处理器
	 * @param inType 请求消息或事件类型
	 * @return
	 */
	public MsgStrategy inStrategyCreator(Class<? extends InMessage> inType) {
		return strategyMap.get(inType.getName());
	}
	
	/**
	 * 获取缺省的策略处理器
	 * @return
	 */
	public MsgStrategy getDefaultStrategy(){
		return strategyMap.get(DefaultHandler.class.getName());
	}
	
	/**
	 * 获取异常处理策略
	 * @return
	 */
	public ExceptionHandlerStrategy getExceptionHandlerStrategy(){
		return exceptionHandlerStrategy;
	}

	public static MsgStrategyFactory getInstance() {
		return factory;
	}

}
