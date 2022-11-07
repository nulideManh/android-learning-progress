package com.karl.c9_weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String cityName;        //Tên thành phố cần lấy dữ liệu
    private String cityCode;        //Mã thành phố cần tìm nạp dữ liệu
    private List<Fragment> fragList; //ViewPager     Fragment of each page
    private String result = "";      //Kết quả do API trả về

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lấy thành phố khởi tạo, sau khi chọn thành phố sẽ được lưu vào cơ sở dữ liệu,
        // lần sau khi mở chương trình sẽ hiển thị tự động.
        getInitCity();
        //phương thức getWeatherSta () để cập nhật dữ liệu thời tiết
        getWeatherInfo();
    }

    //Lấy thành phố khởi tạo, sau khi chọn thành phố sẽ được lưu vào cơ sở dữ liệu,
    // lần sau khi mở chương trình sẽ hiển thị tự động.
    @SuppressLint("Range")
    public void getInitCity() {
        SQLiteDatabase db;      //cơ sở dữ liệu
        MyDBHelper myDBHelper;  //SQLiteOpenHelper
        //hoạt động cơ sở dữ liệu
        myDBHelper = new MyDBHelper(this, "my.db", null, 1);
        db = myDBHelper.getWritableDatabase();
        //con trỏ cơ sở dữ liệu
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        //Nếu cơ sở dữ liệu trống, hãy chèn một dữ liệu mặc định trước. Dữ liệu mặc định cityName = Beijing cityCode = 101010100
        if (cursor.moveToFirst() == false) {
            ContentValues values = new ContentValues();
            values.put("cityCode", "101010100");
            values.put("cityName", "北京");
            db.insert("user", null, values);
            cityName = "北京";
            cityCode = "101010100";
        } else {
            //Nếu cơ sở dữ liệu không trống. Lấy cityName, cityCode từ cơ sở dữ liệu
            // và gán chúng cho biến cityName và biến cityCode
            cityName = cursor.getString(cursor.getColumnIndex("cityName"));
            cityCode = cursor.getString(cursor.getColumnIndex("cityCode"));
        }
    }

    //Gọi API Internet để lấy thông tin thời tiết. Trong Android 4.0 trở lên,
    // kết nối mạng không thể được đặt trên chuỗi chính,
    // nếu không sẽ báo lỗi android.os.NetworkOnMainThreadException
    //Kết nối mạng phiên bản cao Android không thành công báo lỗi:
    // Cleartext HTTP traffic to xxx not allow Solution
    // Tham khảo: https://blog.csdn.net/gengkui9897/article/details/82863966
    public void getWeatherInfo() {
        Thread t = new Thread() {
            String path = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + cityCode;

            @Override
            public void run() {
                //Tạo url bằng cách sử dụng url
                URL url;
                try {
                    url = new URL(path);
                    //Get the connection object and make settings
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    //Send request, get response code
                    if (conn.getResponseCode() == 200) {
                        //Get the input stream returned by the server
                        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        for (int c; (c = in.read()) >= 0; ) {
                            sb.append((char) c);
                        }
                        //Gửi tin nhắn đến hàng đợi tin nhắn, main thread sẽ thực thi handleMessage
                        Message msg = handler.obtainMessage();
                        msg.obj = sb.toString();
                        in.close();
                        result = sb.toString();
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            //Có dữ liệu trong hàng đợi thông báo của trình xử lý và hai dòng code sau được thực thi
            //Thay đổi phương pháp cập nhật giao diện người dùng
            setPagerView();
            Toast.makeText(MainActivity.this, "Dữ liệu thu được thành công", Toast.LENGTH_SHORT).show();
        }
    };

    public void setPagerView() {
        JSONObject jsonObject;  //JSONObject member variable for JSON data manipulation of API
        JSONArray jsonArray;    //JSONArray member variable for JSON data manipulation of the API
        JSONObject jsonObject2; //JSONObject member variable for manipulating imagecode.json
        JSONArray jsonArray2;   //JSONArray member variable for manipulating imagecode.json
        //Nhận hình ảnh tương ứng với loại thời tiết
        String[] arry_data1 = null;    //The weather type used to temporarily save imagecode.json corresponds to arry_data2 one-to-one
        String[] arry_data2 = null;    //The image ID used to temporarily save imagecode.json corresponds to arry_data1 one-to-one
        //Put imagecode.json in the file. Weather type and weather image name are bound to arry_data1, arry_data2 respectively
            try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("imagecode.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonObject2 = new JSONObject(stringBuilder.toString());
            jsonArray2 = jsonObject2.getJSONArray("img");
            arry_data1 = new String[jsonArray2.length()];
            arry_data2 = new String[jsonArray2.length()];
            for (int i = 0; i < jsonArray2.length(); i++) {
                jsonObject2 = jsonArray2.getJSONObject(i);
                arry_data1[i] = jsonObject2.getString("type");
                arry_data2[i] = jsonObject2.getString("code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Build five Bundles to pass weather data on different days to different Fragments Five days of weather data are sent to five Fragment pages
        Bundle b1 = new Bundle();
        Bundle b2 = new Bundle();
        Bundle b3 = new Bundle();
        Bundle b4 = new Bundle();
        Bundle b5 = new Bundle();
        String[] weatherInfo;   //Dùng để lưu thông tin thời tiết của từng giao diện
        // WeatherInfo [0] tương ứng với giao diện thứ nhất
        // WeatherInfo [1] tương ứng với giao diện thứ hai
        //Phân tích cú pháp dữ liệu JSON thời tiết và lưu thông tin thời tiết của các trang khác nhau vào String weatherInfo [i]
        //weatherInfo[i]=Ngày 11,Nhiệt độ cao 23℃,<![CDATA[<cấp 3]]>,Nhiệt độ thấp 12℃,Không có hướng gió duy trì,mưa nhỏ,Lhasa,13
        try {
            jsonObject = new JSONObject(result);
            jsonObject = jsonObject.getJSONObject("data");
            jsonArray = jsonObject.getJSONArray("forecast");
            weatherInfo = new String[5];
            for (int i = 0; i < 5; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                weatherInfo[i] = jsonObject.getString("date") + ",";
                weatherInfo[i] += jsonObject.getString("high") + ",";
                weatherInfo[i] += jsonObject.getString("fengli") + ",";
                weatherInfo[i] += jsonObject.getString("low") + ",";
                weatherInfo[i] += jsonObject.getString("fengxiang") + ",";
                String type = jsonObject.getString("type");
                weatherInfo[i] += type + ",";
                weatherInfo[i] += cityName + ",";
                String imageCode = "99";
                for (int j = 0; j < arry_data1.length; j++) {
                    if (type.equals(arry_data1[j])) {
                        imageCode = arry_data2[j];
                        break;
                    }
                }
                weatherInfo[i] += imageCode;
            }
            b1.putString("weatherInfo", weatherInfo[0]);
            b2.putString("weatherInfo", weatherInfo[1]);
            b3.putString("weatherInfo", weatherInfo[2]);
            b4.putString("weatherInfo", weatherInfo[3]);
            b5.putString("weatherInfo", weatherInfo[4]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create adaptation for ViewPager and generate five Fragment objects
        fragList = new ArrayList<Fragment>();
        WeatherFragment wf1 = new WeatherFragment();
        WeatherFragment wf2 = new WeatherFragment();
        WeatherFragment wf3 = new WeatherFragment();
        WeatherFragment wf4 = new WeatherFragment();
        WeatherFragment wf5 = new WeatherFragment();
        //Bundle binding Fragment
        wf1.setArguments(b1);
        wf2.setArguments(b2);
        wf3.setArguments(b3);
        wf4.setArguments(b4);
        wf5.setArguments(b5);
        //Add fragment to List
        fragList.add(wf1);
        fragList.add(wf2);
        fragList.add(wf3);
        fragList.add(wf4);
        fragList.add(wf5);
        //Create ViewPager Adapter
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(this, fragList);
        //ViewPager binding adapter
        ViewPager2 pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }
    

    //Chuyển đến giao diện thành phố chọn
    public void onClickChangeCity(View view) {
        Intent intent = new Intent(this, AddCityActivity.class);
        intent.putExtra("cityName", cityName);
        startActivity(intent);
    }
}