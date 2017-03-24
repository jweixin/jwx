package com.github.jweixin.jwx.message.strategy;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.jweixin.jwx.context.ExceptionMatcher;
import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.annotation.ExceptionHandler;
import com.github.jweixin.jwx.util.CollectionUtil;

/**
 * 异常处理策略
 * @author Administrator
 *
 */
public class ExceptionHandlerStrategy {
	
	private static Logger logger = Logger.getLogger(ExceptionHandlerStrategy.class);

	public void buildExceptionHandlerMethodMapper(WeixinContext context, WeixinMethod method, Annotation anno)
			throws InitialWeixinConfigureException {
		ExceptionHandler eh = (ExceptionHandler) anno;
		List<ExceptionMatcher> matchers = context.getExceptionMatchers();
		Class<? extends Throwable>[] ts = eh.value();
		if(ts==null || ts.length==0){
			ExceptionMatcher matcher = new ExceptionMatcher();
			matcher.setExceptionAnnotation(eh);
			matcher.setMethod(method);
			handleExceptionLink(context, matcher, matchers);
			logger.debug("建立缺省的微信异常处理(" + eh.annotationType().getName() + ")与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");

		} else {
			for(int i=0; i<ts.length; i++){
				ExceptionMatcher matcher = new ExceptionMatcher();
				matcher.setExceptionAnnotation(eh);
				matcher.setMethod(method);
				matcher.setExceptionType(ts[i]);
				handleExceptionLink(context, matcher, matchers);
				logger.debug("建立微信异常处理(" + eh.annotationType().getName() + "){" + ts[i].getName() + "}与方法" + method.getWxObj().getClass().getName() + "." + method.getWxMethod().getName() + "的映射关系");
			}
		}
	}

	/**
	 * 构建异常处理链
	 * @param matcher
	 * @param matchers
	 * @throws InitialWeixinConfigureException 
	 */
	private void handleExceptionLink(WeixinContext context, ExceptionMatcher matcher, List<ExceptionMatcher> matchers) throws InitialWeixinConfigureException {
		if(matchers.size()==0){
			matchers.add(matcher);
		} else {
			Class<? extends Throwable> type = matcher.getExceptionType();
			Iterator<ExceptionMatcher> iter = matchers.iterator();
			int index = 0;
			while(iter.hasNext()){
				ExceptionMatcher em = iter.next();
				Class<? extends Throwable> existType = em.getExceptionType();
				if(type==null && existType==null){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")最多只能配置1个缺省的异常处理方法");
				}
				if(existType==null){
					matchers.add(index, matcher);
					return;
				}
				if(existType.equals(type)){
					throw new InitialWeixinConfigureException("微信上下文(" + context.getUrl() + ")最多只能为异常{" + type.getName() + "}配置1个处理方法");
				}
				if(type!=null){
					if(existType.isAssignableFrom(type)){
						matchers.add(index, matcher);
						return;
					}
				}
				index++;
			}
			matchers.add(matcher);
		}
	}
	
	/**
	 * 获取异常的处理方法
	 * @param context
	 * @param ex
	 * @return
	 */
	public WeixinMethod getExceptionHandlerMethod(WeixinContext context, Throwable ex) {
		List<ExceptionMatcher> matchers = context.getExceptionMatchers();
		if(!CollectionUtil.isNull(matchers)){
			Iterator<ExceptionMatcher> iter = matchers.iterator();
			while(iter.hasNext()){
				ExceptionMatcher em = iter.next();
				Class<? extends Throwable> type = em.getExceptionType();
				if(type==null || type.isInstance(ex)){
					logger.debug("微信(" + context.getUrl() + ")异常{" + ex.getClass().getName() + "}找到微信处理方法:" + em.getMethod().getWxObj().getClass().getName() + "." + em.getMethod().getWxMethod().getName());
					return em.getMethod();
				}
			}
		}
		return null;
	}

}
