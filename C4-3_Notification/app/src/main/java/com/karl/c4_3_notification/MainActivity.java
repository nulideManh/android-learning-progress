package com.karl.c4_3_notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtn1Click(View v) {
        showNotification(MainActivity.this);
    }

    public void onBtn2Click(View v) {
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancelAll();
    }

    /**
     * Show a normal notification
     */
    public void showNotification(Context context) {
        //material: https://blog.csdn.net/weixin_42171638/article/details/103406312
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        //Determine if it is 8.0Android.O
        //material: https://blog.csdn.net/BINGXIHEART/article/details/95166513
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel("default","Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            nManager.createNotificationChannel(chan1);
            mBuilder = new NotificationCompat.Builder(context, "default");
        } else {
            mBuilder = new NotificationCompat.Builder(context);
        }
        mBuilder
                /**Đặt biểu tượng lớn ở bên trái thông báo**/
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                /**Đặt biểu tượng nhỏ ở bên phải thông báo**/
                .setSmallIcon(R.mipmap.ic_launcher)
                /**Thông báo xuất hiện trên thanh thông báo lần đầu tiên, với hiệu ứng hoạt ảnh tăng lên**/
                .setTicker("Thông báo đến rồi")
                /**Đặt tiêu đề của thông báo**/
                .setContentTitle("Đây là tiêu đề của một thông báo")
                /**Đặt nội dung của thông báo**/
                .setContentText("Đây là nội dung của một thông báo")
                /**Thời gian thông báo được tạo sẽ được hiển thị trong tin nhắn thông báo**/
                .setWhen(System.currentTimeMillis())
                /**Đặt cờ này để cho phép thông báo tự động bị loại bỏ khi người dùng nhấp vào bảng điều khiển**/
                .setAutoCancel(true)
                /**Đặt nó làm thông báo liên tục. Chúng thường được sử dụng để biểu thị một tác vụ nền mà
                 * người dùng đang tích cực tham gia (ví dụ: phát nhạc) hoặc đang chờ theo một cách nào đó và
                 * do đó chiếm thiết bị (ví dụ: tải xuống tệp, hoạt động đồng bộ, kết nối mạng đang hoạt động)**/
                .setOngoing(false)
                /**Cách dễ nhất và nhất quán nhất để thêm hiệu ứng âm thanh, đèn flash và rung vào thông báo là
                 * sử dụng giá trị mặc định của người dùng hiện tại:**/
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                /**PendingIntent delayed intent**/
                .setContentIntent(PendingIntent.getActivity(context, 1, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
        /**initiate notification**/
        Notification notify = mBuilder.build();
        nManager.notify(0, notify);
    }
}