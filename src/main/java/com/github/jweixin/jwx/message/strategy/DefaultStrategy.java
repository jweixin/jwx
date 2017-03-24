package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.MsgMatcher;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 缺省的消息或事件策略处理
 * @author Administrator
 *
 */
public class DefaultStrategy implements MsgStrategy {
	
	public final static String DEFAULT_MSG_OR_EVENT_MATCHOR = "DEFAULT_MSG_OR_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(DefaultStrategy.class);

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(DEFAULT_MSG_OR_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(DEFAULT_MSG_OR_EVENT_MATCHOR, matcherList);
		} else {
			if(matcherList.size()>0){
				throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了缺省注解，按要求只能配置一个。");
			}
		}
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(anno);
		matcherList.add(matcher);
		logger.debug("建立微信缺省消息或事件(" + anno.annotationType().getName() + ")与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(DEFAULT_MSG_OR_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			logger.debug("微信(" + context.getUrl() + ")消息或事件" + in.getClass().getName() + "找到缺省的微信处理方法:" + matchers.get(0).getMethod().getWxObj().getClass().getName() + "." + matchers.get(0).getMethod().getWxMethod().getName());
			return matchers.get(0).getMethod();
		}
		return null;
	}

}
