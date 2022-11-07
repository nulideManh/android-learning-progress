package com.karl.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String db_name = "testDB";  // db name
    static final String tb_name = "test";       // tb name
    SQLiteDatabase db; //db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open or create a database
        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);

        String createTable = "CREATE TABLE IF NOT EXISTS " +
                tb_name +        // Open or create a database
                "(name VARCHAR(32), " +    //name field
                "phone VARCHAR(16), " +    //phone
                "email VARCHAR(64))";  //Email
        db.execSQL(createTable);   // Create data table

        Cursor c = db.rawQuery("SELECT * FROM " + tb_name, null);  // Query all data in the tb_name data table
        if (c.getCount() == 0) {  // If there is no data, 2 new data will be added immediately
            addData("Flag Publishing Co.", "02-23963257", "service@flag.com.tw");
            addData("PCDIY Magazine", "02-23214335", "service@pcdiy.com.tw");
            c = db.rawQuery("SELECT * FROM " + tb_name, null); // 重新查询
        }

        if (c.getCount() > 0) {   // If there is data
            String str = "Total " + c.getCount() + "item data\n";
            str += "-----\n";

            c.moveToLast();    // Move to item 1
            do {       // read data item by item
                str += "name:" + c.getString(0) + "\n";
                str += "phone:" + c.getString(1) + "\n";
                str += "email:" + c.getString(2) + "\n";
                str += "-----\n";
            } while (c.moveToPrevious());   // Continue the loop once there is an item

            TextView txv = (TextView) findViewById(R.id.txv);
            txv.setText(str);
        }
        db.close();       // close the database
    }

    private void addData(String name, String phone, String email) {
        ContentValues cv = new ContentValues(3); // Create an object with 3 data items
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);

        db.insert(tb_name, null, cv);  // add data to the data table
    }
}