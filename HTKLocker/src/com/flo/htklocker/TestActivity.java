package com.flo.htklocker;

import com.flo.util.AudioRecordFunc;
import com.flo.util.FileHelper;
import com.flo.util.HCopyFunc;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity {
	FileHelper fileHelper;
	AudioRecordFunc audioRecordFunc;
	String wavPath;
	String wavlist;
	String wavString = "test_1.wav";
	String rawString = "test_1.raw";
	AlertDialog alertDialog;
	Button button_Test;
	TextView textView_TestInfo;

	protected void startRecord() {
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, wavString,
				rawString);
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
		createMFCCnTest();
		ToastUtil.show(getApplicationContext(), R.string.test_end);
		button_Test.setText(R.string.test);
	}

	private void createMFCCnTest() {
		wavlist=fileHelper.createWavList(wavPath,"test");
		HCopyFunc.exec(fileHelper.getConfigFilePath(), wavlist);
	}

	private void bindControl() {
		button_Test = (Button) findViewById(R.id.button_Test);
		textView_TestInfo = (TextView) findViewById(R.id.textView_TestInfo);
	}

	private void controlBindListener() {
		button_Test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileHelper.getTestWavPath();
				if (wavPath==null) {
					ToastUtil.show(TestActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					button_Test.setText(R.string.start_record);
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							TestActivity.this);
					View view1 = View.inflate(TestActivity.this,
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		fileHelper=new FileHelper(getApplicationContext());
		bindControl();
		controlBindListener();
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
