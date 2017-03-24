package com.github.jweixin.jwx.util;

import java.io.IOException;
import java.util.List;

import com.github.jweixin.jwx.util.ClassFilter;

public interface PackageScanner {

	public List<String> getFullyQualifiedClassNameList() throws IOException;

	List<Class<?>> getPackageClassList() throws ClassNotFoundException, IOException;

	List<Class<?>> getPackageClassList(ClassFilter classFilter) throws IOException, ClassNotFoundException;
	
}
