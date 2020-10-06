package com.example.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter; 
import android.os.Bundle; 
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusActivity extends Activity {

	private TextView mimage_human;
	private TextView mimage_ultra;
	private ImageView mimage_dcmotor_functionality;
	private ImageView mimage_rgbled_functionality; 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_status);

		mimage_human = (TextView)findViewById(R.id.ID_STATUS_HUMAN);
		mimage_ultra = (TextView)findViewById(R.id.ID_STATUS_ULTRA);
		mimage_dcmotor_functionality = (ImageView)findViewById(R.id.ID_STATUS_DCMOTOR);
		mimage_rgbled_functionality = (ImageView)findViewById(R.id.ID_STATUS_RGBLED); 

		get_info_from_server();
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

	private void get_info_from_server()
	{ 
		TCPClient.getInstance().sendMessage(IoTUtility.mkcommand_get_status()); 
	}

	private static IntentFilter makeIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();  
		intentFilter.addAction(Constants.INTENTFILTER_DATA); 
		return intentFilter;
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
					String[] _data = IoTUtility.get_status(bundle.getString(Constants.INTENTEXTRA_RECEIVED_DATA));
					if(_data != null)
					{
						if(Integer.parseInt(_data[0]) == 0)
							mimage_human.setText("감지");
						else
							mimage_human.setText("미감지");
						if(Integer.parseInt(_data[1]) == 0)
							mimage_ultra.setText("감지");
						else
							mimage_ultra.setText("미감지");
						if(Integer.parseInt(_data[0]) == 1)
							mimage_dcmotor_functionality.setImageResource(R.drawable.status_normal);
						else
							mimage_dcmotor_functionality.setImageResource(R.drawable.status_unnormal);  
						if(Integer.parseInt(_data[0]) == 1)
							mimage_rgbled_functionality.setImageResource(R.drawable.status_normal);
						else
							mimage_rgbled_functionality.setImageResource(R.drawable.status_unnormal); 
					}
				}

			}

		}
	};
}
