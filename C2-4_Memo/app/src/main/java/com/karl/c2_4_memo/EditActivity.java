package com.karl.c2_4_memo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    TextView txv;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Edit title
        getSupportActionBar().setTitle("Edit memo");

        //Get the incoming Intent object data
        Intent it = getIntent();
        int no = it.getIntExtra("Numbering", 0);    // Read the Int data named "number", or return 0 if there is none
        String s = it.getStringExtra("Memo");  //Read out String data named "memo"

        txv = findViewById(R.id.textView);
        txv.setText(no + ".");//Display the number in the upper left corner of the screen
        edt = findViewById(R.id.editText);
        if (s.length() > 3) {
            //Remove the first 3 words from the incoming memo data, and then fill in the editing component
            edt.setText(s.substring(3));
        }
    }

    //When the save button is clicked
    public void onSave(View v) {
        //Get the filled data and put it in Extra and return
        Intent it = new Intent();
        String msg = txv.getText().toString() + edt.getText().toString();
        it.putExtra("Memo", msg);
        String s = it.getStringExtra("Memo");  //Read out String data named "memo"
        //The first parameter: when the Activity ends, the resultCode will be returned to onActivityResult(), usually RESULT_CANCELED, RESULT_OK.
        //The second parameter: an Intent object, the data returned to the parent Activity.
        setResult(RESULT_OK, it);
        finish();    //return
    }

    //When the cancel button is clicked
    public void onCancel(View v) {
        setResult(RESULT_CANCELED);
        finish();    //return
    }
}
