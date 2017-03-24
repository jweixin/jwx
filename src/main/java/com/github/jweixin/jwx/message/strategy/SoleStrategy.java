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
import com.github.jweixin.jwx.message.annotation.LinkMsg;
import com.github.jweixin.jwx.message.annotation.LocationEvent;
import com.github.jweixin.jwx.message.annotation.LocationMsg;
import com.github.jweixin.jwx.message.annotation.SubscribeEvent;
import com.github.jweixin.jwx.message.annotation.UnsubscribeEvent;
import com.github.jweixin.jwx.message.request.InLinkMessage;
import com.github.jweixin.jwx.message.request.InLocationMessage;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InLocationEvent;
import com.github.jweixin.jwx.message.request.event.InSubscribeEvent;
import com.github.jweixin.jwx.message.request.event.InUnsubscribeEvent;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 唯一消息或事件处理策略
 * @author Administrator
 *
 */
public class SoleStrategy implements MsgStrategy {
	
	public final static String SOLE_MSG_OR_EVENT_MATCHOR = "SOLE_MSG_OR_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(SoleStrategy.class);
	/**
	 * 消息（事件）与注解名称对应关系
	 */
	private static Map<String, String> nameMap = new HashMap<String, String>();
	
	static {
		nameMap.put(InLocationMessage.class.getName(), LocationMsg.class.getName());
		nameMap.put(InLinkMessage.class.getName(), LinkMsg.class.getName());
		nameMap.put(InSubscribeEvent.class.getName(), SubscribeEvent.class.getName());
		nameMap.put(InUnsubscribeEvent.class.getName(), UnsubscribeEvent.class.getName());
		nameMap.put(InLocationEvent.class.getName(), LocationEvent.class.getName());
	}

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		String type = anno.annotationType().getName();
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(SOLE_MSG_OR_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(SOLE_MSG_OR_EVENT_MATCHOR, matcherList);
		}else{
			//迭代查找是否已经存在该种类型的注解处理方法
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(type.equals(matcher.getMatchValue())){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + type + "注解，按要求只能配置一个。");
				}
			}
		}
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(anno);
		matcher.setMatchValue(type);
		matcherList.add(matcher);
		logger.debug("建立微信消息或事件(" + anno.annotationType().getName() + ")与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		String matchValue = nameMap.get(in.getClass().getName());
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(SOLE_MSG_OR_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(matchValue)){
					logger.debug("微信(" + context.getUrl() + ")消息或事件" + in.getClass().getName() + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		return null;
	}

}
