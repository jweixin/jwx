package com.github.jweixin.jwx;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.message.response.Success;
import com.github.jweixin.jwx.weixin.entity.material.MediaType;

public class EnumTest {
	@Test
	public void testEq(){
		MediaType t = MediaType.IMAGE;
		Assert.assertTrue(t.equals(MediaType.IMAGE));
		Assert.assertFalse(t.equals(null));
		Assert.assertTrue(t == MediaType.IMAGE);
		Assert.assertFalse(MediaType.THUMB == MediaType.IMAGE);
	}
	
	@Test
	public void testSuccess(){
		Success s = Success.SUCCESS;
		Assert.assertTrue(s instanceof Success);
	}

}
