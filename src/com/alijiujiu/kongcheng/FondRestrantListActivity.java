package com.alijiujiu.kongcheng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.restrant.FondRestrant;
import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.alijiujiu.myeat.LoginActivity;
import com.alijiujiu.myeat.R;
import com.alijiujiu.myeat.R.id;
import com.alijiujiu.myeat.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class FondRestrantListActivity extends Activity {

	private BmobUser user;
	private TextView back;
	private ListView fondRestrantList;
	private TextView notice;

	private SimpleAdapter adapter;
	private List<HashMap<String, String>> restrantList;
	int size = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fond_restrant_list);

		loadViews();

		user = BmobUser.getCurrentUser(this);
		if (user == null) {
			Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			getInformation();
			notice.setText("正在获取数据...");
		}
	}

	private void getInformation() {
		BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
		query.addWhereEqualTo("userId", user.getObjectId());
		query.addWhereExists("restrantId");
		Log.i("FondRestrant", user.getObjectId());
		query.findObjects(FondRestrantListActivity.this, new FindListener<FondRestrant>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("FondRestrant", arg1);
				notice.setText(arg1);
			}

			@Override
			public void onSuccess(List<FondRestrant> list) {
				// TODO Auto-generated method stub
				size = list.size();
				if (size == 0) {
					notice.setText("列表为空");
				}
				Log.i("FondRestrant", list.size() + "");
				restrantList = new ArrayList<HashMap<String, String>>();
				for (FondRestrant restrant : list) {
					final HashMap<String, String> map = new HashMap<String, String>();
					if (restrant.getRestrantId() != null) {
						final String objectId = restrant.getObjectId();
						BmobQuery<Restrant> query = new BmobQuery<Restrant>();
						query.getObject(FondRestrantListActivity.this, restrant.getRestrantId(),
								new GetListener<Restrant>() {

									@Override
									public void onFailure(int arg0, String arg1) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(Restrant restrant) {
										// TODO Auto-generated method stub
										map.put("objectId", objectId);
										map.put("restrantId", restrant.getObjectId());
										map.put("name", restrant.getName());
										map.put("address", restrant.getAddress());
										Log.i("FondRestrant", restrant.getName());
										Log.i("FondRestrant", restrant.getAddress());
										restrantList.add(map);
										if (restrantList.size() == size) {
											notice.setVisibility(View.INVISIBLE);
											adapter = new SimpleAdapter(FondRestrantListActivity.this, restrantList,
													R.layout.restrant_brief, new String[] { "name", "address" },
													new int[] { R.id.brief_name, R.id.brief_address });
											fondRestrantList.setAdapter(adapter);
											fondRestrantList.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(AdapterView<?> parent, View view, int position,
														long id) {
													// TODO Auto-generated
													// method stub
													String restrantId = restrantList.get(position).get("restrantId");
													Intent intent = new Intent(FondRestrantListActivity.this,
															RecommendRestrantActivity.class);
													intent.putExtra("source", 0);
													intent.putExtra("PoiUid", restrantId);
													startActivity(intent);
												}

											});
											fondRestrantList.setOnItemLongClickListener(new OnItemLongClickListener() {

												@Override
												public boolean onItemLongClick(AdapterView<?> parent, View view,
														final int position, long id) {
													// TODO Auto-generated
													// method stub

													String[] items = { "删除" };
													new AlertDialog.Builder(FondRestrantListActivity.this)
															.setTitle("操作").setItems(items, new OnClickListener() {

																@Override
																public void onClick(DialogInterface dialog, int which) {

																	FondRestrant restrant = new FondRestrant();
																	restrant.setObjectId(
																			restrantList.get(position).get("objectId"));
																	restrant.delete(FondRestrantListActivity.this,
																			new DeleteListener() {

																				@Override
																				public void onFailure(int arg0,
																						String arg1) {
																					Log.i("FondRestrant", arg1);
																				}

																				@Override
																				public void onSuccess() {

																					restrantList.remove(position);
																					adapter = (SimpleAdapter) fondRestrantList
																							.getAdapter();
																					if (!adapter.isEmpty()) {
																						adapter.notifyDataSetChanged();
																					} else {
																						adapter.notifyDataSetChanged();
																						notice.setVisibility(
																								View.VISIBLE);
																						notice.setText("列表为空");
																					}
																					Toast.makeText(
																							FondRestrantListActivity.this,
																							"已从喜欢餐厅列表移除",
																							Toast.LENGTH_SHORT).show();
																				}

																			});

																}
															}).setNegativeButton("取消",
																	new DialogInterface.OnClickListener() {
																		public void onClick(DialogInterface dialog,
																				int which) {
																		}
																	})
															.show();

													return true;
												}

											});
										}
									}

								});

					}
				}

			}

		});
	}

	private void loadViews() {
		back = (TextView) findViewById(R.id.result_text);
		fondRestrantList = (ListView) findViewById(R.id.fond_restrant_list);
		notice = (TextView) findViewById(R.id.fond_restrant_list_notice);
		back.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

			
			
		});
	}

}
