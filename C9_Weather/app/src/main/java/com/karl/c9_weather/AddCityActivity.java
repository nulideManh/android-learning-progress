package com.karl.c9_weather;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AddCityActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Context mContext;                   //current Activity context
    private ListView listView;                  //ListView control
    private ArrayAdapter<String> arr_adapter;   //ListView Adapter
    private int ItemClickState = 0;             //ListView state is used to implement the corresponding multi-layer ListView
    private String[] arr_data;                  //Adapter for ListView
    private String[] code_data;                 //The city code used to store the selected city API requires a city code
    private JSONObject jsonObject;              //Used to manipulate JSON data in citycode.json
    private JSONArray jsonArray;                //Used to manipulate JSON data in citycode.json
    private Intent intentGet;                   //Used to get the cityName passed in the Intent
    private String lastCityName;                //Used to save the cityName passed in the Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        mContext = AddCityActivity.this;
        //Get the cityName passed by the main interface
        intentGet = getIntent();
        lastCityName = intentGet.getStringExtra("cityName");

        //Bind the ListView control
        listView = (ListView) findViewById(R.id.list_view);

        //Parse citycode.json
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("citycode.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonObject = new JSONObject(stringBuilder.toString());
            jsonArray = jsonObject.getJSONArray("zone");
            //Đi qua tất cả các tỉnh và lưu trữ tất cả tên tỉnh trong arr_data[]
            arr_data = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                arr_data[i] = jsonObject.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Hiển thị tất cả tên tỉnh trong ListView
        arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr_data);
        listView.setAdapter(arr_adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Nếu nhấp chuột vào, ItemClickState == 0, hãy nhập menu cấp hai. Menu cấp hai hiển thị tên thành phố của tỉnh đã chọn
        if (ItemClickState == 0) {
            try {
                //Nhận dữ liệu JSON của tỉnh đã chọn
                jsonObject = jsonArray.getJSONObject(position);
                jsonArray = jsonObject.getJSONArray("zone");
                //Sau khi phân tích cú pháp, hãy lưu trữ tên thành phố trong arr_data
                arr_data = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    arr_data[i] = jsonObject.getString("name");
                }
                //Refresh adapter content
                arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr_data);
                listView.setAdapter(arr_adapter);
                //Lưu ý rằng không thể sử dụng InformDataSetChanged để làm mới dữ liệu ở đây,
                // vì arr_data tạo lại đối tượng và địa chỉ bộ nhớ đã thay đổi.
                // Tham khảo：https://blog.csdn.net/ssq236811/article/details/103324084
                //arr_adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Listener goes to next level
            ItemClickState++;
        } else if (ItemClickState == 1) {
            //Nếu ItemClickState == 1, cú nhấp chuột để vào menu cấp ba Menu cấp ba hiển thị tên khu vực của thành phố đã chọn
            try {
                //Nhận dữ liệu JSON của thành phố đã chọn
                jsonObject = jsonArray.getJSONObject(position);
                jsonArray = jsonObject.getJSONArray("zone");
                //Sau khi phân tích cú pháp, hãy lưu tên khu vực vào arr_data[] ,
                // Mã thành phố của vùng được đưa vào code_data[]
                arr_data = new String[jsonArray.length()];
                code_data = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    arr_data[i] = jsonObject.getString("name");
                    code_data[i] = jsonObject.getString("code");
                }
                //Refresh adapter content
                arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr_data);
                listView.setAdapter(arr_adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Listener goes to next level
            ItemClickState++;
        } else if (ItemClickState == 2) {
            //Nếu ItemClickState == 2, nhận được arr_data [i] sau khi nhấp, giá trị của code_data [i] được cập nhật vào cơ sở dữ liệu, i là mục đã chọn, tức là arg2
            try {
                //Nhận giá trị của arr_data [i], code_data [i]
                String cityName, cityCode;
                cityName = arr_data[position];
                cityCode = code_data[position];
                MyDBHelper myDBHelper = new MyDBHelper(mContext, "my.db", null, 1);
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                //Sửa đổi câu lệnh SQL
                String sql = "update user set cityName='" + cityName + "' where cityName ='" + lastCityName + "'";
                //execute SQL
                db.execSQL(sql);
                sql = "update user set cityCode='" + cityCode + "' where cityName = '" + cityName + "'";
                //execute SQL
                db.execSQL(sql);
                db.close();
                //Chuyển đến giao diện chính
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

