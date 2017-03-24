package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.MsgMatcher;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.annotation.ViewEvent;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.event.InViewEvent;
import com.github.jweixin.jwx.util.CollectionUtil;
import com.github.jweixin.jwx.util.StringUtil;

/**
 * 点击菜单跳转链接时的事件推送处理策略
 * @author Administrator
 *
 */
public class ViewEventStrategy implements MsgStrategy {
	
	public final static String VIEW_EVENT_MATCHOR = "VIEW_EVENT_MATCHOR";
	
	private static Logger logger = Logger.getLogger(ViewEventStrategy.class);

	@Override
	public void buildMsgMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws ServletException {
		ViewEvent viewEvent = (ViewEvent) anno;
		
		ViewEventMatcher matcher = new ViewEventMatcher();
		matcher.setMethod(method);
		matcher.setWxAnnotation(viewEvent);
		
		if(!StringUtil.isNull(viewEvent.value())){
			matcher.setMatchValue(viewEvent.value());
		}
		if(viewEvent.menuId()!=0L){
			matcher.setMenuId(viewEvent.menuId());
		}
		
		Map<String, List<MsgMatcher>> mapper = context.getMessageMethodMappper();
		List<MsgMatcher> matcherList = mapper.get(VIEW_EVENT_MATCHOR);
		if(matcherList == null){
			matcherList = new ArrayList<MsgMatcher>();
			mapper.put(VIEW_EVENT_MATCHOR, matcherList);
		} else {
			Iterator<MsgMatcher> iter = matcherList.iterator();
			while(iter.hasNext()){
				ViewEventMatcher m = (ViewEventMatcher) iter.next();
				if(eq(matcher, m)){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")多个方法配置了" + viewEvent.annotationType().getName() + info(matcher) + "注解，按要求只能配置一个。");
				}
			}
		}
		matcherList.add(matcher);
		logger.debug("建立点击菜单跳转链接事件" + viewEvent.annotationType().getName() + info(matcher) + "与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
	}
	
	/**
	 * 适配器详细信息
	 * @param matcher
	 * @return
	 */
	private String info(ViewEventMatcher matcher){
		if(matcher.getMatchValue()!=null && matcher.getMenuId()!=null){
			return "{EventKey:" + matcher.getMatchValue() + ",MenuId:" + matcher.getMenuId() + "}";
		}
		if(matcher.getMatchValue()!=null && matcher.getMenuId()==null){
			return "{EventKey:" + matcher.getMatchValue() + "}";
		}
		if(matcher.getMatchValue()==null && matcher.getMenuId()!=null){
			return "{MenuId:" + matcher.getMenuId() + "}";
		}
		return "";
	}
	
	/**
	 * 判断适配器是否相同
	 * @param matcher
	 * @param other
	 * @return
	 */
	private boolean eq(ViewEventMatcher matcher, ViewEventMatcher other){
		if(eq(matcher.getMatchValue(), other.getMatchValue()) && eq(matcher.getMenuId(), other.getMenuId())){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否相同
	 * @param s1
	 * @param s2
	 * @return
	 */
	private boolean eq(String s1, String s2){
		if(s1==null && s2==null){
			return true;
		}
		if(s1!=null && s2==null){
			return false;
		}
		if(s1==null && s2!=null){
			return false;
		}
		return s1.equals(s2);
	}
	
	/**
	 * 判断Long值是否相同
	 * @param l1
	 * @param l2
	 * @return
	 */
	private boolean eq(Long l1, Long l2){
		if(l1==null && l2==null){
			return true;
		}
		if(l1==null && l2!=null){
			return false;
		}
		if(l1!=null && l2==null){
			return false;
		}
		return l1.longValue() == l2.longValue();
	}

	@Override
	public WeixinMethod getInMsgWeixinMethod(WeixinContext context, InMessage in) {
		InViewEvent view = (InViewEvent) in;
		String eventKey = view.getEventKey();
		Long menuId = view.getMenuId();
		ViewEventMatcher keyMatcher = null;
		ViewEventMatcher menuMatcher = null;
		ViewEventMatcher defaultMatcher = null;
		List<MsgMatcher> matchers = context.getMessageMethodMappper().get(VIEW_EVENT_MATCHOR);
		if(!CollectionUtil.isNull(matchers)){
			Iterator<MsgMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				ViewEventMatcher matcher = (ViewEventMatcher) iter.next();
				String key = matcher.getMatchValue();
				Long mId = matcher.getMenuId();
				if(key==null && mId==null){
					defaultMatcher = matcher;
				}
				if(key!=null && mId==null){
					if(key.equals(eventKey)){
						if(menuId==null){
							logger.debug("微信(" + context.getUrl() + ")菜单跳转链接事件[" + eventKey + "]找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
							return matcher.getMethod();
						} else {
							keyMatcher = matcher;
						}
					}
				}
				if(mId!=null && key==null){
					if(menuId!=null){
						if(mId.longValue()==menuId.longValue()){
							menuMatcher = matcher;
						}
					}
				}
				if(key!=null && mId!=null){
					if(menuId!=null){
						if(key.equals(eventKey) && mId.longValue()==menuId.longValue()){
							logger.debug("微信(" + context.getUrl() + ")菜单跳转链接事件[" + eventKey + "{MenuId:" + menuId + "}]找到微信处理方法:" + matcher.getMethod().getWxObj().getClass().getName() + "." + matcher.getMethod().getWxMethod().getName());
							return matcher.getMethod();
						}
					}
				}
			}
		}
		if(keyMatcher!=null){
			logger.debug("微信(" + context.getUrl() + ")菜单跳转链接事件[" + eventKey + "]找到微信处理方法:" + keyMatcher.getMethod().getWxObj().getClass().getName() + "." + keyMatcher.getMethod().getWxMethod().getName());
			return keyMatcher.getMethod();
		}
		if(menuMatcher!=null){
			logger.debug("微信(" + context.getUrl() + ")菜单跳转链接事件[" + eventKey + "{MenuId:" + menuId + "}]找到微信处理方法:" + menuMatcher.getMethod().getWxObj().getClass().getName() + "." + menuMatcher.getMethod().getWxMethod().getName());
			return menuMatcher.getMethod();
		}
		if(defaultMatcher!=null){
			logger.debug("微信(" + context.getUrl() + ")菜单跳转链接事件[" + eventKey + "]找到微信处理方法:" + defaultMatcher.getMethod().getWxObj().getClass().getName() + "." + defaultMatcher.getMethod().getWxMethod().getName());
			return defaultMatcher.getMethod();
		}
		return null;
	}

}
