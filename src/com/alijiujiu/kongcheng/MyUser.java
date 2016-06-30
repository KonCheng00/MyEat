package com.alijiujiu.kongcheng;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
	private String taste;
	private String cuisine;
	private Integer nation;

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}
}
