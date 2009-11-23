package com.isitchristmas.android;

import android.text.format.Time;

public class Christmas {

	public static boolean isIt() {
		Time now = new Time();
		now.set(System.currentTimeMillis());
		return isIt(now);
	}
	
	public static boolean isIt(Time time) {
		return false;
	}
	
	public static String yes() {
		return "YES";
	}
}