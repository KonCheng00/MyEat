package com.alijiujiu.myeat;

import com.alijiujiu.kongcheng.MyUser;
import com.alijiujiu.tools.MobileTools;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisteActivity extends Activity {
	
	private String phone;
	private String confirmCode;
	private String password;
	private String confirmPassword;

	private EditText phoneNumberView;
	private EditText confirmCodeView;
	private EditText passwordView;
	private EditText confirmPasswordView;
	private Button sendConfirmCodeButton;
	private Button registeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registe);
		
		loadViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registe, menu);
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
		phoneNumberView = (EditText) findViewById(R.id.registe_phone);
		confirmCodeView = (EditText) findViewById(R.id.registe_confirm_code);
		passwordView = (EditText) findViewById(R.id.registe_password);
		confirmPasswordView = (EditText) findViewById(R.id.registe_confirm_passowrd);
		sendConfirmCodeButton = (Button) findViewById(R.id.registe_send_confirm_code);
		registeButton = (Button) findViewById(R.id.registe_registe);
		sendConfirmCodeButton.setOnClickListener(new SendConfirmCodeListener());
		registeButton.setOnClickListener(new RegisteListener());
	}

	class SendConfirmCodeListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			phone = phoneNumberView.getText().toString();
			if (MobileTools.isMobileNoMatch(phone)) {
				BmobSMS.requestSMSCode(RegisteActivity.this, phone, "Register", new RequestSMSCodeListener() {

					@Override
					public void done(Integer arg0, BmobException arg1) {
						// TODO Auto-generated method stub
						if (arg1 == null) {
							Toast.makeText(RegisteActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
						}
					}

				});

			} else {
				Toast.makeText(RegisteActivity.this, "无效的手机号码", Toast.LENGTH_LONG).show();
			}
		}

	}

	class RegisteListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			phone = phoneNumberView.getText().toString();
			confirmCode = confirmCodeView.getText().toString();
			password = passwordView.getText().toString();
			confirmPassword = confirmPasswordView.getText().toString();
			BmobSMS.verifySmsCode(RegisteActivity.this, phone, confirmCode, new VerifySMSCodeListener() {

				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if (arg0 == null) {

						if (!passwordVerify(password)) {

						} else if (!password.equals(confirmPassword)) {
							Toast.makeText(RegisteActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
						} else {

							MyUser newUser = new MyUser();
							newUser.setUsername(phone);
							newUser.setMobilePhoneNumber(phone);
							newUser.setMobilePhoneNumberVerified(true);
							newUser.setPassword(password);
							newUser.signUp(RegisteActivity.this, new SaveListener() {

								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									Toast.makeText(RegisteActivity.this, "注册失败" + arg1, Toast.LENGTH_LONG).show();
								}

								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									Toast.makeText(RegisteActivity.this, "注册成功", Toast.LENGTH_LONG).show();
									finish();
								}

							});
						}
					} else {
						Toast.makeText(RegisteActivity.this, "未能通过验证", Toast.LENGTH_LONG).show();
					}
				}

			});
		}

	}

	boolean passwordVerify(String password) {
		boolean verify = false;
		if (password.length() < 6) {
			Toast.makeText(RegisteActivity.this, "密码太短\n6-16位的英文字母、数字、“-”、“_”的任意组合", Toast.LENGTH_LONG).show();
		} else if (password.length() > 16) {
			Toast.makeText(RegisteActivity.this, "密码太长\n6-16位的英文字母、数字、“-”、“_”的任意组合", Toast.LENGTH_LONG).show();
		} else {
			char a[] = password.toCharArray();
			for (int i = 0; i < a.length; i++) {
				if (!(a[i] == 45 || a[i] == 95 || (a[i] >= 48 && a[i] <= 57) || (a[i] >= 65 && a[i] <= 90)
						|| (a[i] >= 97 || a[i] <= 122))) {
					verify = false;
					Toast.makeText(RegisteActivity.this, "密码存在非法字符\n6-16位的英文字母、数字、“-”、“_”的任意组合", Toast.LENGTH_LONG)
							.show();
					break;
				}
				verify = true;
			}
		}
		return verify;
	}
}
