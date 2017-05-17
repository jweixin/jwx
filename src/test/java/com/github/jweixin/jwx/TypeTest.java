package com.github.jweixin.jwx;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.message.response.Article;

public class TypeTest {
	
	@Test
	public void testArrayType(){
		Article[] articles = {};
		Class<?> clazz = articles.getClass();
		if(clazz.isArray()){
			Assert.assertTrue(Article.class.isAssignableFrom(clazz.getComponentType()));
		}
	}

}
