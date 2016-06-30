package com.alijiujiu.koncheng.restrant;

import cn.bmob.v3.BmobObject;

public class FondRestrant extends BmobObject {

	/**
	 * 喜欢的餐厅
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String restrantId;
	private String baiduId;

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

	public String getBaiduId() {
		return baiduId;
	}

	public void setBaiduId(String baiduId) {
		this.baiduId = baiduId;
	}

}
