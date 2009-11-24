package com.isitchristmas.android;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ChristmasView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isItChristmas();
    }
    
    public void isItChristmas() {
    	((TextView) findViewById(R.id.answer)).setText(Christmas.answer(Christmas.isIt(), Locale.getDefault()));
    }
}