package com.karl.c2_5_data;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tv = (TextView)findViewById(R.id.textView);
        Uri uri = getIntent().getData();
        String str = uri.getHost();
        tv.setText(tv.getText() + ",data：" + str);
    }

    public void myClick(View v) {
        //Back
        finish();
    }
}
