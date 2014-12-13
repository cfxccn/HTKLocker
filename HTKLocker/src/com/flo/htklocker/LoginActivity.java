package com.flo.htklocker;

import com.flo.service.LoginService;
import com.flo.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends Activity {
	LoginService loginService;
	String password1;
	String password2;

	LinearLayout linearLayout_FirstLogin;
	EditText editText_NewPassword;
	EditText editText_ConfirmPassword;
	Button button_Register;
	Button button_Reset;

	LinearLayout linearLayout_Login;
	EditText editText_Password;
	Button button_Submit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginService=new LoginService(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_login);
		// sharedPreferences = getSharedPreferences("HTKLocker", MODE_PRIVATE);
		// oldPassword = sharedPreferences.getString("PASSWORD", "FIRSTLOGIN");
		if (loginService.isFirstLogin()) {
			final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(
					LoginActivity.this);
			View view1 = View.inflate(LoginActivity.this,
					R.layout.dialog_first_login, null);
			dialogBuilder1.setView(view1);
			dialogBuilder1.setTitle(R.string.first_login_tips);
			editText_NewPassword = (EditText) view1
					.findViewById(R.id.editText_NewPassword);
			editText_ConfirmPassword = (EditText) view1
					.findViewById(R.id.editText_ConfirmPassword);
			button_Register = (Button) view1.findViewById(R.id.button_Register);
			button_Reset = (Button) view1.findViewById(R.id.button_Reset);
			button_Register.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					String password1 = editText_NewPassword.getText()
							.toString();
					String password2 = editText_ConfirmPassword.getText()
							.toString();
					if (password2.equals(password1)) {

						if (password1.equals("")) {
							ToastUtil.show(getApplicationContext(),
									R.string.password_blank);
						} else {
							loginService.setPassword(password1);
							ToastUtil.show(getApplicationContext(),
									R.string.register_success);
							Intent intent = new Intent(getApplicationContext(),
									MainActivity.class);
							startActivity(intent);
						}
					} else {
						ToastUtil.show(getApplicationContext(),
								R.string.passwords_diff);
					}
				}
			});
			button_Reset.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					editText_NewPassword.setText("");
					editText_ConfirmPassword.setText("");
				}
			});
			AlertDialog alertDialog1 = dialogBuilder1.create();
			alertDialog1.setCanceledOnTouchOutside(false);
			alertDialog1.setCancelable(false);
			alertDialog1.show();
		} else {
			final AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(
					LoginActivity.this);
			View view2 = View.inflate(LoginActivity.this,
					R.layout.dialog_login, null);
			dialogBuilder2.setView(view2);
			dialogBuilder2.setTitle(R.string.login_tips);
			editText_Password = (EditText) view2
					.findViewById(R.id.editText_Password);
			button_Submit = (Button) view2.findViewById(R.id.button_Submit);
			button_Submit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					password1 = editText_Password.getText().toString();
					if (loginService.validateUser(password1)) {
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
					} else {
						ToastUtil.show(getApplicationContext(),
								R.string.password_error);
					}
				}
			});
			AlertDialog alertDialog2 = dialogBuilder2.create();
			alertDialog2.setCanceledOnTouchOutside(false);
			alertDialog2.setCancelable(false);
			alertDialog2.show();
		}
	}

}