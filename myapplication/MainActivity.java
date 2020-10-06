package com.example.myapplication;
 

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler; 
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener; 
import android.widget.ImageView; 


public class MainActivity extends Activity{

	private enum MENU_TYPE {STATUS, SETTING};

  
	private ImageView mbtn_connect;
	private ImageView mbtn_status;
	private ImageView mbtn_camera;

	 
	private Handler mHandler;

	private final BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			if(Constants.INTENTFILTER_DATA.equals(action))
			{
			//	Bundle bundle = intent.getExtras();     
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mbtn_connect = (ImageView) findViewById(R.id.BTN_POPUP_CONNECT);
		mbtn_status = (ImageView) findViewById(R.id.BTN_POPUP_STATUS);
		mbtn_camera = (ImageView) findViewById(R.id.BTN_POPUP_CAMERA);


		mHandler = new Handler(); 

		TCPClient.getInstance().setContext(this.getApplicationContext());

		mbtn_connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				get_connect_intent();
			}
		}); 

		mbtn_status.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open_popup(MENU_TYPE.STATUS);
			}
		}); 

		mbtn_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open_popup(MENU_TYPE.SETTING);
			}
		});

	}

	@Override
	public synchronized void onResume() {
		super.onResume(); 
		registerReceiver(mBroadCastReceiver, makeIntentFilter());
	}

	private static IntentFilter makeIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter(); 
		intentFilter.addAction(Constants.INTENTFILTER_CONNECTION);
		intentFilter.addAction(Constants.INTENTFILTER_DATA); 
		return intentFilter;
	}
 
	private void get_connect_intent()
	{
		Intent intent = new Intent(this, ConnectActivity.class); 
		startActivityForResult(intent, 0);
	}

	private void open_popup(MENU_TYPE _type)
	{
		Intent intent;

		if(!TCPClient.getInstance().is_connected())
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
			alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();     
					get_connect_intent();
				}
			});
			alert.setMessage("디바이스 연결을 하십시오.");
			alert.show();
			return;
		}

		switch(_type)
		{
		case STATUS : 
			intent = new Intent(this, StatusActivity.class); 
			startActivityForResult(intent, 0);
			break;
		case SETTING : 
			intent = new Intent(this, SettingActivity.class); 
			startActivityForResult(intent, 0);
			break; 
		}
	}

} 
