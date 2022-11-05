package com.karl.c2_5_data;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button.findViewById(R.id.button2);

        TextView tv = (TextView)findViewById(R.id.textView);
        Uri uri = getIntent().getData();
        String str = uri.getHost();
        tv.setText(tv.getText() + ",data：" + str);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
