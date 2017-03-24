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
import com.github.jweixin.jwx.message.annotation.TextMsg;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InTextMessage;
import com.github.jweixin.jwx.util.CollectionUtil;
import com.github.jweixin.jwx.util.StringUtil;

/**
 * 文本消息处理策略
 * @author Administrator
 *
 */
public class TextMsgStrategy implements MsgStrategy {
	
	public final static String TEXT_MSG_TEXT_MATCHOR = "TEXT_MSG_TEXT_MATCHOR";
	public final static String TEXT_MSG_PATTERN_MATCHOR = "TEXT_MSG_PATTERN_MATCHOR";
	public final static String TEXT_MSG_DEFAULT_MATCHOR = "TEXT_MSG_DEFAULT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(TextMsgStrategy.class);

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno) throws InitialWeixinConfigureException {
		TextMsg textMsg = (TextMsg)anno;
		
		
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		
		String value = textMsg.value();
		if(!StringUtil.isNull(value)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(textMsg);
			matcher.setMatchValue(value);
			List<MsgMatcher> matcherList = mapper.get(TEXT_MSG_TEXT_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(TEXT_MSG_TEXT_MATCHOR, matcherList);
			} else {
				Iterator<MsgMatcher> iter = matcherList.iterator();
				while(iter.hasNext()){
					MsgMatcher m = iter.next();
					if(value.equals(m.getMatchValue())){
						throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + textMsg.annotationType().getName() + "[" + value + "]注解，按要求只能配置一个。");
					}
				}
			}
			matcherList.add(matcher);
			logger.debug("建立微信文本消息" + textMsg.annotationType().getName() + "(" + value + ")与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
		}
		
		String pattern = textMsg.pattern();
		if(!StringUtil.isNull(pattern)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(textMsg);
			matcher.setMatchValue(pattern);
			List<MsgMatcher> matcherList = mapper.get(TEXT_MSG_PATTERN_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(TEXT_MSG_PATTERN_MATCHOR, matcherList);
			}
			matcherList.add(matcher);
			logger.debug("建立微信文本模式消息" + textMsg.annotationType().getName() + "{" + pattern + "}与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
		}
		
		if(StringUtil.isNull(value) && StringUtil.isNull(pattern)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(textMsg);
			List<MsgMatcher> matcherList = mapper.get(TEXT_MSG_DEFAULT_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(TEXT_MSG_DEFAULT_MATCHOR, matcherList);
			}
			if(matcherList.size()>0){
				throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")配置了多个文本消息缺省适配器，按要求只能配置一个。");
			}else{
				matcherList.add(matcher);
				logger.debug("建立微信缺省文本消息" + textMsg.annotationType().getName() + "与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
			}
		}
	}
	
	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		InTextMessage text = (InTextMessage) in;
		String content = text.getContent();
		
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(TEXT_MSG_TEXT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				Annotation anno = matcher.getWxAnnotation();
				TextMsg textMsg = (TextMsg)anno;
				if(textMsg.ignoreCase()){
					if(content.equalsIgnoreCase(matcher.getMatchValue())){
						logger.debug("微信(" + context.getUrl() + ")文本消息" + content + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
						return matcher.getMethod();
					}
				} else {
					if(content.equals(matcher.getMatchValue())){
						logger.debug("微信(" + context.getUrl() + ")文本消息" + content + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
						return matcher.getMethod();
					}
				}
			}
		}
		
		matchers = context.getMessageMethodMappper().get(TEXT_MSG_PATTERN_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(content.matches(matcher.getMatchValue())){
					logger.debug("微信(" + context.getUrl() + ")文本消息" + content + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		
		matchers = context.getMessageMethodMappper().get(TEXT_MSG_DEFAULT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			logger.debug("微信(" + context.getUrl() + ")文本消息" + content + "找到微信处理方法:" + matchers.get(0).getMethod().getWxObj().getClass().getName() + "." + matchers.get(0).getMethod().getWxMethod().getName());
			return matchers.get(0).getMethod();
		}
		
		return null;
	}

}
