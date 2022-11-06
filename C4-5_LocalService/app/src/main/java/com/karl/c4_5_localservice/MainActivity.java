package com.karl.c4_5_localservice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_play, btn_pause, btn_stop, btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_exit = findViewById(R.id.btn_exit);
    }

    public void onPlayClick(View v) {
        playMusic();
    }

    public void onPauseClick(View v) {
        pauseMusic();
    }

    public void onStopClick(View v) {
        stopMusic();
    }

    public void onExitClick(View v) {
        exitMusic();
    }

    private MediaPlayer player;

    //play music
    private void playMusic() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.music);
        }
        player.start();
    }

    //Pause
    private void pauseMusic() {
        if(player != null && player.isPlaying()){
            player.pause();
        }
    }

    //Stop
    private void stopMusic() {
        if(player != null){
            player.stop();
            player.reset(); //reset to initial
            player.release();//free resources
            player = null;
        }
    }

    //Exit
    private void exitMusic() {
        stopMusic();
        finish();
    }
}