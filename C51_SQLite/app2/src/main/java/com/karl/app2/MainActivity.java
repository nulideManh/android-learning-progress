package com.karl.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String DB_NAME = "HotlineDB";
    static final String TB_NAME = "hotlist";
    static final int MAX = 8;
    static final String[] FROM = new String[]{"name", "phone", "email"};
    SQLiteDatabase db;
    Cursor cur;
    SimpleCursorAdapter adapter;
    EditText etName, etPhone, etEmail;
    Button btInsert, btUpdate, btDelete;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btInsert = findViewById(R.id.btInsert);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);

        // Open or create a database
        db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        // Create data table
        String createTable = "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + //index auto increment
                "name VARCHAR(32), " +
                "phone VARCHAR(16), " +
                "email VARCHAR(64))";
        db.execSQL(createTable);

        cur = db.rawQuery("SELECT * FROM " + TB_NAME, null);  // Query data
        // If the query result is empty, write 2 test data
        if (cur.getCount() == 0) {
            addData("Contact 1", "02-23963257", "experiment@β.com.tw");
            addData("Contact 2", "02-23214335", "experiment@β.com.tw");
        }

        adapter = new SimpleCursorAdapter(this,
                R.layout.item, cur,
                FROM,
                new int[]{R.id.name, R.id.phone, R.id.email}, 0);

        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);           // set up Adapter
        lv.setOnItemClickListener(this); // Set up click event listener
        requery(); // Call the custom method, re-query and set the button state
    }

    private void addData(String name, String phone, String email) {
        ContentValues cv = new ContentValues(3); // Create a ContentValues object with 3 fields
        cv.put(FROM[0], name);
        cv.put(FROM[1], phone);
        cv.put(FROM[2], email);

        db.insert(TB_NAME, null, cv);  // Add 1 record
    }

    private void update(String name, String phone, String email, int id) {
        ContentValues cv = new ContentValues(3);
        cv.put(FROM[0], name);
        cv.put(FROM[1], phone);
        cv.put(FROM[2], email);

        db.update(TB_NAME, cv, "_id=" + id, null);   // update the field pointed to by id
    }

    private void requery() {   // Custom method for re-query
        cur = db.rawQuery("SELECT * FROM " + TB_NAME, null);
        adapter.changeCursor(cur); //Change the Cursor of the Adapter
        // The limit has been reached, the add button is disabled
        if (cur.getCount() == MAX) {
            btInsert.setEnabled(false);
        } else {
            btInsert.setEnabled(true);
        }
        btUpdate.setEnabled(false);    // Disable update button
        btDelete.setEnabled(false);    // Disable delete button
    }

    @SuppressLint("Range")
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        cur.moveToPosition(position); //   Move the Cursor to the user-selected option
        // Read out name, phone, Email data and display
        etName.setText(cur.getString(cur.getColumnIndex(FROM[0])));
        etPhone.setText(cur.getString(cur.getColumnIndex(FROM[1])));
        etEmail.setText(cur.getString(cur.getColumnIndex(FROM[2])));

        btUpdate.setEnabled(true); // enable update button
        btDelete.setEnabled(true); // Enable delete button
    }

    public void onInsertUpdate(View v) {
        String nameStr = etName.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();
        String emailStr = etEmail.getText().toString().trim();
        // Returns if the content of any field is empty
        if (nameStr.length() == 0 || phoneStr.length() == 0 || emailStr.length() == 0) return;

        // Click the update button
        if (v.getId() == R.id.btUpdate) {
            update(nameStr, phoneStr, emailStr, cur.getInt(0));
        } else {                     // 单击新增按钮
            addData(nameStr, phoneStr, emailStr);
        }

        requery(); // Update Cursor Content
    }

    public void onDelete(View v) {  // Delete button's On Click event method
        db.delete(TB_NAME, "_id=" + cur.getInt(0), null);
        requery();
    }

    public void call(View v) {  // Call up
        @SuppressLint("Range") String uri = "tel:" + cur.getString(cur.getColumnIndex(FROM[1]));
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(it);
    }

    public void mail(View v) {  // send email
        @SuppressLint("Range") String uri = "mailto:" + cur.getString(cur.getColumnIndex(FROM[2]));
        Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
        startActivity(it);
    }
}