package com.flo.adapter;


import java.util.List;
import java.util.Map;

import com.flo.util.ToastUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AuthListViewAdapter extends BaseAdapter {
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

	public AuthListViewAdapter(Context c,
			List<Map<String, Object>> l, int r,
			String[] from, int[] to) {
		userList = l;
		resource=r;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
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
			String textView_UserName=(String) userInfo.get(keyString[0]);
			String USERID=(String) userInfo.get(keyString[2]);
			
			holder.textView_UserName.setText(textView_UserName);
			holder.imageButton_UnLock.setOnClickListener(new ButtonListener(
					position,textView_UserName,USERID));
		}
		return convertView;
	}

	class ButtonListener implements OnClickListener {
		private int position;
		String textView_UserName;
		String USERID;
		
		ButtonListener(int pos,String name,String userid) {
			position = pos;
			textView_UserName=name;
			USERID=userid;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.imageButton_UnLock.getId())
				ToastUtil.show(mContext, "" + position+"   "+textView_UserName+"  "+USERID);
		}
	}

}
