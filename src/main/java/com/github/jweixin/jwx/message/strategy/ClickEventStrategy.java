package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.MsgMatcher;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.annotation.ClickEvent;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InClickEvent;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 菜单点击事件处理策略
 * @author Administrator
 *
 */
public class ClickEventStrategy implements MsgStrategy {
	
	public final static String CLICK_EVENT_MATCHOR = "CLICK_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(ClickEventStrategy.class);

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno) throws InitialWeixinConfigureException {
		ClickEvent clicEvent = (ClickEvent) anno;
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(clicEvent);
		
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		String value = clicEvent.value();
		matcher.setMatchValue(value);
		List<MsgMatcher> matcherList = mapper.get(CLICK_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(CLICK_EVENT_MATCHOR, matcherList);
		} else {
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				MsgMatcher m = iter.next();
				if(value.equals(m.getMatchValue())){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + clicEvent.annotationType().getName() + "{" + value + "}注解，按要求只能配置一个。");
				}
			}
		}
		matcherList.add(matcher);
		logger.debug("建立微信菜单单击事件" + clicEvent.annotationType().getName() + "{" + value + "}与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		InClickEvent click = (InClickEvent) in;
		String key = click.getEventKey();
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(CLICK_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(key)){
					logger.debug("微信(" + context.getUrl() + ")菜单单击事件{" + key + "}找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		return null;
	}

}
