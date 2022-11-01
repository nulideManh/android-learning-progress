package com.karl.c1_5_temparature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, TextWatcher {
    RadioGroup unit;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unit = findViewById(R.id.unit);  // Get the "Units" radio button group
        unit.setOnCheckedChangeListener(this);        // Set this to the listener

        value = findViewById(R.id.value); // Get input field
        value.addTextChangedListener(this);           // Set this to the listener

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        calc();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        calc();
    }

    // Temperature conversion calculation
    protected void calc() {
        // Get textbox
        TextView degF = findViewById(R.id.degF);
        TextView degC = findViewById(R.id.degC);
        if (!value.getText().toString().equals("")) {
            //When the input box value is not empty
            double f, c;  // Store temperature value conversion results
            // If you choose to enter the temperature in Fahrenheit
            if (unit.getCheckedRadioButtonId() == R.id.unitF) {
                f = Double.parseDouble(value.getText().toString());
                c = (f - 32) * 5 / 9;  // Fahrenheit => Celsius
            } else {   // If you choose to enter the temperature in Celsius
                c = Double.parseDouble(value.getText().toString());
                f = c * 9 / 5 + 32;    // Celsius => Fahrenheit
            }
            // Load strings from project resources
            degC.setText(String.format("%.1f", c) + getResources().getString(R.string.charC));
            degF.setText(String.format("%.1f", f) + getResources().getString(R.string.charF));
        }else{
            // If the value of the input box is empty, the data will be cleared
            degC.setText(getResources().getString(R.string.charC));
            degF.setText(getResources().getString(R.string.charF));
        }
    }
}