package com.karl.c53_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name ,tel;
    private SharedPreferences sp ; //Khai báo đối tượng SharedPreferenced

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name= (EditText) findViewById(R.id.name);
        tel = (EditText) findViewById(R.id.tel);
    }

    public void onMyClick(View view) {
        //Khai báo SharedPreferences object
        sp = getSharedPreferences("phoneBook", Context.MODE_PRIVATE);
        switch (view.getId()){
            case R.id.btn_Save:
                //Get the edit object
                SharedPreferences.Editor edit = sp.edit();
                //Write data through edit object
                edit.putString("name",name.getText().toString().trim());
                edit.putString("phone",tel.getText().toString().trim());
                //Submit data into XML file
                edit.commit();
                Toast.makeText(this, "Successfully saved！", Toast.LENGTH_LONG);
                break;
            case R.id.btn_Get:
                /*Lấy dữ liệu ra, tham số thứ 2 của getString() là giá trị mặc định,
                * nếu không tồn tại key sẽ trả về giá trị mặc định
                */
                name.setText(sp.getString("name","Null"));
                tel.setText(sp.getString("phone","Null"));
                break;
        }
    }
}