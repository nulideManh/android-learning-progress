package com.karl.c2_9_alert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
                        implements DialogInterface.OnClickListener {

    TextView txv; // Document the default TextView component

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.answer); // Find out the default TextView component
    }

    public void myClick(View v) {
        Dialog dialog = new AlertDialog.Builder(this) // Create a Builder object
                .setMessage("Do you like android phones？") // set display information
                .setCancelable(false) // Disable the return key to close the dialog
                .setIcon(android.R.drawable.ic_menu_edit) // Use built-in icons
                .setTitle("Android Questionnaire") // Set the title of the dialog
                .setPositiveButton("like", (DialogInterface.OnClickListener) this)  // Add a negative button
                .setNegativeButton("Hate", (DialogInterface.OnClickListener) this)    // Add affirmative button
                .setNeutralButton("No comment", (DialogInterface.OnClickListener) this) // Don't listen to button events
                .show();// Show dialog

        //Control popup position
        //Window win = dialog.getWindow();
        //win.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) { // If you press affirmative "Like"
            txv.setText("You like android phone");
        } else if (which == DialogInterface.BUTTON_NEGATIVE) { // If you press the negative "hate"
            txv.setText("You hate android phones?");
        }else{
            txv.setText("You have no opinion on Android phones！");
        }
    }
}