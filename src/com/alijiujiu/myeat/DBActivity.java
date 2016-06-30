package com.alijiujiu.myeat;

import java.util.List;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class DBActivity extends Activity implements ValueChangeListener {

	private Button start;
	private TextView number;
	private TextView failnumber;
	private TextView result;
	private int num = 0;
	private int failnum = 0;
	private BDLocation location;
	private LocationServiceManager manager;
	private int pageNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
		start = (Button) findViewById(R.id.db_start);
		number = (TextView) findViewById(R.id.db_number);
		failnumber = (TextView) findViewById(R.id.db_failnumber);
		result = (TextView) findViewById(R.id.db_result);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start();
			}

		});
		number.setText("" + num);
		failnumber.setText("" + failnum);
	}

	private void start() {
		manager = new LocationServiceManager(this);
		manager.setRange(100000);
		manager.getBDLocation(getApplicationContext());
	}

	@Override
	public void onBDLocationChanged(ValueChangeEvent event) {
		location = event.getLocation();
		searchProcess();
	}

	@Override
	public void onPoiResultChanged(ValueChangeEvent event) {
		PoiResult result = event.getResult();
		List<PoiInfo> list = result.getAllPoi();
		if (list != null || list.size() != 0) {
			for (PoiInfo info : list) {
				manager.searchDetail(info.uid);
			}
			pageNum++;
			if (pageNum < event.getResult().getTotalPageNum())
				searchProcess();
			else{
				this.result.setText("上传完成");
			}
		}
	}

	@Override
	public void onPoiDetailResultChanged(ValueChangeEvent event) {
		PoiDetailResult result = event.getDetailResult();
		if (result != null) {
			Restrant restrant = new Restrant();
			restrant.setBaiduId(result.getUid());
			restrant.setName(result.getName());
			restrant.setAddress(result.getAddress());
			restrant.setLocation(result.getLocation());
			restrant.setShopPhone(result.getTelephone());
			restrant.setOverallRating((float) result.getOverallRating());
			restrant.setPrice((float) result.getPrice());
			restrant.setCertificationType(0);
			restrant.setRatingNum(result.getCommentNum());
			restrant.save(getApplicationContext(), new SaveListener() {

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "上传数据失败", Toast.LENGTH_LONG).show();
					failnum++;
					showProcess();
					Log.i("Fail", "Code:" + arg0 + " Msg:" + arg1);
				}

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "上传数据成功", Toast.LENGTH_LONG).show();
					num++;
					showProcess();
				}

			});
		}
	}

	private void searchProcess() {
		manager.search(location, "餐厅", 1, pageNum);
	}

	private void showProcess() {
		number.setText("" + num);
		failnumber.setText("" + failnum);
	}
}
