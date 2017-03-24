package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.MsgMatcher;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.annotation.MenuEvent;
import com.github.jweixin.jwx.message.annotation.MenuType;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InLocationSelectEvent;
import com.github.jweixin.jwx.message.request.event.InMenuEvent;
import com.github.jweixin.jwx.message.request.event.InPicPhotoOrAlbumEvent;
import com.github.jweixin.jwx.message.request.event.InPicSysphotoEvent;
import com.github.jweixin.jwx.message.request.event.InPicWeixinEvent;
import com.github.jweixin.jwx.message.request.event.InScancodePushEvent;
import com.github.jweixin.jwx.message.request.event.InScancodeWaitmsgEvent;
import com.github.jweixin.jwx.util.CollectionUtil;
import com.github.jweixin.jwx.util.StringUtil;

/**
 * 菜单事件策略
 * @author Administrator
 *
 */
public class MenuEventStrategy implements MsgStrategy {
	
	public final static String MENU_EVENT_MATCHOR = "MENU_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(MenuEventStrategy.class);
	
	/**
	 * 菜单事件与注解名称对应关系
	 */
	private static Map<String, String> nameMap = new HashMap<String, String>();
	
	static {
		nameMap.put(InPicPhotoOrAlbumEvent.class.getName(), MenuType.PIC_PHOTO_OR_ALBUM.type());
		nameMap.put(InPicSysphotoEvent.class.getName(), MenuType.PIC_SYSPHOTO.type());
		nameMap.put(InPicWeixinEvent.class.getName(), MenuType.PIC_WEIXIN.type());
		nameMap.put(InScancodePushEvent.class.getName(), MenuType.SCANCODE_PUSH.type());
		nameMap.put(InScancodeWaitmsgEvent.class.getName(), MenuType.SCANCODE_WAITMSG.type());
		nameMap.put(InLocationSelectEvent.class.getName(), MenuType.LOCATION_SELECT.type());
	}

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		MenuEvent menuEvent = (MenuEvent) anno;
		String type = menuEvent.value().type();
		String eventKey = menuEvent.eventKey();
		String matchValue = type;
		if(!StringUtil.isNull(eventKey)){
			matchValue = type + "-" + eventKey;
		}
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(MENU_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(MENU_EVENT_MATCHOR, matcherList);
		} else {
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(matchValue)){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + menuEvent.annotationType().getName() + "[" + matchValue + "]注解，按要求只能配置一个。");
				}
			}
		}
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(anno);
		matcher.setMatchValue(matchValue);
		matcherList.add(matcher);
		logger.debug("建立微信授权事件(" + menuEvent.annotationType().getName() + "[" + matchValue + "])与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		InMenuEvent menuEvent = (InMenuEvent) in;
		String type = nameMap.get(menuEvent.getClass().getName());
		String eventKey = menuEvent.getEventKey();
		String matchValue = type + "-" + eventKey;
		MsgMatcher candidateMatcher = null;
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(MENU_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(matchValue)){
					logger.debug("微信(" + context.getUrl() + ")菜单事件" + menuEvent.getClass().getName() + "[" + eventKey + "]找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				} else if(matcher.getMatchValue().equals(type)){
					candidateMatcher = matcher;
				}
			}
		}
		if(candidateMatcher != null){
			logger.debug("微信(" + context.getUrl() + ")菜单事件" + menuEvent.getClass().getName() + "[" + eventKey + "]找到微信处理方法:" + candidateMatcher.getMethod().getWxObj().getClass().getName() + "." + candidateMatcher.getMethod().getWxMethod().getName());
			return candidateMatcher.getMethod();
		}
		return null;
	}

}
