package com.karl.c2_2_explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MYTAG";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

//    public void myClick(View v) {
//        // Method 1 component instantiation
//        ComponentName cn = new ComponentName(this, "com.example.lean.SecondActivity");
//        Intent intent = new Intent();
//        intent.setComponent(cn);
//        startActivity(intent);
//
//        // Method 2 setClass instantiation
////        Intent intent = new Intent();
////        intent.setClass(this, SecondActivity.class);
////        startActivity(intent);
//
//        // Method 3 Constructor
//        //Intent intent = new Intent(this, SecondActivity.class);
//        //startActivity(intent);
//    }
}