package com.flo.htklocker;

import com.flo.service.LoginService;
import com.flo.util.ToastUtil;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	LoginService loginService;
	Switch switch_Service;
	Button button_UserManage;
	Button button_Test;
	Button button_Config;
	Button button_ChangePassword;
	Button button_Exit;
	Button button_DEVTest;
	Intent serviceIntent;

	private void bindView() {
		switch_Service = (Switch) findViewById(R.id.switch_Service);
		button_UserManage = (Button) findViewById(R.id.button_UserManage);
		button_Test = (Button) findViewById(R.id.button_Test);
		button_Config = (Button) findViewById(R.id.button_Config);
		button_ChangePassword = (Button) findViewById(R.id.button_ChangePassword);
		button_Exit = (Button) findViewById(R.id.button_Exit);
		button_DEVTest= (Button) findViewById(R.id.button_Dev);
		if (BackgroundService.getStatus() == BackgroundService.Status.RUNNING) {
			switch_Service.setChecked(true);
		} else {
			switch_Service.setChecked(false);
		}
		serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
		switch_Service
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						if (arg1) {
							startService(serviceIntent);
						} else {
							stopService(serviceIntent);
						}
					}
				});
	}

	private void bindListener() {
		button_UserManage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						UserActivity.class);
				startActivityForResult(intent, 100);
			}
		});
		button_Test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						TestActivity.class);
				startActivityForResult(intent, 100);
			}
		});
		button_Config.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						ConfigActivity.class);
				startActivityForResult(intent, 100);
			}
		});
		button_ChangePassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.dialog_change_password);
				dialog.setTitle(R.string.change_password);
				Button button_Register = (Button) dialog
						.findViewById(R.id.button_Register);
				Button button_Reset = (Button) dialog
						.findViewById(R.id.button_Reset);
				final EditText editText_OldPassword = (EditText) dialog
						.findViewById(R.id.editText_OldPassword);
				final EditText editText_NewPassword = (EditText) dialog
						.findViewById(R.id.editText_NewPassword);
				final EditText editText_ConfirmPassword = (EditText) dialog
						.findViewById(R.id.editText_ConfirmPassword);
				button_Register.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (loginService.validateUser(editText_OldPassword
								.getText().toString())) {
							String password1 = editText_NewPassword.getText()
									.toString();
							String password2 = editText_ConfirmPassword
									.getText().toString();
							if (password2.equals(password1)) {
								if (password1.equals("")) {
									ToastUtil.show(getApplicationContext(),
											R.string.password_blank);
								} else {
									loginService.setPassword(password1);
									ToastUtil.show(getApplicationContext(),
											R.string.password_change_success);
									dialog.cancel();
								}
							} else {
								ToastUtil.show(getApplicationContext(),
										R.string.passwords_diff);
							}
						} else {
							ToastUtil.show(getApplicationContext(),
									R.string.password_error);
						}
					}
				});
				button_Reset.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						editText_OldPassword.setText("");
						editText_NewPassword.setText("");
						editText_ConfirmPassword.setText("");
					}
				});
				dialog.show();
			}
		});
		button_Exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				stopService(serviceIntent);
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
		});
		button_DEVTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						AuthActivity.class);
				startActivityForResult(intent, 100);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginService = new LoginService(getApplicationContext());
		bindView();
		bindListener();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return false;
		case KeyEvent.KEYCODE_MENU:
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
