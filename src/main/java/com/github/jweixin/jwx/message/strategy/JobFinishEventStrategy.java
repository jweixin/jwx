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
import com.github.jweixin.jwx.message.annotation.JobFinishEvent;
import com.github.jweixin.jwx.message.annotation.JobTask;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InMassSendJobFinishEvent;
import com.github.jweixin.jwx.message.request.event.InTemplateSendJobFinishEvent;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 任务完成事件推送处理策略
 * @author Administrator
 *
 */
public class JobFinishEventStrategy implements MsgStrategy {
	
public final static String JOB_FINISH_EVENT_MATCHOR = "JOB_FINISH_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(JobFinishEventStrategy.class);
	
	/**
	 * 版权事件与注解名称对应关系
	 */
	private static Map<String, String> nameMap = new HashMap<String, String>();
	
	static {
		nameMap.put(InMassSendJobFinishEvent.class.getName(), JobTask.MASS_SEND_JOB_FINISH.type());
		nameMap.put(InTemplateSendJobFinishEvent.class.getName(), JobTask.TEMPLATE_SEND_JOB_FINISH.type());
	}

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		JobFinishEvent jobFinishEvent = (JobFinishEvent) anno;
		String type = jobFinishEvent.value().type();
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(JOB_FINISH_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(JOB_FINISH_EVENT_MATCHOR, matcherList);
		} else {
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(type)){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + jobFinishEvent.annotationType().getName() + "[" + type + "]注解，按要求只能配置一个。");
				}
			}
		}
		MsgMatcher matcher = new MsgMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(anno);
		matcher.setMatchValue(type);
		matcherList.add(matcher);
		logger.debug("建立微信任务完成事件(" + jobFinishEvent.annotationType().getName() + "[" + type + "])与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		String matchValue = nameMap.get(in.getClass().getName());
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(JOB_FINISH_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				if(matcher.getMatchValue().equals(matchValue)){
					logger.debug("微信(" + context.getUrl() + ")任务完成事件" + in.getClass().getName() + "找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		return null;
	}

}
