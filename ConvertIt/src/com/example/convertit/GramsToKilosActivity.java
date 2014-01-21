package com.example.convertit;

import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity to convert gram values to kilogram values.
 * 
 * @author Cameron Irwin
 * @version 1.0
 */
public class GramsToKilosActivity extends Activity {

	EditText etGrams;
	TextView tvKilos;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Call super implementation and set content of page
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grams_to_kilos);

		// Set references to views
		etGrams = (EditText) findViewById(R.id.etGrams);
		tvKilos = (TextView) findViewById(R.id.tvKilos);

		// Add listener for edit text
		etGrams.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable etValue) {
				if (etValue.length() > 0) {
					// If only a decimal point is entered, prepend a 0 so
					// conversion can work
					if (etValue.toString().equals(".")) {
						etGrams.setText("0.");
						etGrams.setSelection(etGrams.length());
					} else {
						double g = Double.valueOf(etValue.toString());

						// Calculate value
						double kg = g / 1000;
						BigDecimal grams = BigDecimal.valueOf(g)
								.stripTrailingZeros();
						BigDecimal kilograms = BigDecimal.valueOf(kg)
								.stripTrailingZeros();

						// Display results
						tvKilos.setText(grams.toPlainString() + " grams\n=\n"
								+ kilograms.toPlainString() + " kilograms");
					}
				} else {
					tvKilos.setText("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

		});

		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grams_to_kilos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Sends user to the KilosToGramsActivity
	 * 
	 * @param v
	 *            Button that was clicked
	 */
	public void toKiloToGrams(View v) {
		Intent intent = new Intent(this, KilosToGramsActivity.class);
		startActivity(intent);
	}

}
