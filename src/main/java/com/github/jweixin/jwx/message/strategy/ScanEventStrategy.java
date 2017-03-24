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
import com.github.jweixin.jwx.message.annotation.ScanEvent;
import com.github.jweixin.jwx.message.annotation.ScanSubscribeEvent;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InScanEvent;
import com.github.jweixin.jwx.message.request.event.InScanSubscribeEvent;
import com.github.jweixin.jwx.util.CollectionUtil;
import com.github.jweixin.jwx.util.StringUtil;

/**
 * 扫描事件处理策略
 * @author Administrator
 *
 */
public class ScanEventStrategy implements MsgStrategy {
	
	public final static String SCAN_EVENT_TEXT_MATCHOR = "SCAN_EVENT_TEXT_MATCHOR";
	public final static String SCAN_EVENT_PATTERN_MATCHOR = "SCAN_EVENT_PATTERN_MATCHOR";
	public final static String SCAN_EVENT_DEFAULT_MATCHOR = "SCAN_EVENT_DEFAULT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(ScanEventStrategy.class);
	
	/**
	 * 扫描事件与注解名称对应关系
	 */
	private static Map<String, String> nameMap = new HashMap<String, String>();
	
	static {
		nameMap.put(InScanEvent.class.getName(), ScanEvent.class.getName());
		nameMap.put(InScanSubscribeEvent.class.getName(), ScanSubscribeEvent.class.getName());
	}

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		String prefix = anno.annotationType().getName();
		String value = null;
		String scenePattern = null;
		if(anno instanceof ScanEvent){
			value = ((ScanEvent) anno).value();
			scenePattern = ((ScanEvent) anno).scenePattern();
		} else if(anno instanceof ScanSubscribeEvent){
			value = ((ScanSubscribeEvent) anno).value();
			scenePattern = ((ScanSubscribeEvent) anno).scenePattern();
		}
		
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		if(!StringUtil.isNull(value)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(anno);
			matcher.setMatchValue(prefix + "-" + value);
			List<MsgMatcher> matcherList = mapper.get(SCAN_EVENT_TEXT_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(SCAN_EVENT_TEXT_MATCHOR, matcherList);
			} else {
				Iterator<MsgMatcher> iter = matcherList.iterator();
				while(iter.hasNext()){
					MsgMatcher m = iter.next();
					if(m.getMatchValue().equals(matcher.getMatchValue())){
						throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")配置了多个扫描事件[" + prefix + "(" + value + ")]文本适配方法，按要求只能配置一个。");
					}
				}
			}
			matcherList.add(matcher);
			logger.debug("建立微信扫描事件" + anno.annotationType().getName() + "(" + value + ")与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
		}
		
		if(!StringUtil.isNull(scenePattern)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(anno);
			matcher.setMatchValue(prefix + "-" + scenePattern);
			List<MsgMatcher> matcherList = mapper.get(SCAN_EVENT_PATTERN_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(SCAN_EVENT_PATTERN_MATCHOR, matcherList);
			}
			matcherList.add(matcher);
			logger.debug("建立微信扫描事件场景模式" + anno.annotationType().getName() + "{" + scenePattern + "}与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
		}
		
		if(StringUtil.isNull(value) && StringUtil.isNull(scenePattern)){
			MsgMatcher matcher = new MsgMatcher();
			matcher.setMethod(method);
			matcher.setWxAnnotation(anno);
			matcher.setMatchValue(prefix);
			List<MsgMatcher> matcherList = mapper.get(SCAN_EVENT_DEFAULT_MATCHOR);
			if(matcherList == null){
				matcherList = new ArrayList<MsgMatcher>();
				mapper.put(SCAN_EVENT_DEFAULT_MATCHOR, matcherList);				
			} else {
				Iterator<MsgMatcher> iter = matcherList.iterator();
				while(iter.hasNext()){
					MsgMatcher msgMatcher = iter.next();
					if(msgMatcher.getMatchValue().equals(prefix)){
						throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")配置了多个扫描事件[" + prefix + "]的缺省方法，按要求只能配置一个。");
					}
				}
			}
			matcherList.add(matcher);
			logger.debug("建立微信缺省扫描事件" + anno.annotationType().getName() + "与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
		}
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		String prefix = nameMap.get(in.getClass().getName());
		String scene = null;
		if(in instanceof InScanEvent){
			scene = ((InScanEvent) in).getEventKey();
		} else if(in instanceof InScanSubscribeEvent){
			scene = ((InScanSubscribeEvent) in).getEventKey().substring("qrscene_".length());
		}
		
		if(!StringUtil.isNull(scene)){
			//场景文本适配
			List<MsgMatcher> matchers = context.getMessageMethodMappper().get(SCAN_EVENT_TEXT_MATCHOR);			
			if(!CollectionUtil.isNull(matchers)){
				Iterator<MsgMatcher> iter = matchers.iterator();
				while(iter.hasNext()){
					MsgMatcher matcher = iter.next();
					if(matcher.getMatchValue().equals(prefix + "-" + scene)){
						logger.debug("微信(" + context.getUrl() + ")扫描事件[" + in.getClass().getName() + "]场景{" + scene + "}找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
						return matcher.getMethod();
					}
				}
			}
			
			//场景正则表达式适配
			matchers = context.getMessageMethodMappper().get(SCAN_EVENT_PATTERN_MATCHOR);
			if(!CollectionUtil.isNull(matchers)){
				Iterator<MsgMatcher> iter = matchers.iterator();
				while(iter.hasNext()){
					MsgMatcher matcher = iter.next();
					String matchValue = matcher.getMatchValue();
					if(matchValue.startsWith(prefix + "-")){
						matchValue = matchValue.substring((prefix + "-").length());
						if(scene.matches(matchValue)){
							logger.debug("微信(" + context.getUrl() + ")扫描事件[" + in.getClass().getName() + "]场景{" + scene + "}通过正则表达式[" + matchValue +"]找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
							return matcher.getMethod();
						}
					}
				}
			}
		}
		
		//缺省适配
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(SCAN_EVENT_DEFAULT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				MsgMatcher matcher = iter.next();
				String matchValue = matcher.getMatchValue();
				if(matchValue.equals(prefix)){
					logger.debug("微信(" + context.getUrl() + ")扫描事件[" + in.getClass().getName() + "]场景{" + scene + "}找到缺省的微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
					return matcher.getMethod();
				}
			}
		}
		
		return null;
	}

}
