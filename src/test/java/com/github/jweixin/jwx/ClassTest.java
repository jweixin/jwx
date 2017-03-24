package com.github.jweixin.jwx;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InTextMessage;

public class ClassTest {
	
	@Test
	public void testClassAssign(){
		Assert.assertTrue(InMessage.class.isAssignableFrom(InTextMessage.class));
	}

}
