package com.isitchristmas.android;

import java.util.Locale;

import android.text.format.Time;

public class Christmas {

	public static String answer(boolean isIt, Locale locale) {
		return isIt ? yes(locale) : no(locale); 
	}
	
	public static boolean isIt() {
		Time now = new Time();
		now.set(System.currentTimeMillis());
		return false;
	}
	
	public static String yes(Locale locale) {
		String country = locale.getCountry();
		String language = locale.getLanguage();
		String code = locale.toString();
		
		if (country.equals("CA"))
			return "YES/OUI";
		
		if (language.equals("en"))
			return "YES"; 
							
		return "YES";
	}
	
	public static String no(Locale locale) {
		String country = locale.getCountry();
		String language = locale.getLanguage();
		String code = locale.toString();
		
		if (country.equals("CA"))
			return "NO/NON";
		
		if (language.equals("en"))
			return "NO";
		
		return "NO";
	}
}