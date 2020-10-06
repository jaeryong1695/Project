package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter; 
import android.os.Bundle;  
import android.os.Handler;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup; 
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener; 

public class SettingActivity extends Activity {


	private Switch Switch_TOY_dcmotor, Switch_TOY_rgbled, Switch_TOY_light;

	private boolean _reading = false;
	private ProgressDialog _loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);


		Switch_TOY_dcmotor = (Switch) findViewById(R.id.ID_TOY_DCMOTOR);
		Switch_TOY_rgbled = (Switch) findViewById(R.id.ID_TOY_RGBLED);
		Switch_TOY_light = (Switch) findViewById(R.id.ID_TOY_LEDBAR);
		_loading = new ProgressDialog(SettingActivity.this);
		_loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_loading.setMessage("정보 불러오는 중.");
		//	timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		_loading.setCancelable(false);


		Switch_TOY_dcmotor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				set_device_status((IoTUtility.mkcommand_set_dcmotor(isChecked)));
			} 
		});

		Switch_TOY_rgbled.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				set_device_status((IoTUtility.mkcommand_set_rgbled(isChecked)));
			} 
		});

		Switch_TOY_light.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				set_device_status((IoTUtility.mkcommand_set_light(isChecked)));
			}
		});
	}
	@Override
	public synchronized void onPause() {
		super.onPause(); 
		unregisterReceiver(mBroadCastReceiver);
	}

	@Override
	public synchronized void onResume() {
		super.onResume(); 
		registerReceiver(mBroadCastReceiver, makeIntentFilter());
	}

	private void set_device_status(byte[] _data)
	{
		if(!_reading){
			timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
			_loading.show();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TCPClient.getInstance().sendMessage(_data);
		}
	}
 	private void set_info_to_server()
	{
		timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		_loading.show();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void get_info_from_server()
	{ 
		timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		_reading = true;
		_loading.show();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TCPClient.getInstance().sendMessage(IoTUtility.mkcommand_get_setting()); 
	}

	private static IntentFilter makeIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();  
		intentFilter.addAction(Constants.INTENTFILTER_DATA); 
		return intentFilter;
	}

	public void timerDelayRemoveDialog(long time, final Dialog d){
		new Handler().postDelayed(new Runnable() {
			public void run() {
				//Toast.makeText(getApplicationContext(), "������ �ҷ��� �� �����ϴ�.", Toast.LENGTH_SHORT).show();       
				d.dismiss();   
			}
		}, time); 
	}


	private final BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			if(Constants.INTENTFILTER_DATA.equals(action))
			{
				Bundle bundle = intent.getExtras();    

				if(bundle != null) 
				{  
					if(IoTUtility.is_get_setting(bundle.getString(Constants.INTENTEXTRA_RECEIVED_DATA)))
					{
						String[] _data = IoTUtility.get_setting(bundle.getString(Constants.INTENTEXTRA_RECEIVED_DATA));
						if(_data != null)
						{
							if(_data.length < 3)
							{

							}
							else 
							{
								Switch_TOY_dcmotor.setChecked(Integer.parseInt(_data[0]) == 1);
								Switch_TOY_rgbled.setChecked(Integer.parseInt(_data[1]) == 1);
								Switch_TOY_light.setChecked(Integer.parseInt(_data[2]) ==1 );
								_loading.dismiss();
								_reading = false;
							}
						}
					}

					else if(IoTUtility.is_set_setting(bundle.getString(Constants.INTENTEXTRA_RECEIVED_DATA)))
					{
						Toast.makeText(getApplicationContext(), "설정 변경 완료.",Toast.LENGTH_SHORT).show();
						_loading.dismiss();
					}
				}

			}
		}
	};
}
