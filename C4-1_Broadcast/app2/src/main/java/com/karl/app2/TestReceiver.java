package com.karl.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TestReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Get the received data
        String str = intent.getExtras().getString("count");
        MainActivity.txt.setText("Đã nhận: " + str);//Display received data
    }
}
