package com.karl.app2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    private static final String TAG = "MusicService";

    public MusicService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        Log.d(TAG, "onStartCommand: action=" + action);
        if ("play".equals(action)) {
            playMusic();
        } else if ("pause".equals(action)) {
            pauseMusic();
        } else if ("stop".equals(action)) {
            stopMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 服务退出时停止音乐
        stopMusic();
    }

    private MediaPlayer player;

    //播放音乐
    private void playMusic() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.music);
        }
        player.start();
    }

    //暂停播放
    private void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    //结束播放
    private void stopMusic() {
        if (player != null) {
            player.stop();  //停止
            player.reset(); //重置到初始
            player.release();//释放资源
            player = null;
        }
    }
}

