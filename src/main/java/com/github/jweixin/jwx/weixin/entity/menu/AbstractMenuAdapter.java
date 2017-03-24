package com.github.jweixin.jwx.weixin.entity.menu;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AbstractMenuAdapter implements JsonDeserializer<AbstractMenu>, JsonSerializer<AbstractMenu> {

	@Override
	public JsonElement serialize(AbstractMenu menu, Type type, JsonSerializationContext context) {
		return context.serialize(menu);
	}

	@Override
	public AbstractMenu deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		if(json != null){
			JsonObject obj = json.getAsJsonObject();
			JsonElement conditionalList = obj.get("conditionalmenu");
			if(conditionalList != null && conditionalList.isJsonArray() && conditionalList.getAsJsonArray().size()>0){
				//获取个性化菜单列表
				MenuGroup menuList = new MenuGroup();
				ConditionalMenu[] conditionals = context.deserialize(conditionalList, ConditionalMenu[].class);
				for(ConditionalMenu conditional : conditionals){
					menuList.getConditionalmenu().add(conditional);
				}
				JsonElement menuElement = obj.get("menu");
				Menu menu = context.deserialize(menuElement, Menu.class);
				menuList.setMenu(menu);
				return menuList;
			} else {
				//获取无个性化菜单
				MenuLabel label = new MenuLabel();
				JsonElement menuElement = obj.get("menu");
				Menu menu = context.deserialize(menuElement, Menu.class);
				label.setMenu(menu);
				return label;
			}
		}
		return null;
	}

}
