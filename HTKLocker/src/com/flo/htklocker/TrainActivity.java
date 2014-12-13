package com.flo.htklocker;

import com.flo.service.UserService;
import com.flo.util.AudioRecordFunc;
import com.flo.util.FileHelper;
import com.flo.util.HCopyFunc;
import com.flo.util.HInitFunc;
import com.flo.util.HRest2Func;
import com.flo.util.HRestFunc;
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

public class TrainActivity extends Activity {
	UserService userService;
	AudioRecordFunc audioRecordFunc;
	Button button_Train;
	TextView textView_TrainInfo;
	String wavPath;
	String userid;
	String username;
	AlertDialog alertDialog;
	FileHelper fileHelper;

	private void bindControl() {
		button_Train = (Button) findViewById(R.id.button_Train);
		textView_TrainInfo = (TextView) findViewById(R.id.textView_TrainInfo);
		textView_TrainInfo.setText(getResources().getString(R.string.user_name)
				+ ":" + username);
	}

	private void controlBindListener() {

		button_Train.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileHelper.getTrainWavPath();
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
		createMFCCnTrain();

		userService.trainUser(Integer.valueOf(userid.substring(2)));
		ToastUtil.show(this, R.string.train_end);
		button_Train.setText(R.string.train);

	}

	private void createMFCCnTrain() {
		String labUserPath = fileHelper.createLab(userid);
		String wavlist = fileHelper.createWavList(wavPath, userid);
		String protoFile = fileHelper.createProto(userid);
		HCopyFunc.exec(fileHelper.getConfigFilePath(), wavlist);
		fileHelper.copyMfcc(userid);
		String trainlist = fileHelper.createTrainList(userid);
		HInitFunc.exec(trainlist,fileHelper.getHmm0Path(),
				 protoFile,  userid,  labUserPath);
		HRestFunc.exec(trainlist,fileHelper.getHmm1Path(),
				fileHelper.getHmm0Path()+"/hmm_"+userid,  userid,  labUserPath);
		HRest2Func.exec(trainlist,fileHelper.getHmm2Path(),
				fileHelper.getHmm1Path()+"/hmm_"+userid,  userid,  labUserPath);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		userService = new UserService(getApplicationContext());
		fileHelper = new FileHelper(getApplicationContext());
		username = getIntent().getStringExtra("USERNAME");
		userid = getIntent().getStringExtra("USERID");
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
