package com.github.jweixin.jwx;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import com.github.jweixin.jwx.context.Weixin;
import com.github.jweixin.jwx.util.PackageAnnotationClassScanner;

public class PackageScanTest {
	
	@Test
	public void testPackageScan() throws ClassNotFoundException, IOException{
		String[] packages = new String[]{"com.github.jweixin.jwx"};
		PackageAnnotationClassScanner scanner = new PackageAnnotationClassScanner(packages, Weixin.class);
		System.out.println(scanner.getClassSet().size());
		Iterator<Class<?>> it = scanner.getClassSet().iterator();
		while(it.hasNext()){
			Class<?> clazz = it.next();
			System.out.println(clazz.getName());
		}
	}

}
