package com.example.convertit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Launch activity to allow navigation to the other three pages.
 * 
 * @author Cameron Irwin
 * @version 1.0
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Sends user to the KilosToGramsActivity
	 * 
	 * @param v
	 *            Button that was clicked
	 */
	public void kiloToGrams(View v) {
		Intent intent = new Intent(this, KilosToGramsActivity.class);
		startActivity(intent);
	}

	/**
	 * Sends user to the GramsToKilosActivity
	 * 
	 * @param v
	 *            Button that was clicked
	 */
	public void gramsToKilo(View v) {
		Intent intent = new Intent(this, GramsToKilosActivity.class);
		startActivity(intent);
	}

	/**
	 * Sends user to the FullConverterActivity
	 * 
	 * @param v
	 *            Button that was clicked
	 */
	public void toFullConverter(View v) {
		Intent intent = new Intent(this, FullConverterActivity.class);
		startActivity(intent);
	}

}
