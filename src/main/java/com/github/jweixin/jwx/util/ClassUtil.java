package com.github.jweixin.jwx.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类工具
 * @author zzx
 * @version 1.0 2017-1-11
 */
public class ClassUtil {
	/**
	 * 判断类是否已经加载
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean findLoadedClass(String name) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> cl = Class.forName("java.lang.ClassLoader", false, Thread.currentThread().getContextClassLoader());
		Method method = cl.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
		method.setAccessible(true);
		if (method.invoke(Thread.currentThread().getContextClassLoader(), name) != null) {
		    return true;
		} else {
		    return false;
		}
	}

}
