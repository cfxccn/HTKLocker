package com.flo.htklocker;

import java.text.DecimalFormat;
import java.util.Calendar;

import com.flo.htklocker.R;
import com.flo.service.LoginService;
import com.flo.util.AudioRecordFunc;
import com.flo.util.FileHelper;
import com.flo.util.HCopyFunc;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AuthActivity extends Activity {
	LoginService loginService;
	boolean isSoundMode = true;
	RelativeLayout relativeLayout_MainPanel;
	TextView textView_Time;
	TextView textView_Date;
	TextView textView_Info;

	EditText editText_Password;
	ProgressBar progressBar;
	Button button_ChangeMode;
	Button button_Reset;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	Button button7;
	Button button8;
	Button button9;
	Button button0;

	GridLayout gridLayout_NumberPanel;
	String inputPassword;
	String oldPassword;
	ImageButton authButton;
	DecimalFormat decimalFormat;
	AlertDialog alertDialog;
	String wavPath;
	String wavString = "test_1.wav";
	String rawString = "test_1.raw";
	String wavlist;
	AudioRecordFunc audioRecordFunc;
	FileHelper fileHelper;

	static String[] weekDaysName = { "SUN", "MON", "TUE", "WED", "THU", "FRI",
			"ÐÇÆÚÁù" };

	private void bindControl() {
		relativeLayout_MainPanel = (RelativeLayout) findViewById(R.id.relativeLayout_MainPanel);
		textView_Time = (TextView) findViewById(R.id.textView_Time);
		textView_Date = (TextView) findViewById(R.id.textView_Date);
		textView_Info = (TextView) findViewById(R.id.textView_Info);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		authButton = (ImageButton) findViewById(R.id.authButton);
		button_Reset = (Button) findViewById(R.id.button_Reset);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		button0 = (Button) findViewById(R.id.button0);
		gridLayout_NumberPanel = (GridLayout) findViewById(R.id.gridLayout_NumberPanel);
		button_ChangeMode = (Button) findViewById(R.id.button_ChangeMode);
		editText_Password = (EditText) findViewById(R.id.editText_Password);
		editText_Password.setVisibility(View.INVISIBLE);
		editText_Password.setCursorVisible(false);
		editText_Password.setFocusable(false);
		editText_Password.setFocusableInTouchMode(false);
		gridLayout_NumberPanel.setVisibility(View.INVISIBLE);

		progressBar.setVisibility(View.INVISIBLE);
		relativeLayout_MainPanel.setBackground(getWallpaper());
	}

	class numberButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.button0:
				inputPassword = inputPassword + "0";
				break;
			case R.id.button1:
				inputPassword = inputPassword + "1";
				break;
			case R.id.button2:
				inputPassword = inputPassword + "2";
				break;
			case R.id.button3:
				inputPassword = inputPassword + "3";
				break;
			case R.id.button4:
				inputPassword = inputPassword + "4";
				break;
			case R.id.button5:
				inputPassword = inputPassword + "5";
				break;
			case R.id.button6:
				inputPassword = inputPassword + "6";
				break;
			case R.id.button7:
				inputPassword = inputPassword + "7";
				break;
			case R.id.button8:
				inputPassword = inputPassword + "8";
				break;
			case R.id.button9:
				inputPassword = inputPassword + "9";
				break;
			default:
				break;
			}
			editText_Password.setText(inputPassword);
			if (loginService.validateUser(editText_Password
					.getText().toString())) {
				unLock();
			}
		}
	}

	private void controlBindListener() {
		button1.setOnClickListener(new numberButtonClickListener());
		button2.setOnClickListener(new numberButtonClickListener());
		button3.setOnClickListener(new numberButtonClickListener());
		button4.setOnClickListener(new numberButtonClickListener());
		button5.setOnClickListener(new numberButtonClickListener());
		button6.setOnClickListener(new numberButtonClickListener());
		button7.setOnClickListener(new numberButtonClickListener());
		button8.setOnClickListener(new numberButtonClickListener());
		button9.setOnClickListener(new numberButtonClickListener());
		button0.setOnClickListener(new numberButtonClickListener());
		button_Reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetPassword(false);
			}
		});
		button_Reset.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				resetPassword(true);
				return false;
			}
		});

		button_ChangeMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isSoundMode) {
					isSoundMode = false;
					authButton.setVisibility(View.INVISIBLE);
					gridLayout_NumberPanel.setVisibility(View.VISIBLE);
					editText_Password.setVisibility(View.VISIBLE);
					button_ChangeMode.setText(R.string.change_sound_password);
				} else {
					isSoundMode = true;
					gridLayout_NumberPanel.setVisibility(View.INVISIBLE);
					authButton.setVisibility(View.VISIBLE);
					editText_Password.setVisibility(View.INVISIBLE);
					button_ChangeMode
							.setText(R.string.change_numerical_password);
				}
			}
		});
	}

	public void unLock() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);		
	}

	protected void resetPassword(boolean isAll) {
		if (isAll) {
			inputPassword = "";
			editText_Password.setText(inputPassword);
		} else {
			if (inputPassword.length() > 0) {
				inputPassword = inputPassword.substring(0,
						inputPassword.length() -1);
				editText_Password.setText(inputPassword);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		fileHelper=new FileHelper(getApplicationContext());
		loginService=new LoginService(getApplicationContext());
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
		win.setAttributes(winParams);
		win.setFlags(0x80000000, 0x80000000);
		bindControl();
		controlBindListener();
		decimalFormat = new DecimalFormat("00");
	}

	@Override
	protected void onResume() {
		super.onResume();
		resetPassword(true);
		Calendar c = Calendar.getInstance();
		int weekIndex = c.get(Calendar.DAY_OF_WEEK) - 1;
		textView_Time.setText(""
				+ decimalFormat.format(c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ decimalFormat.format(c.get(Calendar.MINUTE)));
		textView_Date.setText("" + decimalFormat.format(c.get(Calendar.YEAR))
				+ "/" + decimalFormat.format(c.get(Calendar.MONTH) + 1) + "/"
				+ decimalFormat.format(c.get(Calendar.DAY_OF_MONTH)) + " "
				+ weekDaysName[weekIndex]);
		authButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wavPath = fileHelper.getTestWavPath();
				if (wavPath==null) {
					ToastUtil.ShowResString(AuthActivity.this,
							R.string.audio_error_no_sdcard);
				} else {
					textView_Info.setText(R.string.start_record);
					progressBar.setVisibility(View.VISIBLE);
					authButton.setVisibility(View.INVISIBLE);
					button_ChangeMode.setVisibility(View.INVISIBLE);

					startRecord();
				}
			}
		});
	}

	protected void startRecord() {
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, wavString,
				rawString);
		if (result == 1) {
			ToastUtil.ShowResString(getApplicationContext(),
					R.string.audio_error_unknown);
			return;
		}
		new Handler().postDelayed(new Runnable() {
			public void run() {
				stopRecord();
			}
		}, 3000);
	}

	protected void stopRecord() {
		audioRecordFunc.stopRecordAndFile();
		createMFCCnTest();
		textView_Info.setText("");
		authButton.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		button_ChangeMode.setVisibility(View.VISIBLE);

	}
	private void createMFCCnTest() {
		wavlist=fileHelper.createWavList(wavPath,"test");
		HCopyFunc.exec(fileHelper.getConfigFilePath(), wavlist);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
