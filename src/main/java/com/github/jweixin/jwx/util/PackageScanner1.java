package com.github.jweixin.jwx.util;

import java.io.IOException;
import java.util.List;

import com.github.jweixin.jwx.util.ClassFilter1;

public interface PackageScanner1 {

	public List<String> getFullyQualifiedClassNameList() throws IOException;

	List<Class<?>> getPackageClassList() throws ClassNotFoundException, IOException;

	List<Class<?>> getPackageClassList(ClassFilter1 classFilter) throws IOException, ClassNotFoundException;
	
}
