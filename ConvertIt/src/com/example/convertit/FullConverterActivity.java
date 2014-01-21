package com.example.convertit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity to convert different types of measurements.
 * 
 * @author Cameron Irwin
 * @version 1.0
 */
public class FullConverterActivity extends Activity {

	/**
	 * A list of units of mass and their respective conversion factors.
	 */
	static Map<String, Double> massMap = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("Milligrams (mg)", 0.001d);
			put("Centigrams (cg)", 0.01d);
			put("Decigrams (dg)", 0.1d);
			put("Carats (ct)", 0.2d);
			put("Grams (g)", 1d);
			put("Decagrams (dag)", 10d);
			put("Ounces (oz)", 28.3495d);
			put("Hectograms (hg)", 100d);
			put("Pounds (lb)", 453.592d);
			put("Kilograms (kg)", 1000d);
			put("Short Tons (T)", 907184d);
			put("Metric Tons (t)", 1000000d);
		}
	};

	/**
	 * A list of units of length and their respective conversion factors.
	 */
	static Map<String, Double> lengthMap = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("Millimeters (mm)", 0.001d);
			put("Centimeters (cm)", 0.01d);
			put("Inches (in)", 0.0254d);
			put("Decimeters (dg)", 0.1d);
			put("Feet (ft)", 0.3048d);
			put("Yard (yd)", 0.9144d);
			put("Meters (m)", 1d);
			put("Decameters (dam)", 10d);
			put("Hectometers (hm)", 100d);
			put("Kilometers (km)", 1000d);
		}
	};

	/**
	 * A list of units of volume and their respective conversion factors.
	 */
	static Map<String, Double> volumeMap = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("Milliliters (ml)", 0.001d);
			put("Cubic Centimeters (cc)", 0.001d);
			put("Centiliters (cl)", 0.01d);
			put("Fluid Ounces (oz)", 0.02957d);
			put("Deciliters (dl)", 0.1d);
			put("Cups", 0.23656d);
			put("Pints (pt)", 0.47312d);
			put("Quarts (qt)", 0.94624d);
			put("Liters (l)", 1d);
			put("Gallons (gal)", 3.78496d);
			put("Decaliters (dal)", 10d);
			put("Hectoliter (hl)", 100d);
			put("Kiloliters (kl)", 1000d);
		}
	};

	/**
	 * A list of units of time and their respective conversion factors.
	 */
	static Map<String, Double> timeMap = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			put("Milliseconds (ms)", 0.001d);
			put("Seconds (s)", 1d);
			put("Minutes (min)", 60d);
			put("Hours (h)", 3600d);
			put("Days (d)", 86400d);
			put("Weeks (w)", 604800d);
			put("Years (y)", 31536000d);
			put("Leap Years (ly)", 31622400d);
			put("Centuries (c)", 315532800d);
		}
	};

	/**
	 * A complete list of all available units and their respective conversion
	 * factors for easy access.
	 */
	static Map<String, Double> conversionMap = new HashMap<String, Double>() {
		private static final long serialVersionUID = 1L;

		{
			putAll(massMap);
			putAll(lengthMap);
			putAll(volumeMap);
			putAll(timeMap);
		}
	};

	// Variables associated with from unit
	Spinner spinFrom;
	ArrayAdapter<CharSequence> fromAdapter;
	String fromUnit;
	double fromConversionFactor;

	// Variables associated with to unit
	Spinner spinTo;
	ArrayAdapter<CharSequence> toAdapter;
	String toUnit;
	double toConversionFactor;

	// Variables to access type spinner
	Spinner spinType;
	ArrayAdapter<CharSequence> typeAdapter;

	EditText etFromValue;
	TextView tvConversion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Call super implementation and set content of page
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_converter);

		// Set reference to "from" spinner and set its adapter and listener
		spinFrom = (Spinner) findViewById(R.id.spinFrom);
		fromAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		fromAdapter.addAll(asSortedList(massMap.keySet()));
		fromAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinFrom.setAdapter(fromAdapter);
		spinFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// When "from" item is selected, calculate the unit and
				// conversion factor and update display
				fromUnit = spinFrom.getSelectedItem().toString();
				fromConversionFactor = conversionMap.get(fromUnit);
				calculateAndDisplay();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});

		// Set the selected "from" unit and its associated conversion factor
		fromUnit = spinFrom.getSelectedItem().toString();
		fromConversionFactor = conversionMap.get(fromUnit);

		// Set reference to "to" spinner and set its adapter and listener
		spinTo = (Spinner) findViewById(R.id.spinTo);
		toAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		toAdapter.addAll(asSortedList(massMap.keySet()));
		toAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinTo.setAdapter(toAdapter);
		spinTo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// When "to" item is selected, calculate the unit and conversion
				// factor and update display
				toUnit = spinTo.getSelectedItem().toString();
				toConversionFactor = conversionMap.get(toUnit);
				calculateAndDisplay();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});

		// Set the selected "to" unit and its associated conversion factor
		toUnit = spinTo.getSelectedItem().toString();
		toConversionFactor = conversionMap.get(toUnit);

		// Set reference to "type" spinner and set its adapter and listener
		spinType = (Spinner) findViewById(R.id.spinType);
		typeAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		typeAdapter.addAll("Mass", "Length", "Volume", "Time");
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinType.setAdapter(typeAdapter);
		spinType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// Clear fields
				etFromValue.setText("");
				toAdapter.clear();
				fromAdapter.clear();

				// Determine which map set to attach to the spinners
				String type = spinType.getSelectedItem().toString();
				if (type.equals("Mass")) {
					toAdapter.addAll(asSortedList(massMap.keySet()));
					fromAdapter.addAll(asSortedList(massMap.keySet()));
				} else if (type.equals("Length")) {
					toAdapter.addAll(asSortedList(lengthMap.keySet()));
					fromAdapter.addAll(asSortedList(lengthMap.keySet()));
				} else if (type.equals("Volume")) {
					toAdapter.addAll(asSortedList(volumeMap.keySet()));
					fromAdapter.addAll(asSortedList(volumeMap.keySet()));
				} else if (type.equals("Time")) {
					toAdapter.addAll(asSortedList(timeMap.keySet()));
					fromAdapter.addAll(asSortedList(timeMap.keySet()));
				}

				// Update content of spinners
				toAdapter.notifyDataSetChanged();
				fromAdapter.notifyDataSetChanged();

				// Set unit and conversion values
				toUnit = spinTo.getSelectedItem().toString();
				toConversionFactor = conversionMap.get(toUnit);
				fromUnit = spinFrom.getSelectedItem().toString();
				fromConversionFactor = conversionMap.get(fromUnit);

				// Update display
				calculateAndDisplay();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

		});

		// Set reference to result text view
		tvConversion = (TextView) findViewById(R.id.tvConversion);

		// Set reference to the edit text and add listener
		etFromValue = (EditText) findViewById(R.id.etFromValue);
		etFromValue.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable etValue) {
				// Update display
				calculateAndDisplay();
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
		getMenuInflater().inflate(R.menu.full_converter, menu);
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
	 * Handles the calculation of the conversion and sets the appropriate text
	 * to the result text view.
	 */
	public void calculateAndDisplay() {
		if (etFromValue.length() > 0) {
			// If only a decimal point is entered, prepend a 0 so conversion can
			// work
			if (etFromValue.getText().toString().equals(".")) {
				etFromValue.setText("0.");
				etFromValue.setSelection(etFromValue.length());
			} else {
				double fromValue = Double.valueOf(etFromValue.getText()
						.toString());

				// Calculate conversion value by multiplying by ratio of the
				// "from" and "to" factors
				double toValue = fromValue
						* (fromConversionFactor / toConversionFactor);
				String fromValueString = BigDecimal.valueOf(fromValue)
						.stripTrailingZeros().toPlainString();
				String toValueString = BigDecimal.valueOf(toValue)
						.stripTrailingZeros().toPlainString();

				// Display text
				tvConversion.setText(fromValueString + " " + fromUnit + "\n=\n"
						+ toValueString + " " + toUnit);
			}
		} else {
			tvConversion.setText("");
		}
	}

	/**
	 * Takes a collection of unit strings and converts it to a list sorted by
	 * conversion factor.
	 * 
	 * @param c
	 *            Collection to be sorted
	 * @return List sorted by conversion factor
	 */
	public static List<String> asSortedList(Collection<String> c) {
		List<String> list = new ArrayList<String>(c);
		java.util.Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (conversionMap.get(s1) > conversionMap.get(s2)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return list;
	}

}
