package com.alijiujiu.myeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity {

	private String phone;
	private String password;
	
	private EditText phoneView;
	private EditText passwordView;
	private Button login;
	private TextView registe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		loadViews();
	}
	
	private void loadViews(){
		phoneView=(EditText) findViewById(R.id.login_phone);
		passwordView=(EditText) findViewById(R.id.login_password);
		login=(Button) findViewById(R.id.login_submit);
		registe=(TextView) findViewById(R.id.login_registe);
		login.setOnClickListener(new LoginListener());
		registe.setOnClickListener(new RegisteListener());
	}
	
	class RegisteListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(LoginActivity.this,RegisteActivity.class);
			startActivity(intent);
		}
		
	}
	class LoginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			phone=phoneView.getText().toString();
			password=passwordView.getText().toString();
			if(phone==null||phone.equals("")){
				Toast.makeText(LoginActivity.this, "电话号码不能为空", Toast.LENGTH_LONG).show();
			}else if(password==null||password.equals("")){
				Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else{
				BmobUser user=new BmobUser();
				user.setUsername(phone);
				user.setPassword(password);
				user.login(LoginActivity.this, new SaveListener(){

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(LoginActivity.this, "登录失败"+arg1, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
						finish();
					}
					
				});
			}
		}
		
	}
	
}
