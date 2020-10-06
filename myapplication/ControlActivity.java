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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener; 

public class ControlActivity extends Activity {

	private Button mbtn_up;
	private Button mbtn_down;
	private Button mbtn_left;
	private Button mbtn_right;
	private Button mbtn_stop;

	//private boolean _reading = false;
	private ProgressDialog _loading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feeding);

		mbtn_up = (Button) findViewById(R.id.BTN_TOY_UP);
		mbtn_down = (Button) findViewById(R.id.BTN_TOY_DOWN);
		mbtn_left = (Button) findViewById(R.id.BTN_TOY_LEFT);
		mbtn_right = (Button) findViewById(R.id.BTN_TOY_RIGHT);
		mbtn_stop = (Button) findViewById(R.id.BTN_TOY_STOP);

		//	_loading = new ProgressDialog(ControlActivity.this);
		//	_loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//	_loading.setMessage("������ �ҷ����� ���Դϴ�.");
		//	timerDelayRemoveDialog(Constants.DELAY_TIME, _loading);
		//	_loading.setCancelable(false);

	}
}