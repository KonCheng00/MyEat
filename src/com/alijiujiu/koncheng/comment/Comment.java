package com.alijiujiu.koncheng.comment;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
	private String restrantId;
	private String authorId;
	private String parentId;
	private String content;

	public String getRestrantId() {
		return restrantId;
	}

	public void setRestrantId(String restrantId) {
		this.restrantId = restrantId;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
