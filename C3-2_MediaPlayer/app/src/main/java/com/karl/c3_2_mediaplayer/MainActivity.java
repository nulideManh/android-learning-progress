package com.karl.c3_2_mediaplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

//Implement three event listener interfaces of MediaPlayer
public class MainActivity extends AppCompatActivity
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private static final String TAG = "MainActivity";

    Uri uri;      //Uri for storing video files
    TextView txvName, txvUri;   //Component referenced in the picture
    boolean isVideo = false;   //Whether the record is a video file
    Button btnPlay, btnStop;    //Used to refer to the play button, stop button
    CheckBox ckbLoop;          //Used to refer to the repeat play multi-select button
    MediaPlayer mper;          //Used to refer to the MediaPlayer object
    Toast tos;                 //Used to refer to the Toast object (for displaying information)

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        //request for access
        verifyStoragePermissions(this);

        setContentView(R.layout.activity_main);
        //Đặt màn hình không xoay theo điện thoại và giao diện hiển thị theo chiều dọc
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);   //Đặt màn hình không xoay theo điện thoại
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //Đặt màn hình hiển thị theo chiều dọc
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //Đặt màn hình không chuyển sang chế độ ngủ

        txvName = findViewById(R.id.txvName);   //reference to the first literal component
        txvUri = findViewById(R.id.txvUri);     //reference to the second text component
        btnPlay = findViewById(R.id.btnPlay);     //Reference to play button
        btnStop = findViewById(R.id.btnStop);     //reference to stop button
        ckbLoop = findViewById(R.id.ckbLoop);   //Reference to repeat play multi-select button
        //Theo mặc định, các tệp nhạc trong chương trình sẽ được phát
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome);
        txvName.setText("welcome.mp3");           //Hiển thị tên tệp trên màn hình
        txvUri.setText("Các bài hát trong chương trình：" + uri.toString());   //Hiển thị Uri
        mper = new MediaPlayer();           //Tạo một đối tượng MediaPlayer
        mper.setOnPreparedListener(this);  //Set up 3 event listeners
        mper.setOnErrorListener(this);
        mper.setOnCompletionListener(this);
        tos = Toast.makeText(this, "", Toast.LENGTH_SHORT);//Create Toast object
        prepareMusic();     //Preparing for playback of music (welcome.mp3)
    }

    //Prepare data
    void prepareMusic() {
        btnPlay.setText("播放");           //Return button text to "play"
        btnPlay.setEnabled(false);             //Make the play button unpressable (wait until it is ready)
        btnStop.setEnabled(false);             //make the stop button unpressable
        try {
            mper.reset();            //Nếu một bài hát đã được phát trước đó, nó phải được đặt lại trước khi thay đổi bài hát
            mper.setDataSource(this, uri);     //Ghi rõ nguồn của bài hát
            mper.setLooping(ckbLoop.isChecked());    //Đặt có phát lặp lại hay không
            mper.prepareAsync();                //Yêu cầu MediaPlayer chuẩn bị phát nhạc được chỉ định
        } catch (Exception e) {        //Chặn lỗi và hiển thị thông tin
            tos.setText("Lỗi khi chỉ định tệp nhạc！" + e.toString());
            tos.show();
            Log.e(TAG, "Lỗi khi chỉ định tệp nhạc！" + e.toString());
            btnPlay.setEnabled(true);
            btnStop.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if (mper.isPlaying()) {   //Tạm dừng nếu nó đang phát
            btnPlay.setText("Tiếp tục");
            mper.pause();  //Tạm dừng phát lại
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        mper.release();//Giải phóng đối tượng MediaPlayer
        super.onDestroy();
    }

    //Chọn tệp
    public void onPick(View v) {
        //Set filter file type https://blog.csdn.net/DayDayPlayPhone/article/details/52216055
        //intent.setType(“image/*”);//Select Image
        //intent.setType(“audio/*”); //select audio
        //intent.setType(“video/*”); //select video （mp4 3gp is the video format supported by android）
        //intent.setType(“video/*;image/*”);//Select video and picture at the same time
        //intent.setType("*/*");//No type restrictions
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);  //Create an Intent with the action "Select Content"
        it.addCategory(Intent.CATEGORY_OPENABLE);
        if (v.getId() == R.id.btnPickAudio) {    //If it's the "Choose Song" button
            it.setType("audio/*");   //To select all music types
            startActivityForResult(it, 100);  //Start an external program with identification number 100
        } else {     //Otherwise, the "Choose Video" button
            it.setType("video/*"); //To select all video types
            startActivityForResult(it, 101);  //Start external program with identification number 101
        }
    }

    //Đặt lại tệp đã chọn
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                //Nếu lựa chọn thành công
                isVideo = (requestCode == 101);       //Ghi lại xem tệp video có được chọn hay không（Khi mã nhận dạng là 101）
            }
            Log.i(TAG, "before convertUri: " + data.getData().toString());
            String path = FileChooseUtil.getInstance(this).getChooseFileResultPath(data.getData());
            Log.i(TAG, "onActivityResult: path=" + path);
            uri = Uri.parse("file://" + path);   //Chuyển đổi đường dẫn sang Uri
            Log.i(TAG, "end convertUri: " + uri.toString());
            txvName.setText(uri.getLastPathSegment());   //Hiển thị tên tệp (văn bản cuối cùng của Uri)
            txvUri.setText("文件位置：" + uri.getPath());   //Hiển thị đường dẫn của tệp
            if (!isVideo) {
                //Chuẩn bị dữ liệu phát lại nhạc
                prepareMusic();
            }
        } else {
            Toast.makeText(this, "Không tài liệu nào được chọn！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onPrepared: ");
        btnPlay.setEnabled(true);        //Khi đã sẵn sàng, hãy làm cho nút phát hoạt động (có thể nhấp được)
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.i(TAG, "onError: ");
        tos.setText("Đã xảy ra lỗi, dừng phát lại");    //Khi xảy ra lỗi, hiển thị thông báo lỗi
        tos.show();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onCompletion: ");
        //Sau khi âm nhạc được phát
        mper.seekTo(0);           //trả lại vị trí phát lại 0
        btnPlay.setText("播放");         //Đặt nút phát có nội dung "phát"
        btnStop.setEnabled(false);       //Tắt nút dừng (không bấm được) vì nhạc đã ngừng phát
    }

    //Khi nút phát được nhấp vào
    public void onMpPlay(View v) {
        if (!isVideo) {
            //chơi nhạc
            if (mper.isPlaying()) {  //Tạm dừng nếu nó đang phát
                mper.pause();  //Tạm dừng phát lại
                btnPlay.setText("tiếp tục");
            } else {   //Nếu không chơi, hãy bắt đầu chơi
                mper.start();   //Bắt đầu cuộc chơi
                btnPlay.setText("tạm ngừng");
                btnStop.setEnabled(true);
            }
        } else {
            //Phát video. Tạo intent bắt đầu Hoạt động video và thêm Tên của video vào intent với tên "uri"
            Intent it = new Intent(this, VideoActivity.class);
            it.putExtra("uri", uri.toString());
            startActivity(it);
        }
    }

    //Khi nút dừng được nhấp vào
    public void onMpStop(View v) {
        mper.pause();  //Tạm dừng phát lại
        mper.seekTo(0);//Di chuyển vị trí 0 giây trong bản nhạc
        btnPlay.setText("chơi");
        btnStop.setEnabled(false);
    }

    //Khi nhấp vào nút chơi nhiều lựa chọn, đặt vòng lặp để chơi
    public void onMpLoop(View v) {
        if (ckbLoop.isChecked())
            mper.setLooping(true);  //Đặt để lặp lại
        else
            mper.setLooping(false); //thiết lập không lặp lại
    }

    //Khi nhấp vào nút đồ thị lùi
    public void onMpBackward(View v) {  //Khi nhấp vào nút đồ thị lùi
        if (!btnPlay.isEnabled()) return;   //Nếu chưa sẵn sàng (không thể nhấn nút phát), không xử lý
        int len = mper.getDuration();  //đọc thời lượng âm nhạc
        int pos = mper.getCurrentPosition();  //Đọc vị trí phát hiện tại
        pos -= 10000;  //quay lại 10 giây
        if (pos < 0) pos = 0;  //Không được nhỏ hơn 0
        mper.seekTo(pos);    //Di chuyển vị trí phát lại
        tos.setText("Quay lại 10 giây:" + pos / 1000 + "/" + len / 1000);   //Hiển thị thông tin
        tos.show();
    }

    //Khi nhấp vào nút biểu đồ chuyển tiếp
    public void onMpForward(View v) {
        if (!btnPlay.isEnabled()) return;   //Nếu chưa sẵn sàng (không thể nhấn nút phát), không xử lý
        int len = mper.getDuration();  //đọc thời lượng âm nhạc
        int pos = mper.getCurrentPosition();   //Đọc vị trí phát hiện tại
        pos += 10000;   //chuyển tiếp 10 giây
        if (pos > len) pos = len;
        mper.seekTo(pos);//Di chuyển vị trí phát lại
        tos.setText("Chuyển tiếp 10 giây:" + pos / 1000 + "/" + len / 1000);
        tos.show();
    }
}