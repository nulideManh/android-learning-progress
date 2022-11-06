package com.karl.c4_4_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //material：https://blog.csdn.net/weixin_37730482/article/details/80564018

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Định thời gian: gửi một broadcast sau 15 giây,
     * sau khi broadcast đã nhận, Toast sẽ nhắc rằng hoạt động định thời đã hoàn tất
     */
    public void clickTimingAlam(View v) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("alam");
        //Android 8 After that, set the package name of the receiver to receive the broadcast
        intent.setPackage("com.karl.c4_4_alarm");
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 15);        //Set a time after 15 seconds
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        Toast.makeText(this, "báo thức bật sau 15 giây", Toast.LENGTH_LONG).show();
    }

    /**
     * Xác định vòng lặp: gửi một broadcast sau mỗi 10 giây,
     * sau khi broadcast được nhận, Toast sẽ nhắc rằng hoạt động định thời đã hoàn tất
     */
    public void clickCycleAlam(View v) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("alam");
        //Android 8 Sau đó, đặt tên gói của đầu thu để thu sóng
        intent.setPackage("com.karl.c4_4_alarm");
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        /*  Thời gian bắt đầu  */
        long firstime = SystemClock.elapsedRealtime();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        /*  Chu kỳ 10 giây, liên tục gửi các broadcast   */
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 10 * 1000, sender);
    }

    /**
     * Hủy tin nhắn gửi định kỳ
     */
    public void clickCancel(View v) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("alam");
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.cancel(sender);
    }
}