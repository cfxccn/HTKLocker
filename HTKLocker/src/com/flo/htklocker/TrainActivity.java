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
import android.widget.TextView;

public class TrainActivity extends Activity {
	UserService userService;
	AudioRecordFunc audioRecordFunc;
	Button button_Train;
	TextView textView_TrainInfo;
	String wavPath;
	String userid;
	String username;
	AlertDialog alertDialog;
	FileService fileService;

	private void bindView() {
		button_Train = (Button) findViewById(R.id.button_Train);
		textView_TrainInfo = (TextView) findViewById(R.id.textView_TrainInfo);
		textView_TrainInfo.setText(getResources().getString(R.string.user_name)
				+ ":" + username);
	}

	private void bindListener() {

		button_Train.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileService.getTrainWavPath();
				if (wavPath == null) {
					ToastUtil.show(TrainActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					button_Train.setText(R.string.start_record);
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							TrainActivity.this);
					View view1 = View.inflate(TrainActivity.this,
							R.layout.dialog_record, null);
					dialogBuilder1.setView(view1);
					alertDialog = dialogBuilder1.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();
					startRecord();
				}
			}
		});
	}

	protected void startRecord() {
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, userid
				+ "_1.wav", userid + "_1.raw");
		if (result == 1) {
			ToastUtil.show(getApplicationContext(),
					R.string.audio_error_unknown);
			alertDialog.cancel();
			return;
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				stopRecord();
				alertDialog.cancel();

			}
		}, 3000);
	}

	protected void stopRecord() {
		audioRecordFunc.stopRecordAndFile();
		ToastUtil.show(getApplicationContext(),
				R.string.start_handling);
		TrainTest.createMFCC(fileService,wavPath, userid);
		TrainTest.train(fileService, userid);
		userService.trainUser(Integer.valueOf(userid.substring(2)));
		ToastUtil.show(this, R.string.train_end);
		button_Train.setText(R.string.train);
		TrainTest.clear();

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
