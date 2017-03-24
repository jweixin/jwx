package com.github.jweixin.jwx.message.request.event;

/**
 * 结果列表项
 * @author Administrator
 *
 */
public class ResultListItem {
	//群发文章的序号，从1开始
	private int articleIdx;
	//用户声明文章的状态
	private int userDeclareState;
	//系统校验的状态
	private int auditState;
	//相似原创文的url
	private String originalArticleUrl;
	//相似原创文的类型
	private int originalArticleType;
	//是否能转载
	private int canReprint;
	//是否需要替换成原创文内容
	private int needReplaceContent;
	//是否需要注明转载来源
	private int needShowReprintSource;

	public int getArticleIdx() {
		return articleIdx;
	}

	public void setArticleIdx(int articleIdx) {
		this.articleIdx = articleIdx;
	}

	public int getUserDeclareState() {
		return userDeclareState;
	}

	public void setUserDeclareState(int userDeclareState) {
		this.userDeclareState = userDeclareState;
	}

	public int getAuditState() {
		return auditState;
	}

	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}

	public String getOriginalArticleUrl() {
		return originalArticleUrl;
	}

	public void setOriginalArticleUrl(String originalArticleUrl) {
		this.originalArticleUrl = originalArticleUrl;
	}

	public int getOriginalArticleType() {
		return originalArticleType;
	}

	public void setOriginalArticleType(int originalArticleType) {
		this.originalArticleType = originalArticleType;
	}

	public int getCanReprint() {
		return canReprint;
	}

	public void setCanReprint(int canReprint) {
		this.canReprint = canReprint;
	}

	public int getNeedReplaceContent() {
		return needReplaceContent;
	}

	public void setNeedReplaceContent(int needReplaceContent) {
		this.needReplaceContent = needReplaceContent;
	}

	public int getNeedShowReprintSource() {
		return needShowReprintSource;
	}

	public void setNeedShowReprintSource(int needShowReprintSource) {
		this.needShowReprintSource = needShowReprintSource;
	}

}
