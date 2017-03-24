package com.github.jweixin.jwx.message.request.event;

import java.util.List;

/**
 * 版权检查结果
 * @author Administrator
 *
 */
public class CopyrightCheckResult {
	//数量
	private int count;
	//结果列表项
	private List<ResultListItem> resultList;
	// 整体校验结果 1-未被判为转载，可以群发，2-被判为转载，可以群发，3-被判为转载，不能群发
	private int checkState;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ResultListItem> getResultList() {
		return resultList;
	}

	public void setResultList(List<ResultListItem> resultList) {
		this.resultList = resultList;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

}
