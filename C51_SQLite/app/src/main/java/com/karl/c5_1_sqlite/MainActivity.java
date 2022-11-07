package com.karl.c5_1_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String db_name="testDB";  // Name database
    static final String tb_name="test";       // data table name
    SQLiteDatabase db; //database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open or create a database
        db = openOrCreateDatabase(db_name,  Context.MODE_PRIVATE, null);

        String createTable="CREATE TABLE IF NOT EXISTS " +
                tb_name +        // data table name
                "(name VARCHAR(32), " +    //name field
                "phone VARCHAR(16), " +    //phone field
                "email VARCHAR(64))";  //Email field
        db.execSQL(createTable);   // Create data table

        // Insert 2 sets of data
        addData("Flag Publishing Co.","02-23963257","service@flag.com.tw");
        addData("PCDIY Magazine","02-23214335","service@pcdiy.com.tw");

        TextView txv=(TextView)findViewById(R.id.txv);
        // Get and display database information
        txv.setText("Database file path: " + db.getPath() + "\n\n" +
                "Database page size: " + db.getPageSize() + " Bytes\n\n" +
                "Maximum database size: " + db.getMaximumSize() + " Bytes");

        db.close();       // Close the database
    }

    private void addData(String name, String phone, String email) {
        ContentValues cv = new ContentValues(3); // Create an object with 3 data items
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);

        db.insert(tb_name, null, cv);  // Add data to the data table
    }
}