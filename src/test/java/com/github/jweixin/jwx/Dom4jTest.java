package com.github.jweixin.jwx;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

public class Dom4jTest {
	
	@Test
	public void testElement() throws DocumentException {
		String xmlText = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456</MsgId></xml>";
		// 获取xml文本document对象
		Document document = DocumentHelper.parseText(xmlText);
		// 得到 xml 根元素
		Element root = document.getRootElement();
		String msgType = root.element("MsgType").getTextTrim();
		System.out.println(msgType);
		Element element = root.element("Recognition");
		if(element!=null){
			System.out.println("no null");
		}else{
			System.out.println("null");
		}
	}

}
