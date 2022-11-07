package com.karl.c9_weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WeatherFragment extends Fragment {
    private int[] imgs = {
            R.drawable.a0, R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
            R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11,
            R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17,
            R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23,
            R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28, R.drawable.a29,
            R.drawable.a30, R.drawable.a31, R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35,
            R.drawable.a36, R.drawable.a37, R.drawable.a38, R.drawable.a99
    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //layout to View object
        View view = inflater.inflate(R.layout.fragment, container, false);
        //bind data
        //Phân tích dữ liệu JSON thời tiết và lưu thông tin thời tiết của các trang khác nhau vào String weatherInfo
        //Đi qua Bundle
        //weatherInfo=Ngày 11,Nhiệt độ tối đa 23℃,<![CDATA[<cấp 3]]>,Nhiệt độ tối thiểu 12℃,Không có hướng gió duy trì,Mưa nhỏ,Bắc Ninh,13
        String weatherInfo = getArguments().getString("weatherInfo");

        //chia nhỏ dữ liệu
        String[] Info = weatherInfo.split(",");
        //Get the controls in the fragment, one imageView, seven textViews
        TextView ftvCityName = view.findViewById(R.id.fTextCityName);//tên thành phố
        TextView ftv = view.findViewById(R.id.fText);//ngày
        TextView ftv2 = view.findViewById(R.id.fText2);//thời tiết
        TextView ftv3 = view.findViewById(R.id.fText3);//Nhiệt độ tối đa
        TextView ftv4 = view.findViewById(R.id.fText4);//nhiệt độ tối thiểu
        TextView ftv5 = view.findViewById(R.id.fText5);//gió
        TextView ftv6 = view.findViewById(R.id.fText6);//hướng gió
        //Put the data into the corresponding control
        ftvCityName.setText(Info[6]);//tên thành phố
        ftv.setText(ftv.getText().toString() + Info[0]);//ngày
        ftv2.setText(ftv2.getText().toString() + Info[5]);//thời tiết
        ftv3.setText("Nhất" + Info[1]);//Nhiệt độ tối đa
        ftv4.setText("Nhất" + Info[3]);//nhiệt độ tối thiểu
        //The wind data is <![CDATA[<level 3]]>, take out the useful part <level 3
        String[] fengliTemp = Info[2].split("]");
        String temp = "";
        if (fengliTemp[0].length() > 9) {
            temp = fengliTemp[0].substring(9);
        }
        ftv5.setText(ftv5.getText().toString() + temp);//Gió
        ftv6.setText(ftv6.getText().toString() + Info[4]);//hướng gió
        //Chọn hình ảnh tương ứng theo thời tiết
        int code = Integer.parseInt(Info[7]);
        ImageView fiv = view.findViewById(R.id.fimageView);
        if (code != 99) {
            fiv.setImageResource(imgs[code]);
        }
        return view;
    }
}

