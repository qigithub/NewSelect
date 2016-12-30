package com.qi.newselect.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
	private static Toast toast = null;

	public static final int SHOW = 0;
	public static final int LONG = 1;

	public static void showToast(Context context, String msg) {
		if (msg == null || "".equals(msg)) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}

	/**
	 *
	 * @param context
	 * @param cls 类名.class
     */
	public static void showActNameTest(Context context, Class<?> cls) {
//		Log.e("activity",cls.getName());
//		Toast toast1;
//		if (!GlobalParams.isTest){
//			return;
//		}
//		if (context == null ) {
//			return;
//		}
//		toast1 = Toast.makeText(context, cls.getSimpleName(), Toast.LENGTH_LONG);
//		toast1.setGravity(Gravity.TOP,0,0);
//		toast1.show();
	}


	public static void showToast(Context context, String msg, int time) {
		if (msg == null || "".equals(msg)) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, msg, time);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	public static void showToast(Context context, int resid) {
		if (toast == null) {
			toast = Toast.makeText(context, resid, Toast.LENGTH_SHORT);
		} else {
			toast.setText(context.getString(resid));
		}
		toast.show();
	}
}
