package com.example.carlijnquik.carlijnquik_pset4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TodoManager extends AppCompatActivity {

    // prototypes
    ListView listsview;
    Set<String> savedlists;
    ArrayAdapter<String> listadapter;
    SharedPreferences prefs;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manager);

        // show the current list
        ViewList();

        // create an onclicklistener for the add button
        Button addlist = (Button) findViewById(R.id.addlist);
        addlist.setOnClickListener(new TodoManager.OnClickListener());

        // create onItemClickListener
        listsview.setOnItemClickListener(new TodoManager.OnItemClickListener());

        // create onItemLongClickListener
        listsview.setOnItemLongClickListener(new TodoManager.OnItemLongClickListener());

    }

    public void ViewList(){
        // get the right list from sharedpreferences
        prefs = this.getSharedPreferences("lists", MODE_PRIVATE);
        savedlists = new HashSet<>(prefs.getStringSet("list", new HashSet<String>()));

        // crate adapter with list
        listadapter = new ArrayAdapter<>(TodoManager.this, android.R.layout.simple_list_item_1, savedlists.toArray(new String[savedlists.size()]));
        listsview = (ListView) findViewById(R.id.listsview);
        listsview.setAdapter(listadapter);

    }

    // decide what the add button does
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // get input and put it in a string
            inputText = (EditText) findViewById(R.id.inputlist);
            String inputtext = inputText.getText().toString();
            inputText.setText("");

            // add new list to sharedpreferences
            prefs = getSharedPreferences("lists", MODE_PRIVATE);
            savedlists = new HashSet<>(prefs.getStringSet("list", new HashSet<String>()));
            savedlists.add(inputtext);
            prefs.edit().putStringSet("list", savedlists).apply();

            // update view
            listadapter.notifyDataSetChanged();
            ViewList();
        }
    }

    // decide what clicking of an item does
    public class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // get onclicked item
            String clickedlist = (String) parent.getAdapter().getItem(position);
            Intent intent = new Intent(TodoManager.this, TodoList.class);
            intent.putExtra("clickedlist", clickedlist);
            startActivity(intent);
        }
    }

    // decide what long clicking of an item does
    public class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // find longclicked item
            String item_to_delete = (String) parent.getAdapter().getItem(position);

            // remove it from shared preferences
            savedlists.remove(item_to_delete);
            prefs.edit().putStringSet("list", savedlists).apply();

            // update view
            listadapter.notifyDataSetChanged();
            ViewList();
            Toast.makeText(view.getContext(), "List deleted!", Toast.LENGTH_LONG).show();

            return true;
        }

    }
}
