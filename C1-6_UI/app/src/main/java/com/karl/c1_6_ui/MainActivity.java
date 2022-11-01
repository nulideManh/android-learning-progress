package com.karl.c1_6_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        list = (ListView) findViewById(R.id.ListView01);

         //dynamically bind data
         //define the array
        String[] data = {
                 "Enterprise Session",
                 "Office mail",
                 "Financial Information Query",
                 "Ngao Ngo",
        };
        //Set up array adapter ArrayAdapter for ListView
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));

        //Set list options listener for ListView
        //list.setOnItemClickListener(this);
    }

    //Define events for list option listeners
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Your selected item is："
                + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
    }
}