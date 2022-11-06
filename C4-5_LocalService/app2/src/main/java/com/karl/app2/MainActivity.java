package com.karl.app2;

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

    public void onPlayClick(View v) {
        Intent intent  = new Intent(this, MusicService.class);
        intent.putExtra("action", "play");
        startService(intent);
    }

    public void onPauseClick(View v) {
        Intent intent  = new Intent(this, MusicService.class);
        intent.putExtra("action", "pause");
        startService(intent);
    }

    public void onStopClick(View v) {
        Intent intent  = new Intent(this, MusicService.class);
        intent.putExtra("action", "stop");
        startService(intent);
    }

    public void onExitClick(View v) {
        Intent intent  = new Intent(this, MusicService.class);
        stopService(intent);
        finish();
    }
}