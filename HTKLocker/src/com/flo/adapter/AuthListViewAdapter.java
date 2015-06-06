package com.flo.adapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.flo.htklocker.AuthActivity;
import com.flo.htklocker.R;
import com.flo.accessobject.*;
import com.flo.util.AudioRecordFunc;
import com.flo.util.NativeHTK;
import com.flo.util.ToastUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AuthListViewAdapter extends BaseAdapter {
	AudioRecordFunc audioRecordFunc;

	String wavlist;
	String wavPath;

	public class ButtonViewHolder {
		TextView textView_UserName;
		ImageButton imageButton_UnLock;
	}

	private List<Map<String, Object>> userList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private ButtonViewHolder holder;
	private int resource;
	FileAccessObject fileAccessObject;
	UserAccessObject userAccessObject;

	AlertDialog alertDialog;

	public AuthListViewAdapter(Context c, List<Map<String, Object>> l, int r,
			String[] from, int[] to) {
		userList = l;
		resource = r;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
		// fileService = new FileService(mContext);
		fileAccessObject = FileAccessObject.getInstance(mContext);
		userAccessObject = UserAccessObject.getInstance(mContext);

		// userService = new UserService(mContext);

	}

	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (ButtonViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(resource, null);
			holder = new ButtonViewHolder();
			holder.textView_UserName = (TextView) convertView
					.findViewById(valueViewID[0]);
			holder.imageButton_UnLock = (ImageButton) convertView
					.findViewById(valueViewID[1]);
			convertView.setTag(holder);
		}
		Map<String, Object> userInfo = userList.get(position);
		if (userInfo != null) {
			String textView_UserName = (String) userInfo.get(keyString[0]);
			String userId = (String) userInfo.get(keyString[2]);
			String question = (String) userInfo.get(keyString[3]);
			;
			holder.textView_UserName.setText(textView_UserName);
			holder.imageButton_UnLock.setOnTouchListener(new TouchListener(
					position, textView_UserName, userId, question));
			// holder.imageButton_UnLock.setOnClickListener(new ButtonListener(
			// position, textView_UserName, userId, question));
		}

		return convertView;
	}

	class TouchListener implements OnTouchListener {
		// private int position;
		String textView_UserName;
		String userId;
		String question;
		Long startTime, endTime;

		TouchListener(int pos, String name, String _userId, String _question) {
			// position = pos;
			textView_UserName = name;
			userId = _userId;

			question = _question;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				v.performClick();
				startTime = System.currentTimeMillis();
				wavPath = fileAccessObject.getTestWavPath();
				final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
						mContext);
				View view1 = View.inflate(mContext, R.layout.dialog_unlock,
						null);

				TextView textView2 = (TextView) view1
						.findViewById(R.id.textView2);
				StringBuilder questionString = new StringBuilder(view1
						.getResources().getString(R.string.unlock_tips));
				questionString.append(question);
				textView2.setText(questionString);
				dialogBuilder1.setView(view1);
				alertDialog = dialogBuilder1.create();
				alertDialog.setCanceledOnTouchOutside(false);
				alertDialog.setCancelable(false);
				Window window = alertDialog.getWindow();
				window.setGravity(Gravity.TOP);
				alertDialog.show();
				startRecord(userId);
				break;
			case MotionEvent.ACTION_UP:
				endTime = System.currentTimeMillis();
				if ((endTime - startTime) > 800) {
					ToastUtil.show(mContext, R.string.record_end);
					stopRecord(userId);
				} else {
					ToastUtil.show(mContext, R.string.test_too_short);
					alertDialog.cancel();
					audioRecordFunc.stopRecordAndFile();
				}

				break;
			case MotionEvent.ACTION_CANCEL:
				alertDialog.cancel();
				audioRecordFunc.stopRecordAndFile();
				break;
			default:
				break;
			}

			return false;
		}
	}

	//
	// class ButtonListener implements OnClickListener {
	// // private int position;
	// String textView_UserName;
	// String userId;
	// String question;
	//
	// ButtonListener(int pos, String name, String _userId, String _question) {
	// // position = pos;
	// textView_UserName = name;
	// userId = _userId;
	//
	// question = _question;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// int vid = v.getId();
	// if (vid == holder.imageButton_UnLock.getId())
	// wavPath = fileAccessObject.getTestWavPath();
	// final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
	// mContext);
	//
	// View view1 = View.inflate(mContext, R.layout.dialog_unlock, null);
	//
	// TextView textView2 = (TextView) view1.findViewById(R.id.textView2);
	// StringBuilder questionString = new StringBuilder(view1
	// .getResources().getString(R.string.unlock_tips));
	// questionString.append(question);
	// textView2.setText(questionString);
	// dialogBuilder1.setView(view1);
	//
	// alertDialog = dialogBuilder1.create();
	// alertDialog.setCanceledOnTouchOutside(false);
	// alertDialog.setCancelable(false);
	// alertDialog.show();
	// startRecord(userId);
	// }
	// }
	//
	protected void startRecord(final String userId) {
		String wavString = userId + ".wav";
		String rawString = userId + ".raw";
		audioRecordFunc = AudioRecordFunc.getInstance();
		int result = audioRecordFunc.startRecordAndFile(wavPath, wavString,
				rawString);
		if (result == 1) {
			ToastUtil.show(mContext, R.string.audio_error_unknown);
			return;
		}
	}

	protected void stopRecord(String userId) {
		alertDialog.cancel();
		audioRecordFunc.stopRecordAndFile();
		NativeHTK.createMFCC(fileAccessObject, wavPath, userId, false);
		try {
			NativeHTK.test(fileAccessObject, userAccessObject, userId);
		} catch (IOException e) {
			ToastUtil.show(mContext, R.string.error);
		} catch (InterruptedException e) {
			ToastUtil.show(mContext, R.string.error);
		}
		verify(userId);
	}

	protected void verify(String userId) {
		String result = fileAccessObject.parseRecoMlf();
		String[] r = result.split("-");
		String r1 = r[0];
		Double r2 = Double.parseDouble(r[1]);
		int id = Integer.parseInt(userId.substring(2));

		Double threshold = Double.parseDouble(userAccessObject
				.getUserThreshold(id));
		if (r1.equalsIgnoreCase(userId) && (r2 < threshold)) {
			userAccessObject.verifyUser(id);
			ToastUtil.show(mContext, R.string.unlock_success);
			ToastUtil.show(mContext, userId + " " + r1 + " " + r2 + " "
					+ threshold);
			AuthActivity authActivity = (AuthActivity) mContext;
			authActivity.unLock();
			authActivity.finish();
		} else {
			ToastUtil.show(mContext, R.string.unlock_failure);
			ToastUtil.show(mContext, userId + " " + r1 + " " + r2 + " "
					+ threshold);
		}
	}
}
