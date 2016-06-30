package com.alijiujiu.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileTools {
	public static boolean isMobileNoMatch(String mobile) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobile);

		// System.out.println(m.matches()+"---");

		return m.matches();
	}
}
