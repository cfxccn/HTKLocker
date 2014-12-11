package com.flo.htklocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flo.model.User;
import com.flo.service.UserService;
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
	UserService userService;
	List<Map<String, Object>> userMapList;
	ListView listView;
	SimpleAdapter adapter;

	private List<Map<String, Object>> list2Map(List<User> userList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (userList == null) {
			return result;
		}
		for (User u : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("textView_Username", u.getName());
			map.put("USERID", u.getId());

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

	private void bindControl() {
		listView = (ListView) findViewById(R.id.listView);
		userMapList = list2Map(userService.getUserList());
		adapter = new SimpleAdapter(this, userMapList, R.layout.user_item,
				new String[] { "textView_Username", "textView_TrainState" },
				new int[] { R.id.textView_Username, R.id.textView_TrainState });
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
		userService = new UserService(getApplicationContext());
		bindControl();
		setOnClickListener();
	}

	private void setOnClickListener() {
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
						intent.putExtra("USERNAME",
								map.get("textView_Username").toString());
						intent.putExtra("USERID",
								map.get("USERID").toString());
						dialog.cancel();
						startActivityForResult(intent, 100);
					}
				});
				button_Delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						User user = new User();
						user.setId((int) map.get("USERID"));
						if (userService.deleteUser(user)) {
							ToastUtil.ShowResString(getApplicationContext(),
									R.string.delete_success);
							dialog.cancel();
							bindControl();
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
					User user = new User();
					user.setName(editText_Username.getEditableText().toString());
					user.setIsTrained(false);
					if (userService.addUser(user)) {
						ToastUtil.ShowResString(getApplicationContext(),
								R.string.register_success);
						dialog.cancel();
						bindControl();
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
