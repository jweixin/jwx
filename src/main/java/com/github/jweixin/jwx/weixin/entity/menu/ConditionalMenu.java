package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 个性化菜单
 * @author Administrator
 *
 */
public class ConditionalMenu extends Menu {
	/**
	 * 菜单匹配规则
	 */
	private MatchRule matchrule;
	
	/**
	 * 检查个性化菜单是否合法
	 * 不能全部字段都为空
	 * 国家、省份、城市，如果后面的不为空，则前面的也不为能为空
	 */
	public boolean check(){
		if(matchrule==null){
			return false;
		}
		if(matchrule.getTagId()==null && matchrule.getSex()==null && matchrule.getCountry()==null && matchrule.getProvince()==null && matchrule.getCity()==null && matchrule.getClientPlatformType()==null && matchrule.getLanguage()==null){
			return false;
		}
		if(matchrule.getCity()!=null && (matchrule.getCountry()==null || matchrule.getProvince()==null)){
			return false;
		}
		if(matchrule.getProvince()!=null && matchrule.getCountry()==null){
			return false;
		}
		
		return true;
	}

	public MatchRule getMatchrule() {
		return matchrule;
	}

	public void setMatchrule(MatchRule matchrule) {
		this.matchrule = matchrule;
	}

}
