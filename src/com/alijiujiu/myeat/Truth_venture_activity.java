package com.alijiujiu.myeat;

import java.util.Random;

import com.alijiujiu.myeat.R;
import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Truth_venture_activity extends Activity implements SensorEventListener,OnClickListener{
	TextView tv = null;  
    Button button = null;  
    SensorManager sensorManager = null;  
    Vibrator vibrator = null;
    SharkListener mSharkListenner = new SharkListener();
    SoundPool soundpool;
    int hitOkSfx;
	String[] question={"大冒险：抓着铁门大喊,放我出去",
    		"随便抓个人说，我怀了你的孩子","找个同班的异性说，我没穿内裤，你能借我么？",
    		"绕操场跑一圈，边跑边喊，我再也不尿床了",
    		"买一瓶饮料（要有中奖活动的那种）然后拿着瓶子上的宣传问老板在要一瓶，（当然前提是没中奖）老板可定说你没种，然后就认真的说，上面不是写了开盖有奖，再来一瓶吗？",
    		"做一个大家都满意的鬼脸",
    		"像一位异性表白3分钟",
    		"吃下每个人为你夹得菜（如果是辣椒……）",
    		"跳草裙舞、脱衣舞",
    		"男生抱起女生，保持5秒钟",
    		"正面对着十指 交扣，深情对视，深情朗诵骆宾王的《鹅》",
    		"用手纸当围巾，围脖子上持续一轮游戏",
    		"银行卡密码是多少",
    		"上完厕所洗手了吗",
    		"你觉得自己放的屁臭不臭"
  };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truth_venture_activity);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE); 
		soundpool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		hitOkSfx = soundpool.load(this,R.raw.shake,0);
		tv = (TextView) findViewById(R.id.question);
		//button =(Button) findViewById(R.id.shakebutton1);
		
        /*button.setOnClickListener(new View.OnClickListener()  
        {  
        	
            @Override  
            public void onClick(View v)  
            {  z
               Intent intent=new Intent(Truth_venture_activity.this,);
               startActivity(intent);
            }  
        });*/
		/*GifView gf1; 
		gf1=(GifView)findViewById(R.id.imagegif);
		gf1.setGifImage(R.drawable.shake_yes);
		gf1.setOnClickListener(this);
		gf1.setShowDimension(300, 300);
		gf1.setGifImageType(GifImageType.COVER);
		  */
	}
	
	protected void onPause()  
    {  
        super.onPause();  
        sensorManager.unregisterListener(this);  
    }  
  
    @Override  
    protected void onResume()  
    {  
        super.onResume();  
        sensorManager.registerListener(this,  
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
                SensorManager.SENSOR_DELAY_NORMAL);  
    }  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.truth_venture_activity, menu);
		return true;
	}
	public void onAccuracyChanged(Sensor sensor, int accuracy)  
    {  
        //当传感器精度改变时回调该方法，Do nothing.  
    }
	public void onSensorChanged(SensorEvent event)  
    {

		Random r=new Random();
		int n=r.nextInt(question.length);
        int sensorType = event.sensor.getType();  
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴  
        float[] values = event.values;  
        if (sensorType == Sensor.TYPE_ACCELEROMETER)  
        {  
            if ((Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math  
                    .abs(values[2]) > 14))  
            {  
                Log.d("sensor x ", "============ values[0] = " + values[0]);  
                Log.d("sensor y ", "============ values[1] = " + values[1]);  
                Log.d("sensor z ", "============ values[2] = " + values[2]);  
                tv.setText(question[n]);          
                //摇动手机后，再伴随震动提示~~  
                vibrator.vibrate(500);  
                
            }  
  
        }  
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}  

	/*public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
