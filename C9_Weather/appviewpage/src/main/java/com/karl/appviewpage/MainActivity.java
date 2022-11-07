package com.karl.appviewpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2 = findViewById(R.id.vp_h);
        VpAdapter adapter = new VpAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}