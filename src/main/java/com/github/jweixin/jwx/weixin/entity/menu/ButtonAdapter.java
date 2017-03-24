package com.github.jweixin.jwx.weixin.entity.menu;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 按钮适配器类
 * @author Administrator
 *
 */
public class ButtonAdapter implements JsonDeserializer<Button>, JsonSerializer<Button> {

	@Override
	public Button deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		if (json != null) {
			// 获取按钮对象
			JsonObject obj = json.getAsJsonObject();
			// 获取按钮类型
			JsonElement tElement = obj.get("type");
			if (tElement != null) {
				String t = tElement.getAsString();
				if ("click".equals(t)) {
					ClickButton button = new ClickButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("view".equals(t)) {
					ViewButton button = new ViewButton(obj.get("name").getAsString());
					button.setUrl(obj.get("url").getAsString());
					return button;
				} else if ("scancode_waitmsg".equals(t)) {
					ScancodeWaitmsgButton button = new ScancodeWaitmsgButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("scancode_push".equals(t)) {
					ScancodePushButton button = new ScancodePushButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("pic_sysphoto".equals(t)) {
					PicSysphotoButton button = new PicSysphotoButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("pic_photo_or_album".equals(t)) {
					PicPhotoOrAlbumButton button = new PicPhotoOrAlbumButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("pic_weixin".equals(t)) {
					PicWeixinButton button = new PicWeixinButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("location_select".equals(t)) {
					LocationSelectButton button = new LocationSelectButton(obj.get("name").getAsString());
					button.setKey(obj.get("key").getAsString());
					return button;
				} else if ("media_id".equals(t)) {
					MediaIdButton button = new MediaIdButton(obj.get("name").getAsString());
					button.setMediaId(obj.get("media_id").getAsString());
					return button;
				} else if ("view_limited".equals(t)) {
					ViewLimitedButton button = new ViewLimitedButton(obj.get("name").getAsString());
					button.setMediaId(obj.get("media_id").getAsString());
					return button;
				}
			}
		}

		return null;
	}

	@Override
	public JsonElement serialize(Button button, Type type, JsonSerializationContext context) {
		return context.serialize(button);
	}

}
