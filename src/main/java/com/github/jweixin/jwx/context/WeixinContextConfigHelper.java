package com.github.jweixin.jwx.context;

import java.lang.reflect.Field;

import com.github.jweixin.jwx.util.StringUtil;

/**
 * 微信上下文配置帮助类
 * @author Administrator
 *
 */
public class WeixinContextConfigHelper {
	
	/**
	 * 配置微信上下文中的配置项
	 * 获取微信注解配置中的属性值，如果不为空且微信上下文中没有配置，则赋值；
	 * 如果属性值不为空并且和注解的值相同，则跳过；
	 * 如果与获取的上下文的值不同，说明存在两个或多个属性配置值，这抛出异常
	 * @param context
	 * @param field
	 * @param value
	 * @throws InitialWeixinConfigureException 
	 */
	public static void setFieldValue(WeixinContext context, String field, String value) throws InitialWeixinConfigureException {		
		Class<?> clazz = context.getClass();
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			String val = (String)f.get(context);
			if(StringUtil.isNull(val)){
				f.set(context, value);
			}else if(!val.equals(value)){
				throw new InitialWeixinConfigureException("微信上下文(" + field + ")配置了多个不同的值");
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new InitialWeixinConfigureException("微信上下文不存在属性:" + field);
		}
	}

}
