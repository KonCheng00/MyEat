package com.alijiujiu.myeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.restrant.FondRestrant;
import com.alijiujiu.koncheng.restrant.RecommendRestrantManager;
import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.alijiujiu.kongcheng.MenuActivity;
import com.alijiujiu.kongcheng.RecommendResultActivity;
import com.alijiujiu.tools.Tools;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.poi.PoiResult;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class IndexActivity extends Activity implements SensorEventListener, ValueChangeListener {

	private boolean first_use = false;
	private BmobUser user;
	private List<FondRestrant> myFondRestrant;

	private ImageButton shake;
	private ImageButton button1;

	private LocationServiceManager manager;
	private BDLocation location;
	private String keyword = "餐厅";
	private int pageCapacity = 50;
	private int pageNum = 0;

	private int speed = 15;

	final int RIGHT = 0;
	final int LEFT = 1;

	private GestureDetector gestureDetector;

	SensorManager sensorManager = null;
	Vibrator vibrator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 不显示title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);

		// 首次使用
		first_use = getIntent().getBooleanExtra("FirstUse", false);
		if (first_use) {
			
		}

		user = BmobUser.getCurrentUser(this);
		loadViews();
		ImageButton go_left = (ImageButton) findViewById(R.id.index_go_left);
		ImageButton go_right = (ImageButton) findViewById(R.id.index_go_right);
		go_left.setVisibility(View.INVISIBLE);
		go_right.setVisibility(View.INVISIBLE);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		gestureDetector = new GestureDetector(IndexActivity.this, onGestureListener);
	}

	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(android.view.MotionEvent e1, android.view.MotionEvent e2, float velocityX,
				float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();

			if (x > 0) {
				doResult(RIGHT);
			} else if (x < 0) {
				doResult(LEFT);
			}
			return true;
		}
	};

	public boolean onTouchEvent(android.view.MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public void doResult(int action) {

		switch (action) {
		case RIGHT:
			
			break;

		case LEFT:
			Intent intent = new Intent(IndexActivity.this, GameMainActivity.class);
			startActivity(intent);
			break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// 当传感器精度改变时回调该方法，Do nothing.
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		int sensorType = event.sensor.getType();
		// values[0]:X轴，values[1]：Y轴，values[2]：Z轴
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			if ((Math.abs(values[0]) > speed || Math.abs(values[1]) > speed || Math.abs(values[2]) > speed)) {
				// Log.i("sensor x ", "============ values[0] = " + values[0]);
				// Log.i("sensor y ", "============ values[1] = " + values[1]);
				// Log.i("sensor z ", "============ values[2] = " + values[2]);

				Toast.makeText(this, "正在为您推荐餐厅...", Toast.LENGTH_SHORT).show();

				// 摇动手机后，再伴随震动提示~~
				vibrator.vibrate(250);

				if (user == null) {
					shake();
				} else {
					getRecommend();
				}
			}

		}
	}

	private void loadViews() {
		shake = (ImageButton) findViewById(R.id.index_shake);
		button1 = (ImageButton) findViewById(R.id.index_button1);
		shake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (user == null) {
					shake();
				} else {
					getRecommend();
				}
			}

		});
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IndexActivity.this, MenuActivity.class);
				startActivity(intent);
			}

		});
	}

	private void shake() {
		manager = new LocationServiceManager(this);
		manager.getBDLocation(getApplicationContext());
	}

	@Override
	public void onBDLocationChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		Log.i("BDLocationChanged", "Address:" + event.getLocation().getAddrStr());
		location = event.getLocation();
		manager.search(location, keyword, pageCapacity, pageNum);
	}

	@Override
	public void onPoiResultChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		// Log.i("PoiResultChanged", "ListSize:" +
		// event.getResult().getAllPoi().size());
		PoiResult result = event.getResult();
		if (result != null && result.getAllPoi() != null && result.getAllPoi().size() != 0) {
			int id = new Random().nextInt(result.getAllPoi().size());
			manager.searchDetail(result.getAllPoi().get(id).uid);
		}

	}

	@Override
	public void onPoiDetailResultChanged(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		Log.i("PoiDetailResultChanged", "Name:" + event.getDetailResult().getName());
		Intent intent = new Intent(IndexActivity.this, RecommendResultActivity.class);
		intent.putExtra("ResName", event.getDetailResult().getName());
		intent.putExtra("UID", event.getDetailResult().getUid());
		startActivity(intent);
	}

	private void getRecommend() {
		// 调用Model层中对数据库访问的方法
		BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
		query.addWhereEqualTo("userId", user.getObjectId());
		query.findObjects(this, new FindListener<FondRestrant>() {

			@Override
			public void onError(int code, String msg) {
				Log.i("ERROR", "查询喜欢的餐厅列表错误 " + "Error Code:" + code + "  Info:" + msg);
				// 显示错误信息
				Tools.matchBmobErrorCode(getApplicationContext(), code);
			}

			@Override
			public void onSuccess(List<FondRestrant> list) {

				myFondRestrant = list;
				// Log.i("myFondRestrant", "" +
				// myFondRestrant.get(0).getUserId());
				if (list == null || list.size() == 0) {
					shake();
				} else {
					// myFondRestrant = list;
					List<BmobQuery<FondRestrant>> queryList = new ArrayList<BmobQuery<FondRestrant>>();
					for (int i = 0; i < list.size(); i++) {
						BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
						query.addWhereEqualTo("restrantId", list.get(i).getRestrantId());
						queryList.add(query);
					}
					BmobQuery<FondRestrant> mainQuery = new BmobQuery<FondRestrant>();
					mainQuery.or(queryList);
					mainQuery.addQueryKeys("userId");
					mainQuery.findObjects(IndexActivity.this, new FindListener<FondRestrant>() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Log.i("ERROR", "第二次查询错误 " + "Error Code:" + arg0 + "  Info:" + arg1);
							Tools.matchBmobErrorCode(getApplicationContext(), arg0);
						}

						@Override
						public void onSuccess(List<FondRestrant> list) {
							// TODO Auto-generated method stub
							Log.i("Success", list.size() + "");
							if (list == null || list.size() == 0) {
								shake();
							} else {
								List<BmobQuery<FondRestrant>> queryList = new ArrayList<BmobQuery<FondRestrant>>();
								for (int i = 0; i < list.size(); i++) {
									BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
									query.addWhereEqualTo("userId", list.get(i).getUserId());
									queryList.add(query);
								}
								BmobQuery<FondRestrant> mainQuery = new BmobQuery<FondRestrant>();
								mainQuery.or(queryList);
								mainQuery.findObjects(IndexActivity.this, new FindListener<FondRestrant>() {

									@Override
									public void onError(int arg0, String arg1) {
										// TODO Auto-generated method stub
										Log.i("ERROR", "第三次查询错误 " + "Error Code:" + arg0 + "  Info:" + arg1);
										Tools.matchBmobErrorCode(getApplicationContext(), arg0);
									}

									@Override
									public void onSuccess(List<FondRestrant> list) {
										// TODO Auto-generated method stub
										Log.i("Success", list.size() + "");
										if (list == null || list.size() == 0 || myFondRestrant == null
												|| myFondRestrant.size() == 0) {
											shake();
										} else {
											// 调用Model层中对查询到的数据进行处理
											RecommendRestrantManager manager = new RecommendRestrantManager(
													getApplicationContext());
											// 按用户ID分组
											Map<String, List<FondRestrant>> userIdmap = manager.groupByUserId(list);
											// 计算用户相似度
											Map<String, Double> similarityMap = manager.getSimilarity(userIdmap,
													myFondRestrant);
											// 获取推荐餐厅列表
											final Map<String, Double> recommendRestrantMap = manager
													.getRecommendRestrantMap(similarityMap, userIdmap);
											// 获取推荐餐厅ID
											String recommendRestrantId = manager
													.recommendRestrant(recommendRestrantMap);
											Log.i("Success", "" + recommendRestrantId);

											BmobQuery<Restrant> query = new BmobQuery<Restrant>();
											query.getObject(getApplicationContext(), recommendRestrantId,
													new GetListener<Restrant>() {

														@Override
														public void onFailure(int arg0, String arg1) {
															// TODO
															// Auto-generated
															// method stub
															Log.i("ERROR", "第四次查询错误 " + "Error Code:" + arg0 + "  Info:"
																	+ arg1);
															Tools.matchBmobErrorCode(getApplicationContext(), arg0);
														}

														@Override
														public void onSuccess(Restrant restrant) {
															// TODO
															// Auto-generated
															// method stub
															if (restrant != null) {
																Intent intent = new Intent(IndexActivity.this,
																		RecommendResultActivity.class);
																intent.putExtra("restrant", restrant);
																startActivity(intent);
															}
														}

													});

										}
									}

								});
							}
						}

					});
				}
			}

		});
	}

}
