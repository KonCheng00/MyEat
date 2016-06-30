package com.alijiujiu.kongcheng;

import java.io.File;

import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.myeat.R;
import com.baidu.mapapi.search.poi.PoiDetailResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;

public class RecommendResultActivity extends Activity {

	private Restrant restrant;
	private String resName;
	private String uid;

	private ImageView pic;
	private Button detail;
	private TextView name;
	private Button price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recommand_result);

		restrant = (Restrant) getIntent().getSerializableExtra("restrant");
		resName = getIntent().getStringExtra("ResName");
		uid = getIntent().getStringExtra("UID");
		if (restrant != null) {
			loadViews();
			name.setText(restrant.getName());
			BmobFile file = restrant.getShopPicture();
			if (file != null) {
				file.download(getApplicationContext(), new DownloadFileListener() {

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String path) {
						// TODO Auto-generated method stub
						File file = new File(path);
						if (file.exists()) {
							Bitmap bm = BitmapFactory.decodeFile(path);
							pic.setImageBitmap(bm);
						}
					}

				});
			}
		} else {
			loadViews();
			name.setText(resName);
		}
	}

	private void loadViews() {
		pic = (ImageView) findViewById(R.id.result_picture);
		detail = (Button) findViewById(R.id.result_detail);
		name = (TextView) findViewById(R.id.textView1);
		price = (Button) findViewById(R.id.index_condition2);
		detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecommendResultActivity.this, RecommendRestrantActivity.class);
				if (restrant != null) {
					intent.putExtra("PoiUid", restrant.getObjectId());
					intent.putExtra("source", 0);
				} else if (restrant == null) {
					intent.putExtra("PoiUid", uid);
					intent.putExtra("source", 1);
				}
				startActivity(intent);
			}

		});
	}
}
