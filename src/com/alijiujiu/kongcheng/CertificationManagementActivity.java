package com.alijiujiu.kongcheng;

import com.alijiujiu.myeat.LoginActivity;
import com.alijiujiu.myeat.MyCertificationActivity;
import com.alijiujiu.myeat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

public class CertificationManagementActivity extends Activity {
	
	private BmobUser user;
	private LinearLayout certificationRestrant;
	private LinearLayout myCertification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_certification_management);
		
		user = BmobUser.getCurrentUser(this);
		if(user == null){
			Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}else{
			loadViews();
		}
	}
	
	private void loadViews(){
		certificationRestrant = (LinearLayout)findViewById(R.id.certificationRestrant);
		myCertification = (LinearLayout)findViewById(R.id.myCertification);
		certificationRestrant.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CertificationManagementActivity.this,CertificationRestrantActivity.class);
				startActivity(intent);
			}
			
		});
		myCertification.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CertificationManagementActivity.this, MyCertificationActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
}
