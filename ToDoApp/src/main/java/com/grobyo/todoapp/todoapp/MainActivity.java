package com.grobyo.todoapp.todoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //..Super, SetContentView, define lvItems
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.listViewItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Items");
        setupListViewListener();
    }
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
               items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });
    }
    private void readItems(){
        File filesDir = getFilesDir();
        File todofile = new File(filesDir, "todo.txt");
      try {
          items = new ArrayList<String>(FileUtils.readLines(todofile));
      } catch (IOException e){
          new ArrayList<String>();
          e.printStackTrace();
      }
    }
    private void saveItems(){
        File filesDir = getFilesDir();
        File todofile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todofile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
public void addTodoItem (View v){
    EditText editTextNewItem = (EditText)
            findViewById(R.id.editTextNewItem);
    //Android studio does not like etNewItem
    itemsAdapter.add(editTextNewItem.getText().toString());
    editTextNewItem.setText("");
    saveItems(); //write to file

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
