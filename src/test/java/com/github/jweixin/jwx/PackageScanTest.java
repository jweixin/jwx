package com.github.jweixin.jwx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.github.jweixin.jwx.context.Weixin;
import com.github.jweixin.jwx.util.ClasspathPackageScanner;

public class PackageScanTest {
	
	@Test
	public void testPackageScan() throws ClassNotFoundException, IOException{
		List<String> packages = new ArrayList<String>();
		packages.add("com.github.jweixin.jwx");
		ClasspathPackageScanner scanner = new ClasspathPackageScanner(packages, Weixin.class);
		System.out.println(scanner.getClassSet().size());
		Iterator<Class<?>> it = scanner.getClassSet().iterator();
		while(it.hasNext()){
			Class<?> clazz = it.next();
			System.out.println(clazz.getName());
		}
	}

}
