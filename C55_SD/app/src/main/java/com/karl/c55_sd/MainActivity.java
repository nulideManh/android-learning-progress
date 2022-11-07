package com.karl.c55_sd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Quyền đọc và ghi thẻ SD bên ngoài
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
    TextView txt;
    static final String fileName = "test.txt";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        //Nhận quyền đọc và ghi
        verifyStoragePermissions(this);
    }

    public void onSaveClick(View v) {
        //Get external devices
        String state= Environment.getExternalStorageState();
        String str = getString(R.string.message);
        FileOutputStream f_out;
        if(state.equals(Environment.MEDIA_MOUNTED)){
            //Get SD card directory
            File sdPath = Environment.getExternalStorageDirectory();
            File file = new File(sdPath, fileName);
            try{
                f_out = new FileOutputStream(file);
                f_out.write(str.getBytes());
                txt.setText("Save successfully, file name：" + fileName + "\nFile path：" + sdPath + "\nDocument content：\n" + str);
                f_out.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onGetClick(View arg0) {
        String state = Environment.getExternalStorageState();
        String str;
        byte[] buffer = new byte[1024];
        FileInputStream in_file = null;
        if(state.equals(Environment.MEDIA_MOUNTED)){
            File sdPath = Environment.getExternalStorageDirectory();
            File file = new File(sdPath,fileName);
            try{
                in_file =new FileInputStream(file);
                int bytes=in_file.read(buffer);
                str = new String(buffer, 0, bytes);
                txt.setText("\nRead successfully, filename：" + fileName + "\nFile path：" + sdPath + "\nDocument content：\n" + str);
                in_file.close();
            }catch (FileNotFoundException e) {
                System.out.print("File does not exist");
            } catch (IOException e) {
                System.out.print("IO stream error");
            }
        }
    }
}