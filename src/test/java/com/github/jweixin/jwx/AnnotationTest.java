package com.github.jweixin.jwx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.message.annotation.ClickEvent;

public class AnnotationTest {
	
	@Test
	public void testAnnotationExtend(){
		Assert.assertFalse(B.class.isAnnotationPresent(Foo.class));
		Assert.assertNull(B.class.getAnnotation(Foo.class));
		Method[] methods = B.class.getDeclaredMethods();
		for(Method method : methods){
			if(method.getName().equals("hello")){
				Assert.assertFalse(method.isAnnotationPresent(Bar.class));
			}
		}
	}
	
	@Test
	public void testAnnotationType(){
		for(Method method : A.class.getDeclaredMethods()){
			Annotation[] annos = method.getAnnotations();
			for(Annotation anno : annos) {
				Assert.assertTrue(Bar.class.isInstance(anno));
			}
		}
	}
	
	@Test
	public void testWeixinAnnotation(){
		Class<? extends Annotation> click = ClickEvent.class;
		for(Class<? extends Annotation> at : WeixinMethod.weixinAnnotationTypes){
			if(click == at){
				System.out.println(true);
			}
		}
	}

}
