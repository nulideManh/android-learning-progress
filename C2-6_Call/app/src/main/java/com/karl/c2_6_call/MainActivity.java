package com.karl.c2_6_call;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnCall, btnEmail, btnWeb, btnSearch, btnSms, btnMap;
    public static  final  String ACTION_SECOND= "com.karl.c2_6_call.intent.action.SECOND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void callClick(View view) {
        //Common system uri
        //https://blog.csdn.net/chengkaizone/article/details/20639973
        Uri uri = Uri.parse("tel:0785100501");//
        Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(uri);
        startActivity(intent);
    }
    public void webClick(View view) {
        Uri uri = Uri.parse("https://www.google.com/");
        Intent intent = new Intent();
        intent.setData(uri);
        startActivity(intent);
    }
    public void emailClick(View view) {
        //Common system uri
        //https://blog.csdn.net/chengkaizone/article/details/20639973
        Uri uri = Uri.parse("mailto:manh.nv1005@gmail.com");//
        Intent intent = new Intent();
        intent.setData(uri);
        intent.putExtra(Intent.EXTRA_CC, new String[]{"manh.nv1005@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"data subject");
        intent.putExtra(Intent.EXTRA_TEXT,"Hello，\n Mail received，Thanks！");
        intent.setData(uri);
        startActivity(intent);
    }
    public void smsClick(View view) {
        Uri uri = Uri.parse("sms:0785100501?body=Hello！");
        Intent intent = new Intent();
        intent.setData(uri);
        startActivity(intent);
    }
    public void searchClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,"fb");
        startActivity(intent);
    }
    public void mapClick(View view) {
        Uri uri = Uri.parse("geo:39.896086,116.151147");
        Intent intent = new Intent();
        intent.setData(uri);
        startActivity(intent);
    }
}