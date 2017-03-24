package com.github.jweixin.jwx.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具
 * @author zzx
 * @version 1.0 created by 2017-1-12
 */
public class StringUtil {
	private StringUtil() {

	}

	/**
	 * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
	 * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
	 */
	public static String getRootPath(URL url) {
		String fileUrl = url.getFile();
		int pos = fileUrl.indexOf('!');

		if (-1 == pos) {
			return fileUrl;
		}

		return fileUrl.substring(5, pos);
	}

	/**
	 * "cn.fh.lightning" -> "cn/fh/lightning"
	 * 
	 * @param name
	 * @return
	 */
	public static String dotToSplash(String name) {
		return name.replaceAll("\\.", "/");
	}

	/**
	 * "cn/fh/lightning" -> "cn.fh.lightning"
	 * 
	 * @param name
	 * @return
	 */
	public static String splashToDot(String name) {
		return name.replaceAll("/", "\\.");
	}

	/**
	 * "Apple.class" -> "Apple"
	 */
	public static String trimExtension(String name) {
		int pos = name.indexOf('.');
		if (-1 != pos) {
			return name.substring(0, pos);
		}

		return name;
	}

	/**
	 * /application/home -> /home
	 * 
	 * @param uri
	 * @return
	 */
	public static String trimURI(String uri) {
		String trimmed = uri.substring(1);
		int splashIndex = trimmed.indexOf('/');

		return trimmed.substring(splashIndex);
	}
	
	/**
	 * 去除字符串列表中的重复值
	 * @param list
	 * @return
	 */
	public static List<String> removeDuplicate(List<String> list) {
		List<String> newList = new ArrayList<String>();
		for (String s : list) {
			if (!newList.contains(s)) {
				newList.add(s);
			}
		}
		return newList;
	}
	
	/**
	 * 判断字符串是否为空
	 * null和空字符串都认为是空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
}