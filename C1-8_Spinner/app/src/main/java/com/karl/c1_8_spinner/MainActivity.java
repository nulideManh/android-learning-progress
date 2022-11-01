package com.karl.c1_8_spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txv;
    Spinner cinema;    // Cinema list object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView)findViewById(R.id.txv);
        cinema = (Spinner) findViewById(R.id.cinema);
    }

    public void order(View v){
        // Get an array of strings in a string resource
        String[] cinemas = getResources().getStringArray(R.array.cinemas);
        int index = cinema.getSelectedItemPosition();    // Get the selected option
        txv.setText("Order "+cinemas[index]+" ticket");     // Show selected options
    }
}