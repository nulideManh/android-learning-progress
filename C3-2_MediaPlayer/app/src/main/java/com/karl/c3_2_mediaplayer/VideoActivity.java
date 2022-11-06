package com.karl.c3_2_mediaplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";

    VideoView vdv;  //Được sử dụng để tham chiếu đến đối tượng VideoView
    int pos = 0;    //Được sử dụng để ghi lại vị trí dừng phát

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        //Ẩn thanh trạng thái của hệ thống
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //ẩn thanh tiêu đề
        getSupportActionBar().hide();

        setContentView(R.layout.activity_video);
        //Luôn bật màn hình (không tự động chuyển sang chế độ ngủ)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Lấy đối tượng Intent đến và lấy Uri của video để phát
        Intent it = getIntent();
        Uri uri = Uri.parse(it.getStringExtra("uri"));
        if(savedInstanceState != null) {
            //Nếu Hoạt động được khởi động lại do xoay, hãy lấy ra vị trí phát lại được lưu trước khi xoay
            pos = savedInstanceState.getInt("pos", 0);
        }

        vdv = findViewById(R.id.videoView); //Tham chiếu đến thành phần VideoView trong màn hình
        MediaController mediaCtrl = new MediaController(this); //Tạo đối tượng điều khiển phát lại
        vdv.setMediaController(mediaCtrl);  //Đặt để sử dụng đối tượng điều khiển phát lại để điều khiển phát lại
        vdv.setVideoURI(uri);   //Đặt video để phát Uri
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        //Khi Activity bắt đầu hoặc trở lại trạng thái tương tác từ trạng thái tạm dừng
        vdv.seekTo(pos);   //Di chuyển đến vị trí tạm dừng trước đó
        vdv.start();       //start
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        //Khi Activity chuyển sang trạng thái tạm dừng (chẳng hạn như chuyển sang chương trình khác)
        pos = vdv.getCurrentPosition(); //Lưu trữ vị trí phát lại
        vdv.stopPlayback();    //Dừng chơi
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        //Lưu trữ vị trí phát hiện tại thu được khi tạm dừng
        outState.putInt("pos", pos);
    }
}
