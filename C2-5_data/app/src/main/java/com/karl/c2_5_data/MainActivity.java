package com.karl.c2_5_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static  final  String ACTION_SECOND= "com.karl.c2_5_data.intent.action.SECOND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myClick(View v) {
        //Common system uri
        //https://blog.csdn.net/chengkaizone/article/details/20639973
        //Uri format： <scheme>://<host>:<port>/<path> That is: <protocol>://<hostname>:<port number>/<path>
        //E.g：http://www.tahlia.com:8888/test
        //call method one
        Uri uri = Uri.parse("http://www.tahlia.com:8888/test");
        Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_SECOND);
        intent.setData(uri);
        startActivity(intent);
        //call method 2
        //Intent intent = new Intent(MainActivity.ACTION_SECOND,uri);
        //startActivity(intent);
    }
}