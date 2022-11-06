package com.karl.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ServiceConnection serviceConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStart1Click(View v) {
        //startService start the service
        startService(new Intent(this, LocalService.class));
    }

    public void onStop1Click(View v) {
        // stopService stop service
        stopService(new Intent(this, LocalService.class));
    }

    public void onStart2Click(View v) {
        if (serviceConnection == null) {
            //serviceConnection instantiate, create the object
            Toast.makeText(this, "Service binding ServiceConnection instantiation", Toast.LENGTH_SHORT).show();
            serviceConnection = new ServiceConnection() {
                //Implement the onServiceConnected method
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    Log.i(TAG, "onServiceConnected: ");
                }

                //Implement the onServiceDisconnected method
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.i(TAG, "onServiceDisconnected: ");
                }
            };
        } else {
            Toast.makeText(this, "Service is bound", Toast.LENGTH_SHORT).show();
        }
        //Bind service
        //1 show call
        Intent intent = new Intent(this, LocalService.class);
        //2 Implicit call，After Android 5.0, directly and implicitly declaring the Intent to start the Service throws an exception, refer to：https://blog.csdn.net/l2show/article/details/47421961
        //Intent intent = new Intent();
        //intent.setAction("com.example.lean.service.action.LocalService");
        //intent.setPackage("com.example.lean");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onStop2Click(View v) {
        // After the Service is bound, it can be unbound
        if (serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        } else {
            Toast.makeText(this, "Service not started", Toast.LENGTH_SHORT).show();
        }
    }
}