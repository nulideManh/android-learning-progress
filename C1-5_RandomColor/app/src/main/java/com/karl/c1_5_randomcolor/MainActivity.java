package com.karl.c1_5_randomcolor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txvR,txvG,txvB;
    View colorBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set title
        setTitle("Get Random Color");

        //Get 3 TextView objects and LinearLayout at the bottom of the screen
        txvR = findViewById(R.id.txvR);
        txvG = findViewById(R.id.txvG);
        txvB = findViewById(R.id.txvB);
        colorBlock = findViewById(R.id.colorBlock);
    }

    public void changeColor(View view)
    {
        // Get a random number object and generate 3 random numbers (rgb values)
        Random x = new Random();
        int red = x.nextInt(256);//take 0~random number between 255
        txvR.setText("Red：" + red);//Display random numbers
        txvR.setTextColor(Color.rgb(red,0,0));//Set text to random number color value (red)

        int green = x.nextInt(256);//Take a random number between 0 and 255
        txvG.setText("Green：" + green);//Display random numbers
        txvG.setTextColor(Color.rgb(0,green,0));//Set text to random number color value (green)

        int blue = x.nextInt(256);//Take a random number between 0 and 255
        txvB.setText("Blue：" + blue);//Display random numbers
        txvB.setTextColor(Color.rgb(0,0,blue));//Set text to random number color value (blue)

        //Set the background color of the blank LinearLayout at the bottom of the screen
        colorBlock.setBackgroundColor(Color.rgb(red,green,blue));
    }
}