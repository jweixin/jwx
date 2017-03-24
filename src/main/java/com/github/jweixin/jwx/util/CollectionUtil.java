package com.github.jweixin.jwx.util;

import java.util.Collection;

/**
 * 集合工具
 * @author Administrator
 *
 */
public class CollectionUtil {
	
	/**
	 * 判断集合是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isNull(Collection<?> collection){
		if(collection == null || collection.isEmpty()){
			return true;
		}
		return false;
	}

}
