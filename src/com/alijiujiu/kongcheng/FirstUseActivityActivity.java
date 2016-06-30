package com.alijiujiu.kongcheng;

import java.util.ArrayList;
import java.util.List;

import com.alijiujiu.myeat.IndexActivity;
import com.alijiujiu.myeat.LoginActivity;
import com.alijiujiu.myeat.R;
import com.alijiujiu.myeat.R.id;
import com.alijiujiu.myeat.R.layout;
import com.alijiujiu.myeat.R.menu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class FirstUseActivityActivity extends Activity {

	private BmobUser user;
	private MyUser myUser;

	private Button setPreference;
	private Button quit;
	private TextView login;
	private FrameLayout frameLayout;
	private Spinner nationSpinner;
	private Button submit;

	private Button taste_la;
	private Button taste_tian;
	private Button taste_suan;
	private Button taste_ku;
	private Button cuisine_lu;
	private Button cuisine_chuan;
	private Button cuisine_yue;
	private Button cuisine_su;
	private Button cuisine_zhe;
	private Button cuisine_min;
	private Button cuisine_xiang;
	private Button cuisine_hui;
	
	List<Button> tasteButtonList=new ArrayList<Button>();
	List<Button> cuisineButtonList=new ArrayList<Button>();

	private List<String> nationList = new ArrayList<String>();
	private ArrayAdapter nationAdapter;

	private int nation = 0;
	private String taste = "";
	private String cuisine = "";

	SharedPreferences SP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 不显示title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first_use_activity);

		final LayoutInflater inflater = LayoutInflater.from(this);

	//	user = BmobUser.getCurrentUser(this);
		
		SP = getSharedPreferences("SHARE_APP_TAG", MODE_PRIVATE);
		taste = SP.getString("TASTE", "");
		cuisine = SP.getString("CUISINE", "");
		nation = SP.getInt("NATION", 0);

		frameLayout = (FrameLayout) findViewById(R.id.framelayout);
		setPreference = (Button) findViewById(R.id.set_preference);
		quit = (Button) findViewById(R.id.quit);
		quit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstUseActivityActivity.this, IndexActivity.class);
				startActivity(intent);
			}
			
		});
		login = (TextView) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstUseActivityActivity.this, LoginActivity.class);
				// startActivity(intent);
				startActivityForResult(intent, 1);
			}

		});

		setPreference.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				frameLayout.removeAllViews();
				LinearLayout linearlayout = (LinearLayout) inflater.inflate(R.layout.set_preferences, null)
						.findViewById(R.id.set_preferences);
				frameLayout.addView(linearlayout);
				initSpinner();
				set_preference();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// BmobUser user=BmobUser.getCurrentUser(this);
		user = BmobUser.getCurrentUser(this);
		myUser = new MyUser();
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.getObject(this, user.getObjectId(), new GetListener<MyUser>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("FirstUseActivity", "Login fail " + arg1);
			}

			@Override
			public void onSuccess(MyUser user) {
				// TODO Auto-generated method stub
				myUser = user;
				Log.i("FirstUseActivity", "UserName:" + user.getUsername());
			}

		});
		if (myUser != null) {
			if (myUser.getNation() != null) {
				nation = myUser.getNation();
			}
			if (myUser.getTaste() != null) {
				taste = myUser.getTaste();
			}
			if (myUser.getCuisine() != null) {
				cuisine = myUser.getCuisine();
			}
			Log.i("FirstUseActivity", "Nation:" + nation + " Taste:" + taste + " Cuisine:" + cuisine);
			SP.edit().putString("TASTE", taste).commit();
			SP.edit().putString("CUISINE", cuisine).commit();
			SP.edit().putInt("NATION", nation).commit();
		} else {
			Log.i("FirstUseActivity", "User is NULL");
		}
	}

	private void loadViews(){
		taste_la = (Button) findViewById(R.id.preference_taste_la);
		taste_tian = (Button) findViewById(R.id.preference_taste_tian);
		taste_suan = (Button) findViewById(R.id.preference_taste_suan);
		taste_ku = (Button) findViewById(R.id.preference_taste_ku);
		cuisine_lu = (Button) findViewById(R.id.preference_cuisine_lu);
		cuisine_chuan = (Button) findViewById(R.id.preference_cuisine_chuan);
		cuisine_yue = (Button) findViewById(R.id.preference_cuisine_yue);
		cuisine_su = (Button) findViewById(R.id.preference_cuisine_su);
		cuisine_zhe = (Button) findViewById(R.id.preference_cuisine_zhe);
		cuisine_min = (Button) findViewById(R.id.preference_cuisine_min);
		cuisine_xiang = (Button) findViewById(R.id.preference_cuisine_xiang);
		cuisine_hui = (Button) findViewById(R.id.preference_cuisine_hui);
		
		cuisineButtonList.add(cuisine_chuan);
		cuisineButtonList.add(cuisine_lu);
		cuisineButtonList.add(cuisine_yue);
		cuisineButtonList.add(cuisine_su);
		cuisineButtonList.add(cuisine_zhe);
		cuisineButtonList.add(cuisine_min);
		cuisineButtonList.add(cuisine_xiang);
		cuisineButtonList.add(cuisine_hui);
		tasteButtonList.add(taste_suan);
		tasteButtonList.add(taste_tian);
		tasteButtonList.add(taste_ku);
		tasteButtonList.add(taste_la);
		
		nationSpinner.setSelection(nation);
		
	}
	
	private void initTasteAndCuisineButton(){
		for(int i=0;i<tasteButtonList.size();i++)
		{
			if(taste.contains(tasteButtonList.get(i).getText()))
			{
				tasteButtonList.get(i).setBackgroundColor(Color.parseColor("#a0522d"));
			}
		}
		
		for(int i=0;i<cuisineButtonList.size();i++)
		{
			if(cuisine.contains(cuisineButtonList.get(i).getText()))
			{
				cuisineButtonList.get(i).setBackgroundColor(Color.parseColor("#a0522d"));
			}
		}
		
	}
	
	private void set_preference() {
		
		loadViews();
		initTasteAndCuisineButton();
		
		submit = (Button) findViewById(R.id.preference_submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (user != null) {
					nation = SP.getInt("NATION", 0);
					taste = SP.getString("TASTE", "");
					cuisine = SP.getString("CUISINE", "");
					MyUser newUser = new MyUser();
					newUser.setNation(nation);
					newUser.setTaste(taste);
					newUser.setCuisine(cuisine);

					newUser.update(FirstUseActivityActivity.this, user.getObjectId(), new UpdateListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(FirstUseActivityActivity.this, "信息更新失败:" + arg1, Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(FirstUseActivityActivity.this, "信息更新成功", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(FirstUseActivityActivity.this, IndexActivity.class);
							intent.putExtra("FirstUse", true);
							startActivity(intent);
						}

					});
				} else {
					Intent intent = new Intent(FirstUseActivityActivity.this, IndexActivity.class);
					intent.putExtra("FirstUse", true);
					startActivity(intent);
				}

			}

		});

		

		TasteListener tasteListener = new TasteListener();
		CuisineListener cuisineListener = new CuisineListener();

		taste_la.setOnClickListener(tasteListener);
		taste_tian.setOnClickListener(tasteListener);
		taste_suan.setOnClickListener(tasteListener);
		taste_ku.setOnClickListener(tasteListener);
		cuisine_lu.setOnClickListener(cuisineListener);
		cuisine_chuan.setOnClickListener(cuisineListener);
		cuisine_yue.setOnClickListener(cuisineListener);
		cuisine_su.setOnClickListener(cuisineListener);
		cuisine_zhe.setOnClickListener(cuisineListener);
		cuisine_min.setOnClickListener(cuisineListener);
		cuisine_xiang.setOnClickListener(cuisineListener);
		cuisine_hui.setOnClickListener(cuisineListener);

	}

	private void initSpinner() {

		nationSpinner = (Spinner) findViewById(R.id.preference_nation);
		nationList.add("汉族");
		nationList.add("回族");

		nationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nationList);
		nationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		nationSpinner.setAdapter(nationAdapter);
		nationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.i("SharePreference", "" + position);
				nation = position;
				SP.edit().putInt("NATION", nation).commit();

				Log.i("SharedPreferences", "" + SP.getInt("NATION", 0));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
	}

	private class TasteListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Button button = (Button) findViewById(v.getId());
			// 如果字符串中有
			if (taste.contains(button.getText() + ";")) {

				// 删掉
				taste = taste.replace(button.getText() + ";", "");
				SP.edit().putString("TASTE", taste).commit();
				button.setBackgroundColor(Color.parseColor("#ff5555"));

			} else {

				taste = taste.concat(button.getText() + ";");
				SP.edit().putString("TASTE", taste).commit();
				button.setBackgroundColor(Color.parseColor("#a0522d"));
			}
		}

	}

	private class CuisineListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button button = (Button) findViewById(v.getId());
			if (cuisine.contains(button.getText() + ";")) {
				cuisine = cuisine.replace(button.getText() + ";", "");
				SP.edit().putString("CUISINE", cuisine).commit();
				button.setBackgroundColor(Color.parseColor("#03a9f4"));
			} else {
				cuisine = cuisine.concat(button.getText() + ";");
				SP.edit().putString("CUISINE", cuisine).commit();
				button.setBackgroundColor(Color.parseColor("#a0522d"));
			}
		}

	}
}
