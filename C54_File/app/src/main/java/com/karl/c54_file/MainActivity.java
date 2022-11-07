package com.karl.c54_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    static final String fileName = "test.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
    }

    public void onSaveClick(View v) {
        String str = getString(R.string.message);
        FileOutputStream f_out;
        try {
            f_out = openFileOutput(fileName, Context.MODE_PRIVATE);
            f_out.write(str.getBytes());
            txt.setText("Save successfully, file name：" + fileName + "\nDocument content：\n" + str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGetClick(View arg0) {
        String str;
        byte[] buffer = new byte[1024];
        FileInputStream in_file = null;
        try {
            in_file = openFileInput(fileName);
            int bytes = in_file.read(buffer);
            str = new String(buffer, 0, bytes);
            txt.setText("\nRead successfully, filename：" + fileName + "\nDocument content：\n" + str);
        } catch (FileNotFoundException e) {
            System.out.print("File does not exist");
        } catch (IOException e) {
            System.out.print("IO stream error");
        }
    }
}