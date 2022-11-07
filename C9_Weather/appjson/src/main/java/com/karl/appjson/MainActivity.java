package com.karl.appjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String cityCode;        //Mã thành phố cần tìm nạp dữ liệu
    private String resultJson = "";      //Kết quả do API trả về

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetClick(View view) {
        EditText editText = findViewById(R.id.edtxt_citycode);
        if (editText.getText().toString().length() > 0) {
            this.cityCode = editText.getText().toString();
            getWeatherInfo();
        } else {
            Toast.makeText(MainActivity.this, "Xóa mã thành phố đầu vào", Toast.LENGTH_SHORT).show();
        }
    }

    //Gọi API Internet để nhận thông tin thời tiết，Trên Android 4.0，Không thể đặt kết nối mạng trên luồng chính,
    // nếu không sẽ báo lỗi android.os.NetworkOnMainThreadException
    //Phiên bản Android cao không kết nối được với Internet:Cleartext HTTP traffic to xxx not cách giải quyết được phép
    // Tham khảo：https://blog.csdn.net/gengkui9897/article/details/82863966
    public void getWeatherInfo() {
        Thread t = new Thread() {
            String path = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + cityCode;

            @Override
            public void run() {
                //Tạo url bằng cách sử dụng url
                URL url;
                try {
                    url = new URL(path);
                    //Nhận đối tượng kết nối và thực hiện cài đặt
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    //Gửi yêu cầu, nhận mã phản hồi
                    if (conn.getResponseCode() == 200) {
                        //Nhận luồng đầu vào do máy chủ trả về
                        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        for (int c; (c = in.read()) >= 0; ) {
                            sb.append((char) c);
                        }
                        //Gửi tin nhắn đến hàng đợi tin nhắn, main thread sẽ thực thi handleMessage
                        Message msg = handler.obtainMessage();
                        msg.obj = sb.toString();
                        in.close();
                        resultJson = sb.toString();
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Nhận mã thành phố: " + cityCode + "Lỗi thời tiết, thông báo lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            //There is data in the handler message queue and the following two lines of code are executed
            TextView txt = findViewById(R.id.txt);
            String[] weatherInfo = parseWeatherJosn(resultJson);
            if (weatherInfo != null) {
                txt.setText(convertArrayToString(weatherInfo, "\n") + "\n" + resultJson);
                Toast.makeText(MainActivity.this, "Dữ liệu thu được thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //Phân tích cú pháp dữ liệu định dạng JSON thời tiết
    public String[] parseWeatherJosn(String weatherJson) {
        JSONObject jsonObject;  //JSONObject member variable for JSON data manipulation of API
        JSONArray jsonArray;    //JSONArray member variable for JSON data manipulation of the API
        String[] weatherInfo = null;   //Dùng để lưu thông tin thời tiết của từng giao diện
        // WeatherInfo [0] tương ứng với giao diện thứ nhất
        // WeatherInfo [1] tương ứng với giao diện thứ hai
        //Phân tích cú pháp dữ liệu JSON thời tiết và lưu thông tin thời tiết của các trang khác nhau vào String weatherInfo [i]
        //weatherInfo[i]=Ngày 11,Nhiệt độ tối đa 23℃,<![CDATA[<cấp 3]]>,Nhiệt độ tối thiểu 12℃,Không có hướng gió duy trì,mưa nhỏ,Lhasa
        try {
            jsonObject = new JSONObject(weatherJson);
            jsonObject = jsonObject.getJSONObject("data");
            String cityName = jsonObject.getString("city");//get city
            jsonArray = jsonObject.getJSONArray("forecast");//Nhận dữ liệu thời tiết trong 5 ngày qua
            weatherInfo = new String[5];
            for (int i = 0; i < 5; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                weatherInfo[i] = "Ngày：" + jsonObject.getString("date") + ",";
                weatherInfo[i] += "Nhiệt độ tối đa：" + jsonObject.getString("high") + ",";
                weatherInfo[i] += "Nhiệt độ thấp nhất：" + jsonObject.getString("low") + ",";
                //weatherInfo[i] += "Gió：" + jsonObject.getString("fengli") + ",";
                //Dữ liệu gió là<![CDATA[<cấp 3]]>，Take out the useful part < level 3
                String[] fengliTemp = jsonObject.getString("fengli").split("]");
                String fengli = "";
                if (fengliTemp[0].length() > 9) {
                    fengli = fengliTemp[0].substring(9);
                    weatherInfo[i] += "Gió：" + fengli;
                }
                weatherInfo[i] += "Hướng gió：" + jsonObject.getString("fengxiang") + ",";
                String type = jsonObject.getString("type");
                weatherInfo[i] += "Thời tiết：" + type + ",";
                weatherInfo[i] += "Thành phố：" + cityName;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherInfo;
    }

    //convert string array to string
    public String convertArrayToString(String[] strArr, String splitter) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        String res = "";
        for (int i = 0, len = strArr.length; i < len; i++) {
            res += strArr[i];
            if (i < len - 1) {
                res += splitter;
            }
        }
        return res;
    }
}
