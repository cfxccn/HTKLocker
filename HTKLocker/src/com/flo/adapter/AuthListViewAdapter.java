package com.flo.adapter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.flo.htklocker.AuthActivity;
import com.flo.htklocker.R;
import com.flo.service.FileService;
import com.flo.service.UserService;
import com.flo.util.AudioRecordFunc;
import com.flo.util.NativeHTK;
import com.flo.util.ToastUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	FileService fileService;
	UserService userService;

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
		//fileService = new FileService(mContext);
		fileService =  FileService.getInstance(mContext);
		userService = UserService.getInstance(mContext);

		//userService = new UserService(mContext);

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

			holder.textView_UserName.setText(textView_UserName);
			holder.imageButton_UnLock.setOnClickListener(new ButtonListener(
					position, textView_UserName, userId));
		}
		return convertView;
	}

	class ButtonListener implements OnClickListener {
		// private int position;
		String textView_UserName;
		String userId;

		ButtonListener(int pos, String name, String _userId) {
			// position = pos;
			textView_UserName = name;
			userId = _userId;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.imageButton_UnLock.getId())
				wavPath = fileService.getTestWavPath();
			final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
					mContext);
			View view1 = View.inflate(mContext, R.layout.dialog_unlock, null);
			dialogBuilder1.setView(view1);
			alertDialog = dialogBuilder1.create();
			alertDialog.setCanceledOnTouchOutside(false);
			alertDialog.setCancelable(false);
			alertDialog.show();
			startRecord(userId);
		}
	}

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
		new Handler().postDelayed(new Runnable() {
			public void run() {
				stopRecord(userId);
				alertDialog.cancel();

			}
		}, 3000);
	}

	protected void stopRecord(String userId) {
		audioRecordFunc.stopRecordAndFile();
		NativeHTK.createMFCC(fileService, wavPath, userId, false);
		try {
			NativeHTK.test(fileService, userService, userId);
		} catch (IOException e) {
			ToastUtil.show(mContext, R.string.error);
		} catch (InterruptedException e) {
			ToastUtil.show(mContext, R.string.error);
		}
		verify(userId);
	}

	protected void verify(String userId) {
		boolean result=userId.equalsIgnoreCase(fileService.parseRecoMlf());
		if(result){
			int id=Integer.parseInt(userId.substring(2));
			userService.verifyUser(id);
			ToastUtil.show(mContext, R.string.unlock_success);
			AuthActivity authActivity=(AuthActivity)mContext;
			authActivity.unLock();
			authActivity.finish();
		}else{
			ToastUtil.show(mContext, R.string.unlock_failure);
		}
	}

}
