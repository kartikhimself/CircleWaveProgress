package com.ksharkapps.circlewaveprogress;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


@SuppressLint("HandlerLeak")
public class ExampleActivity extends Activity {

	private CircleWaveProgressView mWaterWaveView;
    private Message message;
    private int progress=0;


	
	 private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				int p=msg.what;
				mWaterWaveView.setmWaterLevel((float)p/100);
			}
	    	
	    };
	    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Drawable colorDrawable = new ColorDrawable(Color.BLACK);
	
		LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable});
		
		ActionBar actionbar = getActionBar();
		actionbar.setBackgroundDrawable(ld);

		mWaterWaveView = (CircleWaveProgressView) findViewById(R.id.wave_view);
        mWaterWaveView.setmWaterLevel(0.0F);
		mWaterWaveView.startWave();
		
		
		new Thread(runnable).start();


	}
	
   Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			message=handler.obtainMessage();
			// TODO Auto-generated method stub
			try {
				for (int i = 1; i <= 100; i++) {
					int x=progress++;
					message.what=x;
					handler.sendEmptyMessage(message.what);
			        mWaterWaveView.setWaveProgess(String.valueOf(progress)+"%");
			        if(progress==100){
			        mWaterWaveView.setWaveUpdate("Completed");

			        }else{
				    mWaterWaveView.setWaveUpdate("Loading...");
			        }
    				Thread.sleep(75);
				}
				//mWaterWaveView.stopWave();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		 
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mWaterWaveView.stopWave();
		mWaterWaveView=null;
		super.onDestroy();
	}
}
