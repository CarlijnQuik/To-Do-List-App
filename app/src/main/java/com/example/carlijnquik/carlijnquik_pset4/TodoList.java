package com.example.carlijnquik.carlijnquik_pset4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TodoList extends AppCompatActivity {

    // prototypes
    ListView listview;
    private ArrayList<TodoItem> list_todos;
    private CustomAdapter adapter;
    DBhelper dbhelper;
    EditText inputET;
    TodoItem todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get right list
        Intent intent = getIntent();
        String clickedlist = intent.getStringExtra("clickedlist");

        // create a list and database helper
        list_todos = new ArrayList<>();
        dbhelper = new DBhelper(this, clickedlist);

        // read data from database so it can be viewed in list
        ArrayList<HashMap<String, String>> restore = dbhelper.read();
        for (int i = 0; i < restore.size(); i++) {
            TodoItem savedtodo = new TodoItem(restore.get(i).get("content"), restore.get(i).get("color"));
            savedtodo.id = Integer.parseInt(restore.get(i).get("id"));
            list_todos.add(savedtodo);
        }

        // if database is empty, give it example TODOs
        if(list_todos.isEmpty()) {
            TodoItem example1 = new TodoItem("Short-press an item to check it", "RED");
            dbhelper.create(example1);
            list_todos.add(example1);
        }

        // crate adapter with list
        adapter = new CustomAdapter(TodoList.this, list_todos);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        // update adapter
        adapter.notifyDataSetChanged();

        // prove database is correct
        String viewdatabase = dbhelper.read().toString();
        Log.d("Viewdatabaseaftercreate", viewdatabase);

        // create an onclicklistener for the add button
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener());

        // create onItemClickListener
        listview.setOnItemClickListener(new OnItemClickListener());

        // create onItemLongClickListener
        listview.setOnItemLongClickListener(new OnItemLongClickListener());

    }

    // decide what the add button does
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // get input and put it in a string
            inputET = (EditText) findViewById(R.id.input);
            String input = inputET.getText().toString();
            inputET.setText("");

            // make input a TodoItem object
            todo = new TodoItem(input, "RED");

            // put input into database and list
            try {
                dbhelper.create(todo);
                list_todos.add(todo);

                // update the adapter
                adapter.notifyDataSetChanged();

                // prove database is correct
                String viewdatabase2 = dbhelper.read().toString();
                Log.d("Viewdatabaseafteradd", viewdatabase2);

            } catch (Exception e) {
                Log.e("Cannot update database", e.getMessage(), e);
            }
        }
    }

    // decide what clicking of an item does
    public class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // get onclicked item
            TodoItem thisitem = list_todos.get(position);

            // change the color
            if (thisitem.getColortodo().equals("RED")){
                thisitem.setColortodo("GREEN");
                adapter.changeColor("GREEN", position);
            }
            else{
                thisitem.setColortodo("RED");
                adapter.changeColor("RED", position);
            }

            // update the adapter and database
            adapter.notifyDataSetChanged();
            dbhelper.update(thisitem);

            // prove database is correct
            String viewdatabase3 = dbhelper.read().toString();
            Log.d("Viewdatabaseafterclick", viewdatabase3);
            }
    }

    // decide what long clicking of an item does
    public class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            // find id of longclicked item
            TodoItem delitem = list_todos.get(position);

            // delete TodoItem from database and list
            dbhelper.delete(delitem.getId());
            list_todos.remove(position);

            // notify adapter and user
            adapter.notifyDataSetChanged();
            Toast.makeText(view.getContext(), "Item deleted!", Toast.LENGTH_LONG).show();

            // prove database is correct
            String viewdatabase4 = dbhelper.read().toString();
            Log.d("Viewdatabaseafterlong", viewdatabase4);

            return true;
        }


    }

}


