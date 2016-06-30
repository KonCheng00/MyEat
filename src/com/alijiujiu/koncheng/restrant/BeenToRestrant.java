package com.alijiujiu.koncheng.restrant;

import cn.bmob.v3.BmobObject;

public class BeenToRestrant extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String restrantId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRestrantId() {
		return restrantId;
	}

	public void setRestrantId(String restrantId) {
		this.restrantId = restrantId;
	}
}
