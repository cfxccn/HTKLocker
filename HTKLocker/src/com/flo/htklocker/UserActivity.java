package com.flo.htklocker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.flo.accessobject.UserAccessObject;
import com.flo.model.User;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserActivity extends Activity {
	UserAccessObject userAccessObject;
	List<Map<String, Object>> userMapList;
	ListView listView;
	SimpleAdapter adapter;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) ; 
	
	
	private List<Map<String, Object>> list2Map(List<User> userList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (userList == null) {
			return result;
		}
		for (User u : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("textView_Username", u.getName());
			map.put("USERID", u.getNameId());
			if (u.getTrainTime() == null) {
				map.put("textView_TrainTime", getResources().getString(R.string.train_time)+":"+getResources().getString(R.string.none));
			} else {
				map.put("textView_TrainTime", getResources().getString(R.string.train_time)+":"+simpleDateFormat.format(u.getTrainTime()));
			}
			if (u.getLastVerifyTime() == null) {
				map.put("textView_TestTime", getResources().getString(R.string.last_verify_time)+":"+getResources().getString(R.string.none));
			} else {
				map.put("textView_TestTime", getResources().getString(R.string.last_verify_time)+":"+simpleDateFormat.format(u.getLastVerifyTime()));
			}

			if (u.getIsTrained()) {
				map.put("textView_TrainState",
						getResources().getString(R.string.istrained));
			} else {
				map.put("textView_TrainState",
						getResources().getString(R.string.untrained));
			}
			result.add(map);
		}
		return result;
	}

	private void bindView() {
		listView = (ListView) findViewById(R.id.listView);
		userMapList = list2Map(userAccessObject.getUserList());
		adapter = new SimpleAdapter(this, userMapList, R.layout.item_user,
				new String[] { "textView_Username", "textView_TrainState",
						"textView_TrainTime", "textView_TestTime" }, new int[] {
						R.id.textView_Username, R.id.textView_TrainState,
						R.id.textView_TrainTime, R.id.textView_TestTime });
		listView.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_user);
	}

	@Override
	protected void onResume() {
		super.onResume();
		userAccessObject = UserAccessObject.getInstance(getApplicationContext());
		bindView();
		bindListener();
	}

	private void bindListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ListView listView = (ListView) arg0;
				@SuppressWarnings("unchecked")
				final Map<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				final Dialog dialog = new Dialog(UserActivity.this);
				dialog.setContentView(R.layout.dialog_user_manage);
				dialog.setTitle(map.get("textView_Username").toString());
				Button button_Train = (Button) dialog
						.findViewById(R.id.button_Train);
				Button button_Delete = (Button) dialog
						.findViewById(R.id.button_Delete);
				button_Train.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getApplicationContext(),
								TrainActivity.class);
						intent.putExtra("USERNAME", map
								.get("textView_Username").toString());
						intent.putExtra("USERID", map.get("USERID").toString());
						dialog.cancel();
						startActivityForResult(intent, 100);
					}
				});
				button_Delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						User user = new User();
						user.setId(Integer.valueOf(map.get("USERID").toString()
								.substring(2)));
						if (userAccessObject.deleteUser(user)) {
							ToastUtil.show(getApplicationContext(),
									R.string.delete_success);
							dialog.cancel();
							bindView();
						}
						;
					}
				});
				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.add_user:
			final Dialog dialog = new Dialog(UserActivity.this);
			dialog.setContentView(R.layout.dialog_user_register);
			dialog.setTitle(R.string.add_user);
			Button button_Register = (Button) dialog
					.findViewById(R.id.button_Register);
			final EditText editText_Username = (EditText) dialog
					.findViewById(R.id.editText_Username);
			button_Register.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String username = editText_Username.getEditableText()
							.toString();
					if (username.equals("")) {
						ToastUtil.show(getApplicationContext(),
								R.string.username_blank);
						return;
					}

					User user = new User();
					user.setName(username);
					user.setIsTrained(false);
					if (userAccessObject.addUser(user)) {
						ToastUtil.show(getApplicationContext(),
								R.string.register_success);
						dialog.cancel();
						bindView();
					} else {
						ToastUtil.show(getApplicationContext(),
								R.string.add_user_failure);
					}
				}
			});
			dialog.show();
			return true;
		case android.R.id.home:
			finish();
			break;
		}
		
		return super.onOptionsItemSelected(item);

	}

}
