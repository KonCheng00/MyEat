package com.alijiujiu.kongcheng;

import com.alijiujiu.myeat.R;
import com.alijiujiu.myeat.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class CertificationDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_certification_details);
	}
	
	
}
