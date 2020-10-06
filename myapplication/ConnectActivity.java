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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;  

public class ConnectActivity extends Activity {


	private String mip;
	private String mport;

	// UI references.
	private EditText metext_ip;
	private EditText metext_port;
	private Button mbtn_connect;

	private ProgressDialog _loading;

	private final BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			final String action = intent.getAction();

			if(Constants.INTENTFILTER_CONNECTION.equals(action))
			{
				Bundle bundle = intent.getExtras();    

				update_status();

				if(bundle != null)
				{
					if(bundle.getInt(Constants.INTENTEXTRA_CONNECT) == 1)
					{
						_loading.dismiss();
						finish();
					}
					if(bundle.getInt(Constants.INTENTEXTRA_CONNECT) ==0)
					{

						TCPClient.getInstance().stopsclient(); 
						_loading.dismiss();
					//	finish();
					}
				}
			}
		}
	};

	private void update_status()
	{
		if(TCPClient.getInstance().is_connected())
		{
			mbtn_connect.setText(R.string.ACTION_DISCONNECT);
		} 
		else {
			mbtn_connect.setText(R.string.STR_CONNECT);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_connect);

		metext_ip = (EditText) findViewById(R.id.ID_IP);
		metext_port = (EditText) findViewById(R.id.ID_PORT); 

		mbtn_connect = (Button) findViewById(R.id.BTN_CONNECT);

		mbtn_connect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!TCPClient.getInstance().is_connected())
					attempt_connect();
				else
					attempt_disconnect();

			}
		}); 

		_loading = new ProgressDialog(ConnectActivity.this);
		_loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_loading.setMessage("연결중입니다.");
		_loading.setCancelable(false);

		update_status();
	}

	@Override
	public synchronized void onStop() {
		super.onStop(); 
		unregisterReceiver(mBroadCastReceiver);
	} 

	@Override
	public synchronized void onResume() {
		super.onResume(); 
		registerReceiver(mBroadCastReceiver, makeIntentFilter());
	}

	private static IntentFilter makeIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter(); 
		intentFilter.addAction(Constants.INTENTFILTER_CONNECTION); 

		return intentFilter;
	}

	public void attempt_connect() { 
		mip = metext_ip.getText().toString();
		mport = metext_port.getText().toString();

		timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		_loading.show();

		TCPClient.getInstance().set_ip_address(mip);
		TCPClient.getInstance().set_port(Integer.parseInt(mport));
		TCPClient.getInstance().startclient();
	}

	public void attempt_disconnect() {  

		timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		_loading.show();

		TCPClient.getInstance().stopsclient(); 
	}

	public void timerDelayRemoveDialog(long time, final Dialog d){
		new Handler().postDelayed(new Runnable() {
			public void run() {                
				d.dismiss();   
				//     Toast.makeText(getApplicationContext(), "������ �ҷ��� �� �����ϴ�.", Toast.LENGTH_SHORT).show();
			}
		}, time); 
	}
}
