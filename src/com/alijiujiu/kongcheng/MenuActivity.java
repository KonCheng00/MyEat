package com.alijiujiu.kongcheng;

import com.alijiujiu.myeat.LoginActivity;
import com.alijiujiu.myeat.R;
import com.alijiujiu.myeat.R.id;
import com.alijiujiu.myeat.R.layout;
import com.alijiujiu.myeat.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class MenuActivity extends Activity {

	private BmobUser user;
	
	private TextView nameView;
	private LinearLayout fondRestrantList;
	private LinearLayout certificationRestrant;
	private LinearLayout coupleBack;
	private LinearLayout contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		
		loadViews();
		
		user = BmobUser.getCurrentUser(this);
		if(user == null){
			nameView.setText("尚未登录");
			nameView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				
			});
		} else {
			nameView.setText(user.getUsername());
		}
		
	}

	private void loadViews(){
		nameView = (TextView) findViewById(R.id.sliding_menu_nickname);
		fondRestrantList = (LinearLayout) findViewById(R.id.sliding_menu_fond_restrant_list);
		certificationRestrant = (LinearLayout) findViewById(R.id.sliding_menu_certification_restrant);
		coupleBack = (LinearLayout) findViewById(R.id.sliding_menu_couple_back);
		contact = (LinearLayout) findViewById(R.id.sliding_menu_contact);
		fondRestrantList.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this, FondRestrantListActivity.class);
				startActivity(intent);
			}
			
		});
		
		certificationRestrant.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this, CertificationManagementActivity.class);
				startActivity(intent);
			}
			
		});
		
		coupleBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		contact.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
