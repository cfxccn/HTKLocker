package com.flo.htklocker;

import java.util.ArrayList;
import java.util.List;

import com.flo.accessobject.FileAccessObject;
import com.flo.accessobject.UserAccessObject;
import com.flo.util.AudioRecordFunc;
import com.flo.util.NativeHTK;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class TrainActivity extends Activity {
	UserAccessObject userAccessObject;
	AudioRecordFunc audioRecordFunc;
	Button button_Record1;
	Button button_Record2;
	Button button_Record3;
	RadioButton radioButton_DIY;
	RadioButton radioButton_System;
	RadioGroup radioGroup_QuestionType;
	EditText editText_Question;
	Spinner spinner_Question;

	String wavPath;
	String userid;
	String username;
	AlertDialog alertDialog;
	FileAccessObject fileAccessObject;
	String question;
	Long startTime, endTime;
	List<Long> timeList = new ArrayList<Long>(3);

	private void bindView() {
		button_Record1 = (Button) findViewById(R.id.button_Record1);
		button_Record2 = (Button) findViewById(R.id.button_Record2);
		button_Record2.setEnabled(false);
		button_Record3 = (Button) findViewById(R.id.button_Record3);
		button_Record3.setEnabled(false);
		radioButton_DIY = (RadioButton) findViewById(R.id.radioButton_DIY);
		radioButton_System = (RadioButton) findViewById(R.id.radioButton_System);
		radioGroup_QuestionType = (RadioGroup) findViewById(R.id.radioGroup_QuestionType);
		editText_Question = (EditText) findViewById(R.id.editText_Question);
		editText_Question.setVisibility(View.GONE);
		spinner_Question = (Spinner) findViewById(R.id.spinner_Question);

		String[] questions = getResources().getStringArray(R.array.questions);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, questions);
		spinner_Question.setAdapter(adapter);
	}

	class RadioCheckListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.radioButton_DIY:
				if (isChecked == true) {
					editText_Question.setVisibility(View.VISIBLE);
					spinner_Question.setVisibility(View.GONE);
				} else {
					editText_Question.setVisibility(View.GONE);
					spinner_Question.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.radioButton_System:
				if (isChecked == true) {

					editText_Question.setVisibility(View.GONE);
					spinner_Question.setVisibility(View.VISIBLE);
				} else {
					editText_Question.setVisibility(View.VISIBLE);
					spinner_Question.setVisibility(View.GONE);
				}

				break;
			default:
				break;
			}

		}
	}

	private void bindListener() {
		RadioCheckListener radioCheckListener = new RadioCheckListener();
		button_Record1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.performClick();

					wavPath = fileAccessObject.getTrainWavPath();
					if (wavPath == null) {
						ToastUtil.show(TrainActivity.this,
								R.string.audio_error_no_sdcard);
					} else {
						final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
								TrainActivity.this);
						if (radioButton_DIY.isChecked() == true) {
							question = editText_Question.getText().toString()
									.trim();
							if (question.equals("")) {
							}
						} else {
							question = spinner_Question.getSelectedItem()
									.toString();
						}

						View view1 = View.inflate(TrainActivity.this,
								R.layout.dialog_record, null);
						dialogBuilder1.setView(view1);
						alertDialog = dialogBuilder1.create();
						alertDialog.setCanceledOnTouchOutside(false);
						alertDialog.setCancelable(false);
						alertDialog.show();
						editText_Question.setEnabled(false);
						radioButton_DIY.setEnabled(false);
						radioButton_System.setEnabled(false);
						spinner_Question.setEnabled(false);
						startRecord(1);
					}
					break;
				case MotionEvent.ACTION_UP:
					stopRecord(1);

					break;
				default:
					break;
				}
				return false;
			}
		});

		// button_Record1.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// wavPath = fileAccessObject.getTrainWavPath();
		// if (wavPath == null) {
		// ToastUtil.show(TrainActivity.this,
		// R.string.audio_error_no_sdcard);
		// } else {
		// final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
		// TrainActivity.this);
		// if (radioButton_DIY.isChecked() == true) {
		// question = editText_Question.getText().toString().trim();
		// if(question.equals("")){
		// return ;
		// }
		// } else {
		// question = spinner_Question.getSelectedItem()
		// .toString();
		// }
		//
		// View view1 = View.inflate(TrainActivity.this,
		// R.layout.dialog_record, null);
		// dialogBuilder1.setView(view1);
		// alertDialog = dialogBuilder1.create();
		// alertDialog.setCanceledOnTouchOutside(false);
		// alertDialog.setCancelable(false);
		// alertDialog.show();
		// editText_Question.setEnabled(false);
		// radioButton_DIY.setEnabled(false);
		// radioButton_System.setEnabled(false);
		// spinner_Question.setEnabled(false);
		// startRecord(1);
		// }
		// }
		// });
		button_Record2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.performClick();
					wavPath = fileAccessObject.getTrainWavPath();
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
					break;
				case MotionEvent.ACTION_UP:
					stopRecord(2);
					break;
				default:
					break;
				}
				return false;
			}
		});
		// button_Record2.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// wavPath = fileAccessObject.getTrainWavPath();
		// if (wavPath == null) {
		// ToastUtil.show(TrainActivity.this,
		// R.string.audio_error_no_sdcard);
		// } else {
		// final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
		// TrainActivity.this);
		// View view1 = View.inflate(TrainActivity.this,
		// R.layout.dialog_record, null);
		// dialogBuilder1.setView(view1);
		// alertDialog = dialogBuilder1.create();
		// alertDialog.setCanceledOnTouchOutside(false);
		// alertDialog.setCancelable(false);
		// alertDialog.show();
		// startRecord(2);
		// }
		// }
		// });
		button_Record3.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.performClick();
					wavPath = fileAccessObject.getTrainWavPath();
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
					break;
				case MotionEvent.ACTION_UP:
					stopRecord(3);
					break;
				default:
					break;
				}
				return false;
			}
		});
		// button_Record3.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// wavPath = fileAccessObject.getTrainWavPath();
		// if (wavPath == null) {
		// ToastUtil.show(TrainActivity.this,
		// R.string.audio_error_no_sdcard);
		// } else {
		// final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
		// TrainActivity.this);
		// View view1 = View.inflate(TrainActivity.this,
		// R.layout.dialog_record, null);
		// dialogBuilder1.setView(view1);
		// alertDialog = dialogBuilder1.create();
		// alertDialog.setCanceledOnTouchOutside(false);
		// alertDialog.setCancelable(false);
		// alertDialog.show();
		// startRecord(3);
		// }
		// }
		// });
		radioButton_System.setOnCheckedChangeListener(radioCheckListener);
		radioButton_DIY.setOnCheckedChangeListener(radioCheckListener);

	}

	protected void startRecord(int n) {
		startTime = System.currentTimeMillis();
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, userid + "_"
				+ n + ".wav", userid + "_" + n + ".raw");
		if (result == 1) {
			ToastUtil.show(getApplicationContext(),
					R.string.audio_error_unknown);
			alertDialog.cancel();
			return;
		}
	}

	protected void stopRecord(int n) {
		alertDialog.cancel();
		audioRecordFunc.stopRecordAndFile();
		endTime = System.currentTimeMillis();
		timeList.add(endTime - startTime);
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

			NativeHTK.createMFCC(fileAccessObject, wavPath, userid, true);
			final Context mContext = this;

			ToastUtil.show(mContext, "hcopy");

			final String labUserPath = fileAccessObject.createLab(userid,
					timeList);
			final String protoFile = fileAccessObject.createProto(userid);
			final String trainlist = fileAccessObject.createTrainList(userid);


			NativeHTK.train(fileAccessObject, userid, timeList, trainlist,
					protoFile, labUserPath);
			ToastUtil.show(mContext, "hinit");
			NativeHTK.train2(fileAccessObject, userid, timeList,
					trainlist, protoFile, labUserPath);
			ToastUtil.show(mContext, "hrest");
			NativeHTK.train3(fileAccessObject, userid, timeList,
					trainlist, protoFile, labUserPath);
			ToastUtil.show(mContext, "hrest2");

			userAccessObject.trainUser(
					Integer.valueOf(userid.substring(2)), question);
			ToastUtil.show(mContext, R.string.train_end);
			

			// Intent i = getBaseContext().getPackageManager()
			// .getLaunchIntentForPackage(
			// getBaseContext().getPackageName());
			// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.addCategory(Intent.CATEGORY_HOME);
			// startActivity(i);
			// System.exit(0);
			


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
		userAccessObject = UserAccessObject
				.getInstance(getApplicationContext());
		// fileService = new FileService(getApplicationContext());
		fileAccessObject = FileAccessObject
				.getInstance(getApplicationContext());

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
