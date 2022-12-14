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
            //??i qua t???t c??? c??c t???nh v?? l??u tr??? t???t c??? t??n t???nh trong arr_data[]
            arr_data = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                arr_data[i] = jsonObject.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Hi???n th??? t???t c??? t??n t???nh trong ListView
        arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr_data);
        listView.setAdapter(arr_adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //N???u nh???p chu???t v??o, ItemClickState == 0, h??y nh???p menu c???p hai. Menu c???p hai hi???n th??? t??n th??nh ph??? c???a t???nh ???? ch???n
        if (ItemClickState == 0) {
            try {
                //Nh???n d??? li???u JSON c???a t???nh ???? ch???n
                jsonObject = jsonArray.getJSONObject(position);
                jsonArray = jsonObject.getJSONArray("zone");
                //Sau khi ph??n t??ch c?? ph??p, h??y l??u tr??? t??n th??nh ph??? trong arr_data
                arr_data = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    arr_data[i] = jsonObject.getString("name");
                }
                //Refresh adapter content
                arr_adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr_data);
                listView.setAdapter(arr_adapter);
                //L??u ?? r???ng kh??ng th??? s??? d???ng InformDataSetChanged ????? l??m m???i d??? li???u ??? ????y,
                // v?? arr_data t???o l???i ?????i t?????ng v?? ?????a ch??? b??? nh??? ???? thay ?????i.
                // Tham kh???o???https://blog.csdn.net/ssq236811/article/details/103324084
                //arr_adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Listener goes to next level
            ItemClickState++;
        } else if (ItemClickState == 1) {
            //N???u ItemClickState == 1, c?? nh???p chu???t ????? v??o menu c???p ba Menu c???p ba hi???n th??? t??n khu v???c c???a th??nh ph??? ???? ch???n
            try {
                //Nh???n d??? li???u JSON c???a th??nh ph??? ???? ch???n
                jsonObject = jsonArray.getJSONObject(position);
                jsonArray = jsonObject.getJSONArray("zone");
                //Sau khi ph??n t??ch c?? ph??p, h??y l??u t??n khu v???c v??o arr_data[] ,
                // M?? th??nh ph??? c???a v??ng ???????c ????a v??o code_data[]
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
            //N???u ItemClickState == 2, nh???n ???????c arr_data [i] sau khi nh???p, gi?? tr??? c???a code_data [i] ???????c c???p nh???t v??o c?? s??? d??? li???u, i l?? m???c ???? ch???n, t???c l?? arg2
            try {
                //Nh???n gi?? tr??? c???a arr_data [i], code_data [i]
                String cityName, cityCode;
                cityName = arr_data[position];
                cityCode = code_data[position];
                MyDBHelper myDBHelper = new MyDBHelper(mContext, "my.db", null, 1);
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                //S???a ?????i c??u l???nh SQL
                String sql = "update user set cityName='" + cityName + "' where cityName ='" + lastCityName + "'";
                //execute SQL
                db.execSQL(sql);
                sql = "update user set cityCode='" + cityCode + "' where cityName = '" + cityName + "'";
                //execute SQL
                db.execSQL(sql);
                db.close();
                //Chuy???n ?????n giao di???n ch??nh
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

