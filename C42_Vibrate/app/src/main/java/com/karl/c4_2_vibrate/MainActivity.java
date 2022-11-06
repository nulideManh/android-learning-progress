package com.karl.c4_2_vibrate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txv = (TextView)findViewById(R.id.textView1);
        txv.setOnTouchListener(this);//Login touch listener object
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //References：https://www.jianshu.com/p/6d779bc9bfba
        Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            Toast.makeText(this,"Shake for 5 seconds",Toast.LENGTH_SHORT).show();
            //vb.vibrate(5000);//Shake for 5 seconds

            //waveform vibration
            long[] pattern = {0,100,200,300};
            //old method
            //vb.vibrate(pattern,2);
            //new processing method
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(pattern, 2);
            vb.vibrate(vibrationEffect);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            Toast.makeText(this,"Stop shaking",Toast.LENGTH_SHORT).show();
            vb.cancel();//stop shaking
        }
        // Trả về true có nghĩa là xử lý các sự kiện theo dõi tiếp theo, để nhận sự kiện kích hoạt nhả ngón tay và dừng rung
        return true;
    }
}