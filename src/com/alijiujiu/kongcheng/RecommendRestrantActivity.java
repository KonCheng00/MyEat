package com.alijiujiu.kongcheng;

import java.io.File;
import java.util.List;

import com.alijiujiu.koncheng.locationService.LocationServiceManager;
import com.alijiujiu.koncheng.restrant.FondRestrant;
import com.alijiujiu.koncheng.restrant.Restrant;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeEvent;
import com.alijiujiu.koncheng.valueChangeListener.ValueChangeListener;
import com.alijiujiu.myeat.R;
import com.baidu.mapapi.search.poi.PoiDetailResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

public class RecommendRestrantActivity extends Activity {

	private BmobUser user;

	private int source;
	private String poiUid;
	private boolean fond = false;

	private ImageView restrantPic;
	private ImageView background;
	private FrameLayout frameLayout1;
	private LinearLayout linearLayout1;
	private LinearLayout linearLayout2;
	private LinearLayout linearLayout3;
	private LinearLayout linearLayout4;
	private LinearLayout linearLayout5;
	private LinearLayout getAllRestrant;

	private TextView nameView;
	private RatingBar overallRatingView;
	private TextView overallRatingTextView;
	private TextView priceView;
	private TextView renzhengView;
	private TextView baiduRenzhengView;
	private TextView addressView;
	private TextView telephoneView;
	private Button certificationRestrant;
	private ImageButton fondButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 不显示title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recommend_restrant);
		user = BmobUser.getCurrentUser(getApplicationContext());
		Intent intent = getIntent();
		poiUid = intent.getStringExtra("PoiUid");
		source = intent.getIntExtra("source", 0);
		loadViews();
		getDetailInfomation();
	}

	private void getDetailInfomation() {
		// TODO Auto-generated method stub

		BmobQuery<Restrant> query = new BmobQuery<Restrant>();
		query.getObject(getApplicationContext(), poiUid, new GetListener<Restrant>() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 == 101) {
					ValueChangeListener listener = new ValueChangeListenerImpl();
					LocationServiceManager manager = new LocationServiceManager(listener);
					if (poiUid != null && !poiUid.equals("")) {
						manager.searchDetail(poiUid);
					}
				}
			}

			@Override
			public void onSuccess(Restrant result) {
				// TODO Auto-generated method stub
				final Restrant res = result;
				nameView.setText(result.getName());
				overallRatingView.setRating((float) result.getOverallRating());
				overallRatingTextView.setText("" + result.getOverallRating());
				priceView.setText("￥" + result.getPrice() + "/人");
				addressView.setText(result.getAddress());
				telephoneView.setText(result.getShopPhone());
				if (result.getCertificationType() == 60) {
					renzhengView.setVisibility(View.VISIBLE);
				} else {
					renzhengView.setVisibility(View.INVISIBLE);
				}
				if (user != null) {
					BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
					query.addWhereEqualTo("userId", user.getObjectId());
					query.findObjects(getApplicationContext(), new FindListener<FondRestrant>() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(List<FondRestrant> list) {
							// TODO Auto-generated method stub
							for (FondRestrant restrant : list) {
								if (source == 0) {
									if (restrant.getRestrantId() != null && res.getObjectId() != null)
										if (restrant.getRestrantId().equals(res.getObjectId())) {
											fond = true;
											fondButton.setImageResource(R.drawable.redheart);
											break;
										}
								} else {
									if (restrant.getBaiduId() != null && res.getBaiduId() != null)
										if (restrant.getBaiduId().equals(res.getBaiduId())) {
											fond = true;
											fondButton.setImageResource(R.drawable.redheart);
											break;
										}
								}
							}
						}

					});
				}
				if (fond) {
					fondButton.setImageResource(R.drawable.redheart);
				} else {
					fondButton.setImageResource(R.drawable.whiteheart);
				}
				BmobFile file = result.getShopPicture();
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
								restrantPic.setImageBitmap(bm);
								background.setImageBitmap(bm);
							}
						}

					});
				}
			}

		});

	}

	private void loadViews() {

		WindowManager wm = this.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();

		restrantPic = (ImageView) findViewById(R.id.restrant_pic);
		frameLayout1 = (FrameLayout) findViewById(R.id.restrant_framelayout1);
		linearLayout1 = (LinearLayout) findViewById(R.id.restrant_linearlayout1);
		linearLayout2 = (LinearLayout) findViewById(R.id.restrant_linearlayout2);
		linearLayout3 = (LinearLayout) findViewById(R.id.restrant_get_detail_information);
		linearLayout4 = (LinearLayout) findViewById(R.id.restrant_linearlayout4);
		getAllRestrant = (LinearLayout) findViewById(R.id.get_all_restrant);

		LayoutParams lp = restrantPic.getLayoutParams();
		lp.height = LayoutParams.FILL_PARENT;
		lp.width = height / 4;
		restrantPic.setLayoutParams(lp);

		lp = frameLayout1.getLayoutParams();
		lp.height = height / 3;
		lp.width = LayoutParams.FILL_PARENT;
		frameLayout1.setLayoutParams(lp);

		lp = linearLayout1.getLayoutParams();
		lp.height = height / 3;
		lp.width = LayoutParams.FILL_PARENT;
		linearLayout1.setLayoutParams(lp);

		// lp = linearLayout2.getLayoutParams();
		// lp.height = height / 3;
		// lp.width = LayoutParams.FILL_PARENT;
		// linearLayout2.setLayoutParams(lp);

		lp = linearLayout3.getLayoutParams();
		lp.height = height / 6;
		lp.width = LayoutParams.FILL_PARENT;
		linearLayout3.setLayoutParams(lp);

		lp = linearLayout4.getLayoutParams();
		lp.height = height / 6;
		lp.width = LayoutParams.FILL_PARENT;
		linearLayout4.setLayoutParams(lp);

		getAllRestrant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecommendRestrantActivity.this, AllRestrantActivity.class);
				startActivity(intent);
			}

		});

		nameView = (TextView) findViewById(R.id.restrant_name);
		overallRatingView = (RatingBar) findViewById(R.id.restrant_overall_ratingBar);
		overallRatingTextView = (TextView) findViewById(R.id.restrant_overall_rating);
		priceView = (TextView) findViewById(R.id.restrant_price);
		renzhengView = (TextView) findViewById(R.id.restrant_renzheng);
		baiduRenzhengView = (TextView) findViewById(R.id.restrant_baidurenzheng);
		addressView = (TextView) findViewById(R.id.restrant_address);
		telephoneView = (TextView) findViewById(R.id.restrant_telephone);
		certificationRestrant = (Button) findViewById(R.id.restrant_certification);
		background = (ImageView) findViewById(R.id.restrant_backimage);
		certificationRestrant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecommendRestrantActivity.this, CertificationRestrantActivity.class);
				intent.putExtra("source", source);
				intent.putExtra("restrantId", poiUid);
				startActivity(intent);
			}

		});
		fondButton = (ImageButton) findViewById(R.id.restrant_fond);
		fondButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fond) {
					fond = false;
					fondButton.setImageResource(R.drawable.whiteheart);

					BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
					if (source == 0) {
						query.addWhereEqualTo("restrantId", poiUid);
						query.addWhereEqualTo("userId", user.getObjectId());
					} else {
						query.addWhereEqualTo("baiduId", poiUid);
						query.addWhereEqualTo("userId", user.getObjectId());
					}
					query.findObjects(getApplicationContext(), new FindListener<FondRestrant>() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(List<FondRestrant> list) {
							// TODO Auto-generated method stub
							for (FondRestrant restrant : list) {
								restrant.delete(getApplicationContext(), new DeleteListener() {

									@Override
									public void onFailure(int arg0, String arg1) {
										// TODO Auto-generated method stub
										fond = true;
										fondButton.setImageResource(R.drawable.redheart);
										Toast.makeText(getApplicationContext(), "操作失败", Toast.LENGTH_LONG).show();
										Log.i("Fail", "code:" + arg0 + " msg:" + arg1);
									}

									@Override
									public void onSuccess() {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(), "已从喜欢的餐厅列表删除", Toast.LENGTH_LONG)
												.show();
									}

								});
							}
						}

					});

				} else {
					fond = true;
					fondButton.setImageResource(R.drawable.redheart);

					FondRestrant restrant = new FondRestrant();
					if (source == 0) {
						restrant.setRestrantId(poiUid);
					} else {
						restrant.setBaiduId(poiUid);
					}
					restrant.setUserId(user.getObjectId());
					restrant.save(getApplicationContext(), new SaveListener() {

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							fond = false;
							fondButton.setImageResource(R.drawable.whiteheart);
							Toast.makeText(getApplicationContext(), "操作失败", Toast.LENGTH_LONG).show();
							Log.i("Fail", "code:" + arg0 + " msg:" + arg1);
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "已标记为喜欢", Toast.LENGTH_LONG).show();
						}

					});
				}
			}

		});
	}

	private class ValueChangeListenerImpl implements ValueChangeListener {

		@Override
		public void onBDLocationChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPoiResultChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPoiDetailResultChanged(ValueChangeEvent event) {
			// TODO Auto-generated method stub
			final ValueChangeEvent evt = event;
			PoiDetailResult result = null;
			if (event != null) {
				result = event.getDetailResult();
			}
			if (result != null) {
				nameView.setText(result.getName());
				overallRatingView.setRating((float) result.getOverallRating());
				overallRatingTextView.setText("" + result.getOverallRating());
				priceView.setText("￥" + result.getPrice() + "/人");
				addressView.setText(result.getAddress());
				telephoneView.setText(result.getTelephone());

				renzhengView.setVisibility(View.INVISIBLE);

				if (user != null) {
					BmobQuery<FondRestrant> query = new BmobQuery<FondRestrant>();
					query.addWhereEqualTo("userId", user.getObjectId());
					query.findObjects(getApplicationContext(), new FindListener<FondRestrant>() {

						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(List<FondRestrant> list) {
							// TODO Auto-generated method stub
							for (FondRestrant restrant : list) {
								if (restrant.getBaiduId() != null && evt.getDetailResult() != null)
									if (restrant.getBaiduId().equals(evt.getDetailResult().getUid())) {
										fond = true;
										fondButton.setImageResource(R.drawable.redheart);
										break;
									}
							}
						}

					});
				}
				if (fond) {
					fondButton.setImageResource(R.drawable.redheart);
				} else {
					fondButton.setImageResource(R.drawable.whiteheart);
				}

			}
		}

	}
}
