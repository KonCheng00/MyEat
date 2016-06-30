package com.alijiujiu.koncheng.menu;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Menu extends BmobObject {
	private String restrantId;
	private List<MenuItem> recommendMenu;
	private List<MenuItem> menu;

	public String getRestrantId() {
		return restrantId;
	}

	public void setRestrantId(String restrantId) {
		this.restrantId = restrantId;
	}

	public List<MenuItem> getRecommendMenu() {
		return recommendMenu;
	}

	public void setRecommendMenu(List<MenuItem> recommendMenu) {
		this.recommendMenu = recommendMenu;
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = menu;
	}

}
