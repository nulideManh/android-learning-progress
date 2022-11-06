package com.karl.c4_1_broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static TextView txt;
    int num, numouter;;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
    }
    //Application internal components receive messages
    public void myClickInner(View v)
    {
        Intent intent = new Intent();
        intent.setAction("inner");//Set the action attribute value
        //Android 8 Sau đó, đặt tên gói của receiver để nhận broadcast
        intent.setPackage("com.karl.c4_1_broadcast");
        num++;
        String msg="Internal broadcast information "+Integer.toString(num)+" times sent";
        intent.putExtra("count",msg);
        sendBroadcast(intent);//send broadcast message

        Toast.makeText(this,"Send：" + msg, Toast.LENGTH_SHORT).show();
    }
    //External application receives messages
    public void myClickOuter(View v)
    {
        Intent intent = new Intent();
        intent.setAction("outer");//Set the action attribute value
        //Android 8 Sau đó, đặt tên gói của receiver để nhận broadcast
        intent.setPackage("com.karl.app2");
        numouter++;
        String msg="External broadcast information "+Integer.toString(numouter)+" times sent";
        intent.putExtra("count",msg);
        sendBroadcast(intent);//send broadcast message

        Toast.makeText(this,"Send：" + msg,Toast.LENGTH_SHORT).show();
    }
}