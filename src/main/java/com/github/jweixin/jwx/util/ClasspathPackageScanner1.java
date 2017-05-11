package com.github.jweixin.jwx.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.github.jweixin.jwx.util.ClassFilter1;

/**
 * 包扫描器
 * 检出指定包（包括子包）下所有的类
 * @author zzx
 * @version 1.0 2017-01-11
 */
public class ClasspathPackageScanner1 implements PackageScanner1 {
	private String basePackage;
	private ClassLoader cl;

	/**
	 * Construct an instance and specify the base package it should scan.
	 * 
	 * @param basePackage
	 *            The base package to scan.
	 */
	public ClasspathPackageScanner1(String basePackage) {
		this.basePackage = basePackage;
		this.cl = getClass().getClassLoader();
	}

	/**
	 * Construct an instance with base package and class loader.
	 * 
	 * @param basePackage
	 *            The base package to scan.
	 * @param cl
	 *            Use this class load to locate the package.
	 */
	public ClasspathPackageScanner1(String basePackage, ClassLoader cl) {
		this.basePackage = basePackage;
		this.cl = cl;
	}

	/**
	 * Get all fully qualified names located in the specified package and its
	 * sub-package.
	 *
	 * @return A list of fully qualified names.
	 * @throws IOException
	 */
	@Override
	public List<String> getFullyQualifiedClassNameList() throws IOException {
		return doScan(basePackage, new ArrayList<String>());
	}
	
	/**
	 * 获取包及子包路径下所有类
	 * @return 包类列表
	 */
	@Override
	public List<Class<?>> getPackageClassList() throws ClassNotFoundException, IOException {
		return getPackageClassList(null);
	}
	
	/**
	 * 获取包及子包路径下符合类过滤器过滤掉类
	 * @return 包类列表
	 */
	@Override
	public List<Class<?>> getPackageClassList(ClassFilter1 classFilter) throws IOException, ClassNotFoundException {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		Iterator<String> iter = getFullyQualifiedClassNameList().iterator();
		while (iter.hasNext()) {
			Class<?> clazz = Class.forName(iter.next(), Boolean.FALSE, cl);
			if (classFilter == null || (classFilter != null && classFilter.accept(clazz))) {
				classList.add(clazz);
			}
		}
		return classList;
	}
	
	/**
	 * 根据类名列表获取类列表
	 * @param classNameList
	 * @param classFilter
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClassList(List<String> classNameList, ClassFilter1 classFilter) throws ClassNotFoundException{
		List<Class<?>> classList = new ArrayList<Class<?>>();
		Iterator<String> iter = classNameList.iterator();
		while (iter.hasNext()) {
			Class<?> clazz = Class.forName(iter.next(), Boolean.FALSE, ClasspathPackageScanner1.class.getClassLoader());
			if (classFilter == null || (classFilter != null && classFilter.accept(clazz))) {
				classList.add(clazz);
			}
		}
		return classList;
	}

	/**
	 * Actually perform the scanning procedure.
	 *
	 * @param basePackage
	 * @param nameList A list to contain the result.
	 * @return A list of fully qualified names.
	 *
	 * @throws IOException
	 */
	private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
		// 用斜杠替代包名中的点
		String splashPath = StringUtil.dotToSplash(basePackage);

		// 获取所有包路径资源
		Enumeration<URL> urls = cl.getResources(splashPath);
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String filePath = StringUtil.getRootPath(url);
			// 判断路径资源是jar包还是目录
			if (isJarFile(filePath)) {
				// jar file
				readFromJarFile(filePath, splashPath, nameList);
			} else {
				// directory
				readFromDirectory(basePackage, filePath, nameList);
			}
		}

		return nameList;
	}

	/**
	 * Convert short class name to fully qualified name. e.g., String -> java.lang.String
	 */
	private String toFullyQualifiedName(String shortName, String basePackage) {
		StringBuilder sb = new StringBuilder(basePackage);
		sb.append('.');
		sb.append(StringUtil.trimExtension(shortName));

		return sb.toString();
	}
	
	/**
	 * 从jar包查找类文件
	 * @param jarPath
	 * @param splashedPackageName
	 * @param nameList
	 * @throws IOException
	 */
	private void readFromJarFile(String jarPath, String splashedPackageName, List<String> nameList) throws IOException {
		JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
		JarEntry entry = jarIn.getNextJarEntry();

		while (null != entry) {
			String name = entry.getName();
			if (name.startsWith(splashedPackageName) && isClassFile(name)) {
				// 去除class文件扩展名,用点替代斜杠
				nameList.add(StringUtil.splashToDot(StringUtil.trimExtension(name)));
			}

			entry = jarIn.getNextJarEntry();
		}
		jarIn.close();
	}
	
	/**
	 * 从目录读取类文件
	 * @param basePackage
	 * @param path
	 * @param nameList
	 */
	private void readFromDirectory(String basePackage, String path, List<String> nameList) {
		File file = new File(path);
		String[] names = file.list();

		if (names != null) {
			for (String name : names) {
				if (isClassFile(name)) {
					nameList.add(toFullyQualifiedName(name, basePackage));
				} else {
					// 如果子文件是目录类型，则递归查找类文件
					String subPath = path + File.separator + name;
					File subFile = new File(subPath);
					if (subFile.isDirectory()) {
						readFromDirectory(basePackage + "." + name, subPath, nameList);
					}
				}
			}
		}
	}

	private boolean isClassFile(String name) {
		return name.endsWith(".class");
	}

	private boolean isJarFile(String name) {
		return name.endsWith(".jar");
	}

}