package com.alijiujiu.koncheng.locationService;

import java.util.ArrayList;
import java.util.List;

import com.alijiujiu.koncheng.valueChangeListener.EventProducer;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.content.Context;
import android.util.Log;

public class LocationServiceManager implements LocationServiceInter {

	private BDLocation location;
	private PoiDetailResult detailResult;
	private PoiResult result;
	private ValueChangeListener listener;
	private int range = 1000;

	public LocationServiceManager() {

	}

	public LocationServiceManager(ValueChangeListener listener) {
		this.listener = listener;
	}

	/* 定位获取位置 */
	@Override
	public void getBDLocation(Context context) {
		final LocationClient mLocationClient = new LocationClient(context);

		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				setLocation(location);
				EventProducer producer = new EventProducer();
				if (listener != null) {
					producer.addListener(listener);
					producer.setLocation(location);
				}
				Log.i("Location", "ErrorCode:" + location.getLocType());
				Log.i("Location", "Address:" + location.getAddrStr());
				mLocationClient.stop();
			}
		});

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);

		// 启动定位
		mLocationClient.start();

	}

	@Override
	public void search(BDLocation location, String keyword, int pageCapacity, int pageNum) {
		// TODO Auto-generated method stub
		PoiSearch mPoiSearch = PoiSearch.newInstance();

		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
			public void onGetPoiResult(PoiResult result) {
				setResult(result);
				EventProducer producer = new EventProducer();
				if (listener != null) {
					producer.addListener(listener);
					producer.setResult(result);
				}

				Log.i("Result", "ListSize:" + result.getAllPoi().size()+"   "+result.getAllPoi().get(0).uid);
			}

			public void onGetPoiDetailResult(PoiDetailResult result) {

			}
		};

		// 设置检索监听
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

		// 周边搜索选项
		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.keyword(keyword);
		LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
		option.location(latlng);
		option.radius(range);
		option.pageCapacity(pageCapacity);
		option.pageNum(pageNum);

		mPoiSearch.searchNearby(option);
	}

	@Override
	public void searchDetail(String id) {
		// TODO Auto-generated method stub

		PoiSearch mPoiSearch = PoiSearch.newInstance();

		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
			public void onGetPoiResult(PoiResult result) {

			}

			public void onGetPoiDetailResult(PoiDetailResult result) {
				setDetailResult(result);
				EventProducer producer = new EventProducer();
				if (listener != null) {
					producer.addListener(listener);
					producer.setDetailResult(result);
				}

				Log.i("Result", "Name:" + result.getName());
			}
		};

		// 设置检索监听
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

		mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(id));
	}

	public BDLocation getLocation() {
		return location;
	}

	public void setLocation(BDLocation location) {
		this.location = location;
	}

	public PoiDetailResult getDetailResult() {
		return detailResult;
	}

	public void setDetailResult(PoiDetailResult detailResult) {
		this.detailResult = detailResult;
	}

	public PoiResult getResult() {
		return result;
	}

	public void setResult(PoiResult result) {
		this.result = result;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
