package com.karl.c2_3_implicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    public static final String ACTION_SECOND= "com.karl.c2_3_implicitintent.intent.action.SECOND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.setAction("com.karl.c2_3_implicitintent.intent.action.SECOND");
                intent.setAction(MainActivity.ACTION_SECOND);
                startActivity(intent);
            }
        });
    }

}