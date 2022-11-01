package com.karl.c1_2_linearlayout;

import androidx.appcompat.app.AppCompatActivity;

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
        // hide title bar
        // When activity inherits AppCompatActivity by default
        getSupportActionBar().hide();

        //When activity inherits Activity by default requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Initialize variables
        fName = findViewById(R.id.firstName);
        sName = findViewById(R.id.surName);
        phone = findViewById(R.id.phone);
        txv = findViewById(R.id.txv);
    }
    public void myClick(View view) {
        txv.setText(fName.getText().toString() + " " +sName.getText().toString() + " 's phone is " + phone.getText());
        Toast.makeText(this, "This is button response specified in xml", Toast.LENGTH_LONG).show();
    }

}