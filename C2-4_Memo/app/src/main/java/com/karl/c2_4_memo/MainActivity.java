package com.karl.c2_4_memo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    // Default memo content
    String[] aMemo = {"1. Nhấn để chỉnh sửa ghi chú", "2. Nhấn và giữ để xóa ghi chú", "3.", "4.", "5.", "6."};
    ListView lv; // show memo ListView
    ArrayAdapter<String> aa; // ListView Bridge to Memo Data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listView);
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aMemo);
        lv.setAdapter(aa);    //set up listView1 Content
        // Set a listener for listView1 being clicked
        lv.setOnItemClickListener(this);
        // Set a listener for long-pressed listView1
        lv.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(this, EditActivity.class);
        it.putExtra("Đánh số", position + 1);      //Additional number
        it.putExtra("Memo", aMemo[position]); //Contents of additional memo options
        //startActivity(it);                  // Start the Edit activity
        //The first parameter: an Intent object
        //Second parameter: If >= 0, the requestCode will be returned in onActivityResult() when the Activity ends. In order to determine which Activity the returned data is returned from
        startActivityForResult(it, position);  // Start the Edit activity
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        aMemo[position] = (position + 1) + "."; //Clear the content (only the numbers remain)
        aa.notifyDataSetChanged();  //Notifies the Adapter to update the array contents
        return true;             //Returns true to indicate that this event has been handled
    }

    //The first parameter: This integer requestCode is provided to onActivityResult to confirm which Activity the returned data is returned from. This requestCode corresponds to the requestCode in startActivityForResult.
    //The second parameter: This integer resultCode is returned by the child Activity through its setResult() method.
    //The third parameter: an Intent object with the returned data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        super.onActivityResult(requestCode, resultCode, it);
        if (resultCode == RESULT_OK) {
            aMemo[requestCode] = it.getStringExtra("Memo");
            aa.notifyDataSetChanged();  //Notifies the Adapter to update the array contents
        }
    }
}