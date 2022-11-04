package com.karl.c2_1_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void myClick(View v) {
        //Method 3 Constructor
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}