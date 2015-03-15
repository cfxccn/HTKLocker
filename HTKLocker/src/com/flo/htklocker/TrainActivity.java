package com.flo.htklocker;

import com.flo.service.FileService;
import com.flo.service.UserService;
import com.flo.util.AudioRecordFunc;
import com.flo.util.ToastUtil;
import com.flo.util.TrainTest;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrainActivity extends Activity {
	UserService userService;
	AudioRecordFunc audioRecordFunc;
	Button button_Record1;
	Button button_Record2;
	Button button_Record3;

	String wavPath;
	String userid;
	String username;
	AlertDialog alertDialog;
	FileService fileService;

	private void bindView() {
		button_Record1 = (Button) findViewById(R.id.button_Record1);
		button_Record2 = (Button) findViewById(R.id.button_Record2);
		button_Record2.setEnabled(false);
		button_Record3 = (Button) findViewById(R.id.button_Record3);
		button_Record3.setEnabled(false);


	
	}

	private void bindListener() {

		button_Record1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileService.getTrainWavPath();
				if (wavPath == null) {
					ToastUtil.show(TrainActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							TrainActivity.this);
					View view1 = View.inflate(TrainActivity.this,
							R.layout.dialog_record, null);
					dialogBuilder1.setView(view1);
					alertDialog = dialogBuilder1.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();
					startRecord(1);
				}
			}
		});
		button_Record2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileService.getTrainWavPath();
				if (wavPath == null) {
					ToastUtil.show(TrainActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							TrainActivity.this);
					View view1 = View.inflate(TrainActivity.this,
							R.layout.dialog_record, null);
					dialogBuilder1.setView(view1);
					alertDialog = dialogBuilder1.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();
					startRecord(2);
				}
			}
		});
		button_Record3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileService.getTrainWavPath();
				if (wavPath == null) {
					ToastUtil.show(TrainActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							TrainActivity.this);
					View view1 = View.inflate(TrainActivity.this,
							R.layout.dialog_record, null);
					dialogBuilder1.setView(view1);
					alertDialog = dialogBuilder1.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();
					startRecord(3);
				}
			}
		});
	}

	protected void startRecord(final int n) {
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, userid + "_"
				+ n + ".wav", userid + "_" + n + ".raw");
		if (result == 1) {
			ToastUtil.show(getApplicationContext(),
					R.string.audio_error_unknown);
			alertDialog.cancel();
			return;
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				stopRecord(n);
				alertDialog.cancel();

			}
		}, 3000);
	}

	protected void stopRecord(int n) {
		audioRecordFunc.stopRecordAndFile();
		switch (n) {
		case 1:
			button_Record1.setEnabled(false);
			button_Record2.setEnabled(true);
			button_Record3.setEnabled(false);
			ToastUtil.show(getApplicationContext(), R.string.record_end);

			break;
		case 2:
			button_Record1.setEnabled(false);
			button_Record2.setEnabled(false);
			button_Record3.setEnabled(true);
			ToastUtil.show(getApplicationContext(), R.string.record_end);

			break;
		case 3:
			button_Record1.setEnabled(false);
			button_Record2.setEnabled(false);
			button_Record3.setEnabled(false);
			
			TrainTest.createMFCC(fileService, wavPath, userid,true);
			TrainTest.train(fileService, userid);
			userService.trainUser(Integer.valueOf(userid.substring(2)));
			ToastUtil.show(this, R.string.train_end);
			
			break;
		default:
			break;
		}
		
		
	
		// TrainTest.clear();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		username = getIntent().getStringExtra("USERNAME");
		userid = getIntent().getStringExtra("USERID");
		userService = new UserService(getApplicationContext());
		fileService = new FileService(getApplicationContext());

		bindView();
		bindListener();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
