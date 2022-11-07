package com.karl.c52_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txt1, txt2;
    TextView txt3;
    JSONObject p1, p2, p3;
    JSONArray weather;
    // json parsing data：https://www.cnblogs.com/chengmuzhe/p/10596922.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (EditText) findViewById(R.id.editText);
        txt2 = (EditText) findViewById(R.id.editText2);
        txt3 = (TextView) findViewById(R.id.txt3);

        try {
            p1 = new JSONObject("{\"Thành phố\":\"Hà Nội\", \"Nhiệt độ\":\"19-23 Độ, Mây vừa\"}");
            p2 = new JSONObject("{\"Thành phố\":\"Bắc Ninh\", \"Nhiệt độ\":\"22-24 ，Mưa nhỏ\"}");
            p3 = new JSONObject("{\"Thành phố\":\"Bắc Giang\", \"Nhiệt độ\":\"25-30 Độ，Nắng\"}");
            weather = new JSONArray();
            weather.put(p1);
            weather.put(p2);
            weather.put(p3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setJsonData() {
        try {
            JSONObject test = new JSONObject();
            test.put("Thành phố", "Thâm Quyến");
            test.put("Nhiệt độ", 30);
            String jc = test.getString("Thành phố");
            txt1.setText(jc);
            int jw = test.getInt("Nhiệt độ");
            txt2.setText(Integer.toString(jw));
        } catch (JSONException e) {
        }
    }

    void setandgetArrayData() {
        try {
            String jc, jw;
            int length = weather.length();
            for (int i = 0; i < length; i++) {    //iterate over JSONArray
                JSONObject jsonObject = weather.getJSONObject(i);
                jc = jsonObject.getString("Thành phố") + ": ";
                jw = jsonObject.getString("Nhiệt độ") + "\n";
                txt3.append(jc + jw);
            }
        } catch (JSONException e) {
        }
    }

    public void onMyClick(View v) {
        if (v.getId() == R.id.button) {
            setJsonData();
        } else {
            setandgetArrayData();
        }
    }
}