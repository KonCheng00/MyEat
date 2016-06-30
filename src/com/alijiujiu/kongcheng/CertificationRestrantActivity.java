package com.alijiujiu.kongcheng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.alijiujiu.koncheng.restrant.CertificationRestrant;
import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.myeat.LoginActivity;
import com.alijiujiu.myeat.R;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class CertificationRestrantActivity extends Activity {

	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 121;

	private BmobUser user;
	private String source;
	private String restrantId;

	private EditText nameView;
	private EditText addressView;
	private Button locationButton;
	private EditText shopPhoneView;
	private Spinner spinner1;
	private Spinner spinner2;
	private EditText certificationManNameView;
	private EditText certificationManTelephoneView;
	private ImageView shopPictureView;
	private ImageView licensePictureView;
	private EditText licenseNumberView;
	private ImageView idCardPictureView;
	private ImageView holdIdCardPictureView;
	private EditText idCardNumberView;
	private EditText titleView;
	private EditText tasteView;
	private EditText cuisineView;
	private EditText priceView;
	private Button submit;

	private List<String> spinner1List = new ArrayList<String>();
	private List<String> spinner2List = new ArrayList<String>();

	private ArrayAdapter<String> spinner1Adapter;
	private ArrayAdapter<String> spinner2Adapter;

	private String shopPicturePath;
	private String licensePicturePath;
	private String idCardPicturePath;
	private String holdIdCardPicturePath;
	private String name;
	private String address;
	private String shopPhone;
	private int industryType;
	private String certificationManName;
	private String certificationManTelephone;
	private String licenseNumber;
	private String idCardNumber;
	private String title;
	private String taste;
	private String cuisine;
	private float price;
	private LatLng latlng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_certification_restrant);

		// 取消TEXTVIEW自动获取焦点
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
				| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
		user = BmobUser.getCurrentUser(this);
		restrantId = getIntent().getStringExtra("restrantId");
		if (user == null) {
			Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			loadViews();
			if (restrantId == null) {

			} else {
				{
				BmobQuery<Restrant> query = new BmobQuery<Restrant>();
				query.getObject(getApplicationContext(), restrantId, new GetListener<Restrant>() {

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Restrant restrant) {
						// TODO Auto-generated method stub
						nameView.setText(restrant.getName());
						addressView.setText(restrant.getAddress());
						if(restrant.getLocation() != null){
							locationButton.setText("打开地图并标注地点的精确位置（已标注）");
						}
						shopPhoneView.setText(restrant.getShopPhone());
						
					}

				});
			}
			}
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 10 && resultCode == 10) {
			latlng = data.getParcelableExtra("latlng");
			if(latlng != null){
				locationButton.setText("打开地图并标注地点的精确位置（已标注）");
			}
		} else {
			ImageView iv = (ImageView) findViewById(requestCode);

			if (resultCode != RESULT_OK) {

				Log.e("TAG->onresult", "ActivityResult resultCode error");

				return;

			}

			Bitmap bm = null;

			// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
			ContentResolver resolver = getContentResolver();

			// 此处的用于判断接收的Activity是不是你想要的那个
			try {
				Uri originalUri = data.getData(); // 获得图片的uri

				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				// 显得到bitmap图片
				iv.setImageBitmap(bm);

				// 这里开始的第二部分，获取图片的路径：
				String[] proj = { MediaStore.Images.Media.DATA };

				// 好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null, null);

				// 按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();

				// 最后根据索引值获取图片路径
				String path = cursor.getString(column_index);

				if (path == null) {
					Toast.makeText(this, "无法获取图片路径，请确保图片选取自您的 相册 ！", Toast.LENGTH_SHORT).show();
				} else {
					switch (requestCode) {
					case R.id.certification_restrant_shopPicture:
						shopPicturePath = path;
						Log.i("shopPicturePath", "" + path);
						break;
					case R.id.certification_restrant_licensePicture:
						licensePicturePath = path;
						Log.i("licensePicturePath", "" + path);
						break;
					case R.id.certification_restrant_idCardPicture:
						idCardPicturePath = path;
						Log.i("idCardPicturePath", "" + path);
						break;
					case R.id.certification_restrant_holdIdCardPicture:
						holdIdCardPicturePath = path;
						Log.i("holdIdCardPicturePath", "" + path);
						break;
					}
				}
			} catch (IOException e) {

				Log.e("TAG-->Error", e.toString());

			}
		}
	}

	private void loadViews() {
		nameView = (EditText) findViewById(R.id.certification_restrant_name);
		addressView = (EditText) findViewById(R.id.certification_restrant_address);
		locationButton = (Button) findViewById(R.id.certification_restrant_location);
		shopPhoneView = (EditText) findViewById(R.id.certification_restrant_shopPhone);
		spinner1 = (Spinner) findViewById(R.id.certification_restrant_industry1);
		spinner2 = (Spinner) findViewById(R.id.certification_restrant_industry2);
		certificationManNameView = (EditText) findViewById(R.id.certification_restrant_certificationManName);
		certificationManTelephoneView = (EditText) findViewById(R.id.certification_restrant_certificationManTelephone);
		shopPictureView = (ImageView) findViewById(R.id.certification_restrant_shopPicture);
		licensePictureView = (ImageView) findViewById(R.id.certification_restrant_licensePicture);
		licenseNumberView = (EditText) findViewById(R.id.certification_restrant_licenseNumber);
		idCardPictureView = (ImageView) findViewById(R.id.certification_restrant_idCardPicture);
		holdIdCardPictureView = (ImageView) findViewById(R.id.certification_restrant_holdIdCardPicture);
		idCardNumberView = (EditText) findViewById(R.id.certification_restrant_idCardNumber);
		titleView = (EditText) findViewById(R.id.certification_restrant_title);
		tasteView = (EditText) findViewById(R.id.certification_restrant_taste);
		cuisineView = (EditText) findViewById(R.id.certification_restrant_cuisine);
		priceView = (EditText) findViewById(R.id.certification_restrant_price);
		submit = (Button) findViewById(R.id.certification_restrant_submit);

		locationButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CertificationRestrantActivity.this, LocationActivity.class);
				startActivityForResult(intent, 10);
			}

		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit();
			}

		});

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType(IMAGE_TYPE);
				startActivityForResult(getAlbum, v.getId());
			}

		};

		shopPictureView.setOnClickListener(listener);

		licensePictureView.setOnClickListener(listener);

		idCardPictureView.setOnClickListener(listener);

		holdIdCardPictureView.setOnClickListener(listener);

		initSpinner();
	}

	private void initSpinner() {
		// TODO Auto-generated method stub
		spinner1List.add("一级行业");
		spinner1List.add("美食");

		spinner1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner1List);
		spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(spinner1Adapter);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		spinner2List.add("二级行业");
		spinner2List.add("中餐厅");
		spinner2List.add("西餐厅");
		spinner2List.add("日本菜");
		spinner2List.add("韩国菜");
		spinner2List.add("东南亚菜");
		spinner2List.add("自助餐");
		spinner2List.add("快餐");
		spinner2List.add("小吃");
		spinner2List.add("蛋糕甜点");
		spinner2List.add("烧烤");
		spinner2List.add("其他");

		spinner2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner2List);
		spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(spinner2Adapter);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				industryType = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void submit() {

		name = nameView.getText().toString();
		address = addressView.getText().toString();
		shopPhone = shopPhoneView.getText().toString();
		certificationManName = certificationManNameView.getText().toString();
		certificationManTelephone = certificationManTelephoneView.getText().toString();
		licenseNumber = licenseNumberView.getText().toString();
		idCardNumber = idCardNumberView.getText().toString();
		title = titleView.getText().toString();
		taste = tasteView.getText().toString();
		cuisine = cuisineView.getText().toString();
		try {
			price = Float.parseFloat(priceView.getText().toString());
		} catch (NumberFormatException e) {
			Toast.makeText(this, "请输入正确的人均消费价格", Toast.LENGTH_SHORT).show();
		}

		// BmobFile shopPicture = new BmobFile(new File(shopPicturePath));
		final String[] filePaths = new String[4];
		filePaths[0] = shopPicturePath;
		filePaths[1] = licensePicturePath;
		filePaths[2] = idCardPicturePath;
		filePaths[3] = holdIdCardPicturePath;
		BmobFile.uploadBatch(CertificationRestrantActivity.this, filePaths, new UploadBatchListener() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CertificationRestrantActivity.this, "图片上传失败" + arg1, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onProgress(int arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(CertificationRestrantActivity.this, "图片上传中", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<BmobFile> files, List<String> urls) {
				// TODO Auto-generated method stub
				if (files.size() == filePaths.length) {
					Toast.makeText(CertificationRestrantActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
					CertificationRestrant cr = new CertificationRestrant();
					cr.setName(name);
					cr.setAddress(address);
					cr.setShopPhone(shopPhone);
					cr.setIndustryType(industryType);
					cr.setCertificationManName(certificationManName);
					cr.setCertificationManTelephone(certificationManTelephone);
					cr.setShopPicture(files.get(0));
					cr.setLicensePicture(files.get(1));
					cr.setLicenseNumber(licenseNumber);
					cr.setIdCardPicture(files.get(2));
					cr.setHoldIdCardPicture(files.get(3));
					cr.setIdCardNumber(idCardNumber);
					cr.setCertificationType(0);
					cr.setCertificationManId("");
					cr.setTitle(title);
					cr.setTaste(taste);
					cr.setCuisine(cuisine);
					cr.setPrice(price);
					cr.setCertificationManId(user.getObjectId());
					cr.setRestrantId(restrantId);
					if (latlng != null) {
						cr.setLocation(latlng);
					}
					cr.save(CertificationRestrantActivity.this, new SaveListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(CertificationRestrantActivity.this, "上传失败" + arg1, Toast.LENGTH_SHORT)
									.show();
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(CertificationRestrantActivity.this, "信息提交成功", Toast.LENGTH_SHORT).show();
							finish();
						}

					});
				}
			}

		});

	}
}
