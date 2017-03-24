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
import com.github.jweixin.jwx.message.annotation.MediaEnum;
import com.github.jweixin.jwx.message.annotation.MediaMsg;
import com.github.jweixin.jwx.message.request.InImageMessage;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InShortVideoMessage;
import com.github.jweixin.jwx.message.request.InVideoMessage;
import com.github.jweixin.jwx.message.request.InVoiceMessage;
import com.github.jweixin.jwx.message.request.InVoiceRecognitionMessage;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 媒体消息策略
 * @author Administrator
 *
 */
public class MediaMsgStrategy implements MsgStrategy {
	
	public final static String MEDIA_MSG_MATCHOR = "MEDIA_MSG_MATCHOR";
	
	private static Logger logger = Logger.getLogger(MediaMsgStrategy.class);
	
	/**
	 * 媒体消息与注解名称对应关系
	 */
	private static Map<String, String> nameMap = new HashMap<String, String>();
	
	static {
		nameMap.put(InImageMessage.class.getName(), MediaEnum.IMAGE.type());
		nameMap.put(InVoiceMessage.class.getName(), MediaEnum.VOICE.type());
		nameMap.put(InVoiceRecognitionMessage.class.getName(), MediaEnum.VOICE_RECOGNITION.type());
		nameMap.put(InVideoMessage.class.getName(), MediaEnum.VIDEO.type());
		nameMap.put(InShortVideoMessage.class.getName(), MediaEnum.SHORT_VIDEO.type());
	}

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		MediaMsg mediaMsg = (MediaMsg) anno;
		String type = mediaMsg.value().type();
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(MEDIA_MSG_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(MEDIA_MSG_MATCHOR, matcherList);
		} else {
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(type)){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + mediaMsg.annotationType().getName() + "[" + type + "]注解，按要求只能配置一个。");
				}
			}
		}
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(anno);
		matcher.setMatchValue(type);
		matcherList.add(matcher);
		logger.debug("建立微信媒体消息(" + mediaMsg.annotationType().getName() + "[" + type + "])与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		String matchValue = nameMap.get(in.getClass().getName());
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(MEDIA_MSG_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(matchValue)){
					logger.debug("微信(" + context.getUrl() + ")媒体消息" + in.getClass().getName() + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		return null;
	}

}
