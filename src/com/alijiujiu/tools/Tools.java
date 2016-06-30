package com.alijiujiu.tools;

import android.content.Context;
import android.widget.Toast;

public class Tools {

	public static String matchMapErrorCode(int errorCode) {
		String errorInfo = null;
		switch (errorCode) {
		case 61: {
			errorInfo = "GPS定位结果，GPS定位成功。";
			break;
		}
		case 62: {
			errorInfo = "无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。";
			break;
		}
		}
		return errorInfo;
	}

	public static void matchBmobErrorCode(Context context, int code) {
		String msg = "未知错误";
		switch (code) {
		case 9010: {
			msg = "网络超时";
			break;
		}
		case 9016: {
			msg = "无网络连接，请检查您的手机网络";
			break;
		}
		case 9015: {
			msg = "未知错误";
			break;
		}
		case 9003: {
			msg = "上传文件错误";
			break;
		}
		case 9004: {
			msg = "上传文件失败";
			break;
		}
		case 9007: {
			msg = "文件大小超过10M";
			break;
		}
		case 101:{
			msg = "没有找到相关数据";
			break;
		}
		default:
			msg = "未知错误";
		}
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
