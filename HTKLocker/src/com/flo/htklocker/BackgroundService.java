package com.flo.htklocker;

import com.flo.util.ToastUtil;
import com.flo.htklocker.AuthActivity;
import com.flo.htklocker.R;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BackgroundService extends Service{
	private BroadcastReceiver receiverOff;
	private IntentFilter filterOff;
	public enum Status {
		STOP, RUNNING
	}
	private static Status status = Status.STOP;
	public static Status getStatus() {
		return status;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		ToastUtil.show(this, R.string.service_start);
		super.onCreate();
		registerIntentReceivers();
		startForeground(1, new Notification());
		status = Status.RUNNING;
	}
	@Override
	public void onDestroy(){
		ToastUtil.show(this, R.string.service_stop);
		status = Status.STOP;
		super.onDestroy();
	}
	private void registerIntentReceivers() {
		filterOff = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		receiverOff = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				Intent startMain = new Intent(context, AuthActivity.class);
				startMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
			}
		};
		registerReceiver(receiverOff, filterOff);
	}
}
