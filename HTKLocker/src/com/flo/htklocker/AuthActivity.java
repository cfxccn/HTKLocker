package com.flo.htklocker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flo.accessobject.FileAccessObject;
import com.flo.accessobject.KVAccessObject;
import com.flo.accessobject.UserAccessObject;
import com.flo.htklocker.R;
import com.flo.model.User;
import com.flo.util.AudioRecordFunc;
import com.flo.util.NativeHTK;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AuthActivity extends Activity {
	AudioRecordFunc audioRecordFunc;

	KVAccessObject kVAccessObject;
	boolean isSoundMode;
	RelativeLayout relativeLayout_MainPanel;
	TextView textView_Time;
	TextView textView_Date;
	TextView textView_Info;
	User user;
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
	ImageButton imageButton_UnLock;
	GridLayout gridLayout_NumberPanel;
	String inputPassword;
	String oldPassword;
	String wavPath;
	Spinner spinner_User;
	List<Map<String, Object>> userMapList;
	// SimpleAdapter adapter;
	// AuthListViewAdapter adapter;
	DecimalFormat decimalFormat;
	AlertDialog alertDialog;
	UserAccessObject userAccessObject;

	FileAccessObject fileAccessObject;

	static String[] weekDaysName;

	private List<Map<String, Object>> list2Map(List<User> userList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (userList == null) {
			return result;
		}
		for (User u : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (u.getIsTrained()) {
				map.put("textView_UserName", u.getName());
				map.put("userId", u.getNameId());
				map.put("question", u.getQuestion());
			} else {
			}
			result.add(map);
		}
		return result;
	}

	private void bindView() {
		relativeLayout_MainPanel = (RelativeLayout) findViewById(R.id.relativeLayout_MainPanel);
		textView_Time = (TextView) findViewById(R.id.textView_Time);
		textView_Date = (TextView) findViewById(R.id.textView_Date);
		textView_Info = (TextView) findViewById(R.id.textView_Info);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		spinner_User = (Spinner) findViewById(R.id.spinner_User);
		imageButton_UnLock = (ImageButton) findViewById(R.id.imageButton_UnLock);
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
		relativeLayout_MainPanel.setBackground(getWallpaper());
		editText_Password.setCursorVisible(false);
		editText_Password.setFocusable(false);
		editText_Password.setFocusableInTouchMode(false);
		progressBar.setVisibility(View.INVISIBLE);

		userMapList = list2Map(userAccessObject.getTrainedUserList());
		if (userMapList.size() < 3) {
			isSoundMode = false;
		}
		if (isSoundMode) {
			editText_Password.setVisibility(View.INVISIBLE);
			gridLayout_NumberPanel.setVisibility(View.INVISIBLE);
			spinner_User.setVisibility(View.VISIBLE);
			imageButton_UnLock.setVisibility(View.VISIBLE);

			button_ChangeMode.setText(R.string.switch_numerical_password);

		} else {
			spinner_User.setVisibility(View.INVISIBLE);
			imageButton_UnLock.setVisibility(View.INVISIBLE);
			editText_Password.setVisibility(View.VISIBLE);
			gridLayout_NumberPanel.setVisibility(View.VISIBLE);
			button_ChangeMode.setText(R.string.switch_sound_password);

		}

		// adapter = new AuthListViewAdapter(this, userMapList,
		// R.layout.item_auth, new String[] { "textView_UserName",
		// "imageButton_UnLock", "userId", "question" },
		// new int[] { R.id.textView_UserName, R.id.imageButton_UnLock });
		// listView_User.setAdapter(adapter);
		List<String> nameStrings = new ArrayList<String>(10);
		for (Map<String, Object> i : userMapList) {
			nameStrings.add((String) i.get("textView_UserName"));
		}
		String[] strings = nameStrings.toArray(new String[nameStrings.size()]);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, strings);
		spinner_User.setAdapter(adapter);


		imageButton_UnLock.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.performClick();
					wavPath = fileAccessObject.getTestWavPath();
					final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
							AuthActivity.this);
					View view1 = View.inflate(AuthActivity.this,
							R.layout.dialog_unlock, null);
					TextView textView2 = (TextView) view1
							.findViewById(R.id.textView2);
					StringBuilder questionString = new StringBuilder(view1
							.getResources().getString(R.string.unlock_tips));
					user = userAccessObject.getUserQuestion(spinner_User
							.getSelectedItem().toString());
					questionString.append(user.getQuestion());
					textView2.setText(questionString);
					dialogBuilder1.setView(view1);
					alertDialog = dialogBuilder1.create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.setCancelable(false);
					alertDialog.show();

					startRecord(user.getNameId());

					return true;
				case MotionEvent.ACTION_UP:

					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stopRecord(user.getNameId());

					return false;
				default:
					return false;
				}
			}
		});

	}

	protected void startRecord(final String userId) {
		String wavString = userId + ".wav";
		String rawString = userId + ".raw";
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, wavString,
				rawString);
		if (result == 1) {
			ToastUtil.show(getApplicationContext(),
					R.string.audio_error_unknown);
			return;
		}

	}

	protected void stopRecord(String userId) {
		alertDialog.cancel();
		audioRecordFunc.stopRecordAndFile();
		NativeHTK.createMFCC(fileAccessObject, wavPath, userId, false);
		try {
			NativeHTK.test(fileAccessObject, userAccessObject, userId);
		} catch (Exception e) {
			ToastUtil.show(getApplicationContext(), R.string.error);
		}
		verify(userId);
	}

	protected void verify(String userId) {
		boolean result = userId.equalsIgnoreCase(fileAccessObject
				.parseRecoMlf());
		if (result) {
			int id = Integer.parseInt(userId.substring(2));
			userAccessObject.verifyUser(id);
			ToastUtil.show(getApplicationContext(), R.string.unlock_success);
			unLock();
			finish();
		} else {
			ToastUtil.show(getApplicationContext(), R.string.unlock_failure);
		}
	}

	class NumberButtonClickListener implements OnClickListener {
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
			if (kVAccessObject.validateUser(editText_Password.getText()
					.toString())) {
				unLock();
			}
		}
	}

	private void bindListener() {
		NumberButtonClickListener numberButtonClickListener = new NumberButtonClickListener();
		button1.setOnClickListener(numberButtonClickListener);
		button2.setOnClickListener(numberButtonClickListener);
		button3.setOnClickListener(numberButtonClickListener);
		button4.setOnClickListener(numberButtonClickListener);
		button5.setOnClickListener(numberButtonClickListener);
		button6.setOnClickListener(numberButtonClickListener);
		button7.setOnClickListener(numberButtonClickListener);
		button8.setOnClickListener(numberButtonClickListener);
		button9.setOnClickListener(numberButtonClickListener);
		button0.setOnClickListener(numberButtonClickListener);
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
				if (userMapList.size() < 3) {
					isSoundMode = false;

					ToastUtil.show(getApplicationContext(), getResources()
							.getString(R.string.trained_user_not_enough));
				} else {
					changeMode();
				}
			}
		});
	}

	protected void changeMode() {
		if (isSoundMode) {
			isSoundMode = false;
			spinner_User.setVisibility(View.INVISIBLE);
			imageButton_UnLock.setVisibility(View.INVISIBLE);
			gridLayout_NumberPanel.setVisibility(View.VISIBLE);
			editText_Password.setVisibility(View.VISIBLE);
			button_ChangeMode.setText(R.string.switch_sound_password);
		} else {
			isSoundMode = true;
			gridLayout_NumberPanel.setVisibility(View.INVISIBLE);
			spinner_User.setVisibility(View.VISIBLE);
			imageButton_UnLock.setVisibility(View.VISIBLE);
			editText_Password.setVisibility(View.INVISIBLE);
			button_ChangeMode.setText(R.string.switch_numerical_password);
		}
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
						inputPassword.length() - 1);
				editText_Password.setText(inputPassword);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		kVAccessObject = KVAccessObject.getInstance(getApplicationContext());
		weekDaysName = getResources().getStringArray(R.array.weekDays);
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
		win.setAttributes(winParams);
		win.setFlags(0x80000000, 0x80000000);
		// userService = new UserService(getApplicationContext());
		userAccessObject = UserAccessObject
				.getInstance(getApplicationContext());
		fileAccessObject = FileAccessObject
				.getInstance(getApplicationContext());
		decimalFormat = new DecimalFormat("00");
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindView();
		bindListener();
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
