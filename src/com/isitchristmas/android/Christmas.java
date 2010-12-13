package com.isitchristmas.android;

import java.util.Locale;

import android.text.format.Time;

public class Christmas {
	public static final String TAG = "IsItChristmas";

	public static final int MONTH = 11; // 0-indexed
	public static final int DAY = 25;   // 1-indexed
	
	public static int answer(boolean isIt, Locale locale) {
		return isIt ? yes(locale) : no(locale); 
	}
	
	public static boolean isIt() {
		Time now = new Time();
		now.set(System.currentTimeMillis());
		return now.month == MONTH && now.monthDay == DAY;
	}
	
	// returns the epoch-based time of the next Christmas, at midnight, in milliseconds
	public static long time() {
		Time now = new Time();
		now.setToNow();
		int thisYear = now.year;
		
		Time christmas = new Time();
		christmas.set(DAY, MONTH, thisYear);
		
		if (System.currentTimeMillis() > christmas.toMillis(false))
			christmas.set(DAY, MONTH, thisYear + 1);
		
		return christmas.toMillis(false);
	}
	
	public static int yes(Locale locale) {
		String country = locale.getCountry();
		String language = locale.getLanguage();
		
		if (country.equals("CA"))
			return R.string.canada_yes;
		
		if (language.equals("ar"))
			return R.string.ar_yes;
		if (language.equals("cs"))
			return R.string.cs_yes;
		if (language.equals("da"))
			return R.string.da_yes;
		if (language.equals("de"))
			return R.string.de_yes;
		if (language.equals("el"))
			return R.string.el_yes;
		if (language.equals("en"))
			return R.string.en_yes;
		if (language.equals("es"))
			return R.string.es_yes;
		if (language.equals("fi"))
			return R.string.fi_yes;
		if (language.equals("fr"))
			return R.string.fr_yes;
		if (language.equals("iw"))
			return R.string.iw_yes;
		if (language.equals("hr"))
			return R.string.hr_yes;
		if (language.equals("hu"))
			return R.string.hu_yes;
		if (language.equals("in"))
			return R.string.in_yes;
		if (language.equals("it"))
			return R.string.it_yes;
		if (language.equals("ko"))
			return R.string.ko_yes;
		if (language.equals("nb"))
			return R.string.nb_yes;
		if (language.equals("nl"))
			return R.string.nl_yes;
		if (language.equals("pl"))
			return R.string.pl_yes;
		if (language.equals("pt"))
			return R.string.pt_yes;
		if (language.equals("ro"))
			return R.string.ro_yes;
		if (language.equals("ru"))
			return R.string.ru_yes;
		if (language.equals("sk"))
			return R.string.sk_yes;
		if (language.equals("sv"))
			return R.string.sv_yes;
		if (language.equals("th"))
			return R.string.th_yes;
		if (language.equals("tr"))
			return R.string.tr_yes;
		if (language.equals("uk"))
			return R.string.uk_yes;
		if (language.equals("zh"))
			return R.string.zh_yes;
		if (language.equals("sr"))
			return R.string.sr_yes;
							
		return R.string.default_yes;
	}
	
	public static int no(Locale locale) {
		String country = locale.getCountry();
		String language = locale.getLanguage();
		
		if (country.equals("CA"))
			return R.string.canada_no;
		
		if (language.equals("ar"))
			return R.string.ar_no;
		if (language.equals("cs"))
			return R.string.cs_no;
		if (language.equals("da"))
			return R.string.da_no;
		if (language.equals("de"))
			return R.string.de_no;
		if (language.equals("el"))
			return R.string.el_no;
		if (language.equals("en"))
			return R.string.en_no;
		if (language.equals("es"))
			return R.string.es_no;
		if (language.equals("fi"))
			return R.string.fi_no;
		if (language.equals("fr"))
			return R.string.fr_no;
		if (language.equals("iw"))
			return R.string.iw_no;
		if (language.equals("hr"))
			return R.string.hr_no;
		if (language.equals("hu"))
			return R.string.hu_no;
		if (language.equals("in"))
			return R.string.in_no;
		if (language.equals("it"))
			return R.string.it_no;
		if (language.equals("ko"))
			return R.string.ko_no;
		if (language.equals("nb"))
			return R.string.nb_no;
		if (language.equals("nl"))
			return R.string.nl_no;
		if (language.equals("pl"))
			return R.string.pl_no;
		if (language.equals("pt"))
			return R.string.pt_no;
		if (language.equals("ro"))
			return R.string.ro_no;
		if (language.equals("ru"))
			return R.string.ru_no;
		if (language.equals("sk"))
			return R.string.sk_no;
		if (language.equals("sv"))
			return R.string.sv_no;
		if (language.equals("th"))
			return R.string.th_no;
		if (language.equals("tr"))
			return R.string.tr_no;
		if (language.equals("uk"))
			return R.string.uk_no;
		if (language.equals("zh"))
			return R.string.zh_no;
		if (language.equals("sr"))
			return R.string.sr_no;
		
		return R.string.default_no;
	}

}