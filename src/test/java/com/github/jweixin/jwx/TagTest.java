package com.github.jweixin.jwx;

public class TagTest {
	/*
	private TagService tagService = new TagService();

	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter()).create();

	private String getAccessToken() {
		String appID = "wx7710f3ee377d59de";
		String appSecret = "2f0303d4c88211c8b376601ce4ef32f9";
		String url = WeixinConst.WEIXIN_ACCESS_TOKEN_LINK + "&appid=" + appID + "&secret=" + appSecret;
		AccessToken at = null;
		try {
			at = WeixinInterfaceHelper.get(url, AccessToken.class);
		} catch (WeixinInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return at.getAccessToken();
	}

	@Test
	public void testCreateTag() {
		String accessToken = getAccessToken();
		Tag tag = new Tag("管理组");
		System.out.println(gson.toJson(tag));
		tag = tagService.createTag(accessToken, tag);
		System.out.println(tag.getId());

	}

	@Test
	public void testGetTags() {
		List<Tag> tags = tagService.getTags(getAccessToken());
		for (Tag tag : tags) {
			System.out.println(gson.toJson(tag));
		}
	}

	@Test
	public void testUpdateTag() {
		Tag tag = new Tag("维修组");
		tag.setId(103);
		ReturnCode rc = tagService.updateTag(getAccessToken(), tag);
		System.out.println(gson.toJson(rc));
	}

	@Test
	public void testDelTag() {
		ReturnCode rc = tagService.deleteTag(getAccessToken(), 103);
		System.out.println(gson.toJson(rc));
	}

	@Test
	public void testTagUserList() {
		TagUserList group = tagService.getTagUserList(getAccessToken(), 100, "");
		System.out.println(gson.toJson(group));
	}

	@Test
	public void testUserTagging() {
		ArrayList<String> openIdList = new ArrayList<String>();
		openIdList.add("obfFfv7qNx063AW9x449u1LmZvxw");
		ReturnCode rc = tagService.batchTagging(getAccessToken(), 100, openIdList);
		System.out.println(gson.toJson(rc));
	}

	@Test
	public void testGetUserTagList() {
		String openId = "obfFfv7qNx063AW9x449u1LmZvxw";
		int[] ids = tagService.getUserTagId(getAccessToken(), openId);
		for (int t : ids)
			System.out.println(t);
	}

	@Test
	public void testUntagging() {
		ArrayList<String> openIdList = new ArrayList<String>();
		openIdList.add("obfFfv7qNx063AW9x449u1LmZvxw");
		ReturnCode rc = tagService.batchUntagging(getAccessToken(), 100, openIdList);
		System.out.println(gson.toJson(rc));
	}*/

}
