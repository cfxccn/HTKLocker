package com.flo.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast ;  
	public static void Show(Context context, CharSequence text) {
		toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
		toast.show();
	}
	public static void ShowResString(Context context, int resId) {
		String text=context.getString(resId);
		toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
		toast.show();
	}
}
