package com.github.jweixin.jwx.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.github.jweixin.jwx.message.annotation.ClickEvent;
import com.github.jweixin.jwx.message.annotation.CopyrightEvent;
import com.github.jweixin.jwx.message.annotation.DefaultHandler;
import com.github.jweixin.jwx.message.annotation.ExceptionHandler;
import com.github.jweixin.jwx.message.annotation.JobFinishEvent;
import com.github.jweixin.jwx.message.annotation.LinkMsg;
import com.github.jweixin.jwx.message.annotation.LocationEvent;
import com.github.jweixin.jwx.message.annotation.LocationMsg;
import com.github.jweixin.jwx.message.annotation.MediaMsg;
import com.github.jweixin.jwx.message.annotation.MenuEvent;
import com.github.jweixin.jwx.message.annotation.ScanEvent;
import com.github.jweixin.jwx.message.annotation.ScanSubscribeEvent;
import com.github.jweixin.jwx.message.annotation.SubscribeEvent;
import com.github.jweixin.jwx.message.annotation.TextMsg;
import com.github.jweixin.jwx.message.annotation.UnsubscribeEvent;
import com.github.jweixin.jwx.message.annotation.ViewEvent;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Article;
import com.github.jweixin.jwx.message.response.Image;
import com.github.jweixin.jwx.message.response.Music;
import com.github.jweixin.jwx.message.response.OutMessage;
import com.github.jweixin.jwx.message.response.Success;
import com.github.jweixin.jwx.message.response.Text;
import com.github.jweixin.jwx.message.response.Video;
import com.github.jweixin.jwx.message.response.Voice;

/**
 * 微信方法
 * 在初始化时扫描微信方法时生成，持有微信控制器对象、方法、方法参数、方法返回值信息
 * @author Administrator
 *
 */
public class WeixinMethod {
	public static List<Class<?>> weixinMethodParamTypes = new ArrayList<Class<?>>();
	public static List<Class<?>> weixinMethodReturnTypes = new ArrayList<Class<?>>();
	public static List<Class<? extends Annotation>> weixinAnnotationTypes = new ArrayList<Class<? extends Annotation>>();
	
	private Object wxObj;
	private Method wxMethod;
	private Class<?>[] params;
	private Class<?> returnType;
	
	static {
		/* 添加允许的微信方法参数类型 */
		weixinMethodParamTypes.add(InMessage.class);
		weixinMethodParamTypes.add(ServletRequest.class);
		weixinMethodParamTypes.add(ServletResponse.class);
		weixinMethodParamTypes.add(WeixinContext.class);
		weixinMethodParamTypes.add(Throwable.class);
		
		/* 添加允许的微信方法返回值类型 */
		weixinMethodReturnTypes.add(OutMessage.class);
		weixinMethodReturnTypes.add(Success.class);
		weixinMethodReturnTypes.add(void.class);
		weixinMethodReturnTypes.add(String.class);
		weixinMethodReturnTypes.add(Text.class);
		weixinMethodReturnTypes.add(Music.class);
		weixinMethodReturnTypes.add(Video.class);
		weixinMethodReturnTypes.add(Image.class);
		weixinMethodReturnTypes.add(Voice.class);
		weixinMethodReturnTypes.add(Article.class);
		
		/* 微信消息或事件注解类型 */
		weixinAnnotationTypes.add(TextMsg.class);
		weixinAnnotationTypes.add(ClickEvent.class);
		weixinAnnotationTypes.add(ViewEvent.class);
		weixinAnnotationTypes.add(LocationMsg.class);
		weixinAnnotationTypes.add(LinkMsg.class);
		weixinAnnotationTypes.add(MediaMsg.class);
		weixinAnnotationTypes.add(CopyrightEvent.class);
		weixinAnnotationTypes.add(JobFinishEvent.class);
		weixinAnnotationTypes.add(MenuEvent.class);
		weixinAnnotationTypes.add(SubscribeEvent.class);
		weixinAnnotationTypes.add(UnsubscribeEvent.class);
		weixinAnnotationTypes.add(ScanEvent.class);
		weixinAnnotationTypes.add(ScanSubscribeEvent.class);
		weixinAnnotationTypes.add(LocationEvent.class);
		weixinAnnotationTypes.add(DefaultHandler.class);
		weixinAnnotationTypes.add(ExceptionHandler.class);
	}
	
	/**
	 * 判断是否合法的参数类型
	 * @param cls
	 * @return
	 */
	public boolean isLegalParamType(Class<?> cls){
		for(Class<?> pt : weixinMethodParamTypes){
			if(pt.isAssignableFrom(cls)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否合法的返回值类型
	 * @param cls
	 * @return
	 */
	public boolean isLegalReturnType(Class<?> cls){
		for(Class<?> rt : weixinMethodReturnTypes){
			if(rt.isAssignableFrom(cls)){
				return true;
			}
		}
		
		//编译时泛型类型擦除，无法取得定义的微信方法返回值是集合的具体泛型类型，只能在运行时判断
		if(Collection.class.isAssignableFrom(cls)){
			return true;
		}
		
		if(cls.isArray()){
			if(Article.class.isAssignableFrom(cls.getComponentType())){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断方法是否有合法的微信注解
	 * @param method
	 * @return
	 */
	public static boolean hasWeixinAnnotationType(Method method){
		for(Class<? extends Annotation> at : weixinAnnotationTypes){
			if(method.isAnnotationPresent(at)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断注解是否为微信注解类型
	 * @return
	 */
	public static boolean isWeixinAnnotationType(Class<? extends Annotation> annotationType){
		for(Class<?> at : weixinAnnotationTypes){
			if(at.isAssignableFrom(annotationType)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断注解是否是异常处理类型
	 * @param annotationType
	 * @return
	 */
	public static boolean isExceptionAnnotationType(Class<? extends Annotation> annotationType){
		if(ExceptionHandler.class.isAssignableFrom(annotationType)){
			return true;
		}
		return false;
	}

	public Object getWxObj() {
		return wxObj;
	}

	public void setWxObj(Object wxObj) {
		this.wxObj = wxObj;
	}

	public Method getWxMethod() {
		return wxMethod;
	}

	public void setWxMethod(Method wxMethod) {
		this.wxMethod = wxMethod;
	}

	public Class<?>[] getParams() {
		return params;
	}

	public void setParams(Class<?>[] params) {
		this.params = params;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
}
