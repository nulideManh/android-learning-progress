package com.karl.c1_5_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener
{
        Button btn;
        EditText login_id,login_password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.check_view);
            setContentView(R.layout.login_view);
            //setContentView(R.layout.text_view);
            //set text color
            //TextView txt = (TextView) findViewById(R.id.title);
            //txt.setTextColor(Color.BLUE);

            login_id = (EditText) findViewById(R.id.login_id);
            login_password = (EditText) findViewById(R.id.login_password);
            btn = (Button) findViewById(R.id.login_button);
            btn.setOnClickListener((View.OnClickListener) this);
        }

    public void myClick(View view){
        String  str = login_id.getText()+" "+login_password.getText();
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        String  str = login_id.getText()+" "+login_password.getText();
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

//    public void checkButtonClick(View view){
//
//        TextView txv =  findViewById(R.id.txv);
//        RadioGroup rg = findViewById(R.id.rg);
//
//        String str = "";
//        // Display different information by selected option id
//        switch (rg.getCheckedRadioButtonId()) {
//            case R.id.rb1:   // C++
//                str = "C++";
//                break;
//            case R.id.rb2:   // C
//                str = "C";
//                break;
//            case R.id.rb3:  // Java
//                str = "Java";
//                break;
//            case R.id.rb4:  // C#
//                txv.setText("C#");
//                str = "C#";
//                break;
//        }
//        // Use an array to store the IDs of all CheckBox components
//        CheckBox chk;
//        int[] id = {R.id.cb_st, R.id.cb_jc, R.id.cb_xt, R.id.cb_xhx};
//        for (int i : id) {    // Check whether each CheckBox is selected one by one in a loop
//            chk = (CheckBox) findViewById(i);
//            if (chk.isChecked()) {
//                // If selected, appends the newline character and option text to the msg string
//                str += ", " + chk.getText();
//            }
//        }
//        txv.setText("Selected is：" + str);
//    }
}