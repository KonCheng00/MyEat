package com.alijiujiu.myeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alijiujiu.koncheng.restrant.CertificationRestrant;
import com.alijiujiu.koncheng.restrant.FondRestrant;
import com.alijiujiu.kongcheng.CertificationDetailsActivity;
import com.alijiujiu.kongcheng.FondRestrantListActivity;
import com.alijiujiu.kongcheng.RecommendRestrantActivity;
import com.alijiujiu.tools.CertificationTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

public class MyCertificationActivity extends Activity {

	private BmobUser user;
	private CertificationRestrant restrant;

	private TextView back;
	private TextView notice;
	private ListView listView;

	private List<Map<String, String>> restrantList;
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_certification);

		user = BmobUser.getCurrentUser(this);
		if (user == null) {
			Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			loadViews();
			getInformation();
			notice.setText("正在获取数据...");
		}
	}

	private void getInformation() {
		BmobQuery<CertificationRestrant> query = new BmobQuery<CertificationRestrant>();
		query.addWhereEqualTo("certificationManId", user.getObjectId());
		query.findObjects(this, new FindListener<CertificationRestrant>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("MyCertification", arg1);
				Toast.makeText(getApplicationContext(), "查询数据出错:" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<CertificationRestrant> list) {
				notice.setText("");
				restrantList = new ArrayList<Map<String, String>>();
				for (CertificationRestrant restrant : list) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("objectId", restrant.getObjectId());
					map.put("restrantId", restrant.getRestrantId());
					map.put("name", restrant.getName());
					map.put("address", restrant.getAddress());
					map.put("status", CertificationTool.verifyInformation(restrant.getCertificationType()));
					restrantList.add(map);
				}
				adapter = new SimpleAdapter(getApplicationContext(), restrantList, R.layout.cer_restrant_brief,
						new String[] { "name", "address", "status" },
						new int[] { R.id.brief_name, R.id.brief_address, R.id.brief_status });
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						final String cer_id = restrantList.get(position).get("objectId");
						final String restrantId = restrantList.get(position).get("restrantId");
						String status = restrantList.get(position).get("status");
						if (status.equals("审核通过，认证完成")) {
							String[] items = { "编辑餐厅信息" };
							new AlertDialog.Builder(MyCertificationActivity.this).setTitle("操作")
									.setItems(items, new android.content.DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {

											Intent intent = new Intent(MyCertificationActivity.this, CertificationDetailsActivity.class);
											intent.putExtra("cer_id", cer_id);
											intent.putExtra("restrantId", restrantId);
											startActivity(intent);
											
										}
									}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
										}
									}).show();
						}

					}

				});
			}

		});
	}

	private void loadViews() {
		back = (TextView) findViewById(R.id.result_text);
		notice = (TextView) findViewById(R.id.certification_restrant_list_notice);
		listView = (ListView) findViewById(R.id.certification_list);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

}
