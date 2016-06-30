package com.alijiujiu.kongcheng;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.overlayutil.PoiOverlay;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.alijiujiu.myeat.R;
import com.alijiujiu.myeat.R.id;
import com.alijiujiu.myeat.R.layout;
import com.alijiujiu.myeat.R.menu;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity {

	MapView mMapView = null;

	private TextView back;
	private EditText keywordView;
	private Button search;
	private TextView submit;

	private String keyword;

	private LocationClient mLocationClient;
	public BDLocationListener myListener = new MyLocationListener();
	public BaiduMap baiduMap;
	private LocationMode mCurrentMode;
	private PoiSearch mPoiSearch;
	private static LatLng latlng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_location);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mPoiSearch = PoiSearch.newInstance();

		mCurrentMode = LocationMode.NORMAL;
		baiduMap = mMapView.getMap();
		baiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latlng) {
				// TODO Auto-generated method stub

				MyLocationData locData = new MyLocationData.Builder().accuracy((float) 30.0)// 此处设置开发者获取到的方向信息，顺时针0-360

						.direction(100).latitude(latlng.latitude).longitude(latlng.longitude).build(); // 设置定位数据
				baiduMap.setMyLocationData(locData);

				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(latlng).zoom(18.0f);
				baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
				LocationActivity.latlng = latlng;
			}

			@Override
			public boolean onMapPoiClick(MapPoi poi) {
				// TODO Auto-generated method stub
				latlng = poi.getPosition();
				MyLocationData locData = new MyLocationData.Builder().accuracy((float) 30.0)// 此处设置开发者获取到的方向信息，顺时针0-360

						.direction(100).latitude(latlng.latitude).longitude(latlng.longitude).build(); // 设置定位数据
				baiduMap.setMyLocationData(locData);

				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(latlng).zoom(18.0f);
				baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
				return true;
			}

		});
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);

		loadViews();
		getPosition();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	private void getPosition() {
		// 开启定位图层
		baiduMap.setMyLocationEnabled(true);
		LocationClientOption option = new LocationClientOption();

		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);

		mLocationClient.start();

	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			// 构造定位数据
			Log.i("Location:", location.getRadius() + "");
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			// 设置定位数据
			baiduMap.setMyLocationData(locData);

			// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
			// BitmapDescriptor mCurrentMarker =
			// BitmapDescriptorFactory.fromResource(R.drawable.loc_self);
			// MyLocationConfiguration config = new
			// MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
			// baiduMap.setMyLocationConfigeration(config);

			// 显示位置
			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatus.Builder builder = new MapStatus.Builder();
			builder.target(ll).zoom(18.0f);
			baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			mLocationClient.stop();
		}

	}

	private void loadViews() {
		WindowManager wm = this.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();

		back = (TextView) findViewById(R.id.result_text);
		keywordView = (EditText) findViewById(R.id.search_keyword);
		search = (Button) findViewById(R.id.search);
		submit = (TextView) findViewById(R.id.location_submit);

		LayoutParams lp = keywordView.getLayoutParams();
		lp.width = width - 100;
		keywordView.setLayoutParams(lp);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
						public void onGetPoiResult(PoiResult result) {
							// 获取POI检索结果
							baiduMap.clear();
							PoiOverlay overlay = new MyPoiOverlay(baiduMap);
							baiduMap.setOnMarkerClickListener(overlay);
							overlay.setData(result);
							overlay.addToMap();
							overlay.zoomToSpan();
						}

						public void onGetPoiDetailResult(PoiDetailResult result) {
							// 获取Place详情页检索结果
						}
					};
					mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
					mPoiSearch.searchInCity((new PoiCitySearchOption()).city("青岛").keyword(keyword).pageNum(0));
				} else {
					Toast.makeText(LocationActivity.this, "关键词为空", Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(latlng == null){
					Toast.makeText(LocationActivity.this, "还没有选取位置", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.putExtra("latlng", latlng);
					setResult(10, intent);
					finish();
				}
			}
			
		});
	}
	
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			latlng = poi.location;
			MyLocationData locData = new MyLocationData.Builder().accuracy((float) 30.0)// 此处设置开发者获取到的方向信息，顺时针0-360

					.direction(100).latitude(latlng.latitude).longitude(latlng.longitude).build(); // 设置定位数据
			baiduMap.setMyLocationData(locData);

			MapStatus.Builder builder = new MapStatus.Builder();
			builder.target(latlng).zoom(18.0f);
			baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			return true;
		}
	}
}
