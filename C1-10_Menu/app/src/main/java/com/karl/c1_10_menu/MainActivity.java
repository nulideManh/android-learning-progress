package com.karl.c1_10_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt;

    ImageView img;
    private static final int item1 = Menu.FIRST;
    private static final int item2 = Menu.FIRST+1;
    private static final int item3 = Menu.FIRST+2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);

        registerForContextMenu(txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Call the parent class method to join the system menu
        super.onCreateOptionsMenu(menu);
        // Add menu item
        menu.add(
                1,//Group No
                1, //unique ID number
                1, //queue number
                "Home"); //title
        menu.add( 1, 2, 2,  "Search");
        menu.add( 1, 3, 3,  "Set up");
        menu.add( 1, 4, 4,  "Quit");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String title = "Chosen" + item.getTitle().toString();
        switch (item.getItemId())
        { //Respond to each menu item (by the menu item's ID)
            case 1:
                txt.setText(title);
                break;
            case 2:
                txt.setText(title);
                break;
            case 3:
                txt.setText(title);
                break;
            case 4:
                txt.setText(title);
                break;
            default:
                //For unhandled events, hand them over to the parent class to handle
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    //上下文菜单，长按特定视图会激活上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //Add menu item
        menu.add(0, item1, 0, "Copy");
        menu.add(0, item2, 0, "Collect");
        menu.add(0, item3, 0, "Remind");
    }
    //menu click response
    @Override
    public boolean onContextItemSelected(MenuItem item){
        String title = "Chosen" + item.getTitle().toString();
        txt.setText(title);
        //Get information about the currently selected menu item
        switch(item.getItemId())
        {
            case item1:
                //Copy code
                break;
            case item2:
                //Favorite code
                break;
            case item3:
                //Reminder code
                break;
        }
        return true;
    }
}