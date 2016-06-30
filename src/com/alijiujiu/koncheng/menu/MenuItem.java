package com.alijiujiu.koncheng.menu;

import cn.bmob.v3.datatype.BmobFile;

public class MenuItem {
	private String name;
	private float price;
	private String type;
	private BmobFile picture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BmobFile getPicture() {
		return picture;
	}

	public void setPicture(BmobFile picture) {
		this.picture = picture;
	}
}
