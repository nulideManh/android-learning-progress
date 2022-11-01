package com.karl.c1_7_listview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.listview_ui,
                new String[]{"title", "info", "img"},
                new int[]{R.id.title, R.id.info, R.id.img});
        ListView list = findViewById(R.id.ListView01);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        //define array
        String[] title = {
                "Corporate session",
                "Office mail",
                "Financial information query",
        };
        String[] info = {
                "Hey hello",
                "Write a letter",
                "Subsidized",
        };
        //define array
        int[] imageId = {
                R.drawable.icon1,
                R.drawable.icon2,
                R.drawable.icon3
        };
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        //Put the image id and list item text into the map through the for loop and add it to the List collection
        for (int i = 0; i < title.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", title[i]);
            map.put("info", info[i]);
            map.put("img", imageId[i]);
            listItems.add(map);
        }
        return listItems;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView txv = view.findViewById(R.id.title);
        Toast.makeText(this, "Your choice：" + (position+1) + " item, " + txv.getText(), Toast.LENGTH_SHORT).show();
    }
}