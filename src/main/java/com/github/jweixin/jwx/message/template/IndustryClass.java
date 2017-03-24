package com.github.jweixin.jwx.message.template;

/**
 * 行业类别
 * 
 * @author Administrator
 *
 */
public class IndustryClass {
	/**
	 * 主行业
	 */
	private String firstClass;
	/**
	 * 副行业
	 */
	private String secondClass;

	public String getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}

	public String getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}
}
