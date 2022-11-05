package com.karl.c2_8_toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3;
    Toast toast;
    LinearLayout toastView;
    ImageView imageCodeProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn1) {
            Toast.makeText(getApplicationContext(),
                    "Default Toast mode",
                    Toast.LENGTH_SHORT).show();
        } else if (view == btn2) {
            toast = Toast.makeText(getApplicationContext(),
                    "Customize the location of the Toast",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            // failure
        } else if (view == btn3) {
            toast = Toast.makeText(getApplicationContext(),
                    "Toast with Icon",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 80);
            toastView = (LinearLayout) toast.getView();
            imageCodeProject = new ImageView(this);
            imageCodeProject.setImageResource(R.drawable.icon);
            toastView.addView(imageCodeProject, 0);
            toast.show();
        }
    }
}