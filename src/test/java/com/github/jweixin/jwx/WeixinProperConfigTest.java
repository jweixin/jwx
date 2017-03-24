package com.github.jweixin.jwx;

import org.junit.Assert;
import org.junit.Test;

import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinContextConfigHelper;


public class WeixinProperConfigTest {
	
	@Test
	public void testProperSet() throws InitialWeixinConfigureException {
		WeixinContext ctx = new WeixinContext();
		ctx.setToken(" ");
		WeixinContextConfigHelper.setFieldValue(ctx, "token", "foo");
		Assert.assertEquals(ctx.getToken(), "foo");
	}

}
