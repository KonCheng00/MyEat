package com.alijiujiu.koncheng.locationService;

import com.baidu.location.BDLocation;

import android.content.Context;

public interface LocationServiceInter {
	
	// 获取位置
	public abstract void getBDLocation(Context context);

	// 搜索餐厅
	public abstract void search(BDLocation location, String keyword, int pageCapacity, int pageNum);
	
	// 搜索详细信息
	public abstract void searchDetail(String id);
}
