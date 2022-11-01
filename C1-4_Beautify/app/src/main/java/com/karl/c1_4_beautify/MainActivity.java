package com.karl.c1_4_beautify;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText fName, sName, phone;
    TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the title bar
        // When activity inherits AppCompatActivity by default
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        fName = findViewById(R.id.firstName);
        sName = findViewById(R.id.surName);
        phone = findViewById(R.id.phone);
        txv = findViewById(R.id.txv);

        fName.setTextColor(Color.rgb(255,0,255));
        sName.setTextColor(Color.rgb(127, 255, 0));
        txv.setTextColor(Color.RED);
    }

    public void myClick(View view) {
        txv.setText(fName.getText().toString() + sName.getText().toString() + "'s phone is " + phone.getText());
        Toast.makeText(this, "This is the Button response specified in xml", Toast.LENGTH_LONG).show();
    }
}