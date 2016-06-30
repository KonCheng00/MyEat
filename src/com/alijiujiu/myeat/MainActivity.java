package com.alijiujiu.myeat;

import com.alijiujiu.kongcheng.FirstUseActivityActivity;
import com.alijiujiu.tools.BmobId;
import com.baidu.mapapi.SDKInitializer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

public class MainActivity extends Activity {

	protected static final String SHARE_APP_TAG = null;
	ImageView backgroungView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 不显示title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 百度地图工具初始化
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		Bmob.initialize(this, BmobId.APPLICATION_ID);

		loadViews();
		backgroungView.setImageResource(R.drawable.activate_pic5);
		// 启动画面
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(MainActivity.this, IndexActivity.class);
				Intent intent2 = new Intent(MainActivity.this, FirstUseActivityActivity.class);

				SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
				Boolean user_first = setting.getBoolean("FIRST", true);
				if (user_first) {
					setting.edit().putBoolean("FIRST", false).commit();
					startActivity(intent2);
				} else {
					// 从启动动画ui跳转到主ui
					startActivity(intent);
				}
				MainActivity.this.finish(); // 结束启动动画界面
			}
		}, 1000); // 启动动画持续3秒钟
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadViews() {
		backgroungView = (ImageView) findViewById(R.id.main_background);
	}
}
