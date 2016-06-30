package com.alijiujiu.kongcheng;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.overlayutil.PoiOverlay;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.alijiujiu.myeat.R;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class AllRestrantActivity extends FragmentActivity implements ValueChangeListener {

	private PoiSearch mPoiSearch = null;
	private BaiduMap mBaiduMap = null;
	private LocationServiceManager manager;

	private BDLocation location;
	private String keyword = "餐厅";
	private int pageCapacity = 10;
	private int pageNum = 0;
	private int totalPageNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_all_restrant);

		manager = new LocationServiceManager(this);
		loadViews();
		manager.getBDLocation(getApplicationContext());
	}

	private void loadViews() {
		// TODO Auto-generated method stub
		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.all_restrant_map)))
				.getBaiduMap();
	}

	@Override
	public void onBDLocationChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		location = event.getLocation();
		searchProcess();
	}

	@Override
	public void onPoiResultChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		PoiResult result = event.getResult();
		totalPageNum = result.getTotalPageNum();
		
		// 展示餐厅位置
		mBaiduMap.clear();
		PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
		mBaiduMap.setOnMarkerClickListener(overlay);
		overlay.setData(result);
		overlay.addToMap();
		overlay.zoomToSpan();
	}

	@Override
	public void onPoiDetailResultChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		Log.i("PoiDetailResultChanged", "Name:" + event.getDetailResult().getName());
		Intent intent = new Intent(AllRestrantActivity.this, RecommendRestrantActivity.class);
		intent.putExtra("PoiUid", event.getDetailResult().getUid());
		intent.putExtra("source", 1);
		startActivity(intent);
	}

	private void searchProcess() {
		manager.search(location, keyword, pageCapacity, pageNum);
	}

	public void frontPage(View v) {
		if (pageNum > 0) {
			pageNum--;
		}
		searchProcess();
	}

	public void nextPage(View v) {
		pageNum++;
		if (pageNum >= totalPageNum) {
			Toast.makeText(this, "附近没有更多的餐厅了", Toast.LENGTH_SHORT).show();
			// v.setClickable(false);
		} else {
			searchProcess();
		}
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			// mPoiSearch.searchPoiDetail((new
			// PoiDetailSearchOption()).poiUid(poi.uid));
			manager.searchDetail(poi.uid);
			// }
			return true;
		}
	}
}
