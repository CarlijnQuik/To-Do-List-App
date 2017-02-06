package com.example.carlijnquik.carlijnquik_pset4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Activity activity;
    Context context;
    ArrayList<TodoItem> todos_list;

    protected CustomAdapter(Activity activity, ArrayList<TodoItem> todos) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.todos_list = todos;
    }

    private class ViewHolder {
        TextView text;
    }

    public void changeColor(String newColor, int id){
        TodoItem item = todos_list.get(id);
        item.setColortodo(newColor);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.listitem_1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // get current item and set text
        TodoItem item = (TodoItem) getItem(position);
        holder.text.setText(item.content);

        // set color
        if(item.colortodo.equals("RED")){
            holder.text.setTextColor(Color.RED);
        }
        else {
            holder.text.setTextColor(Color.GREEN);
        }

        return convertView;
    }

    @Override
    public int getCount(){
        return todos_list.size();
    }

    public Object getItem(int position){
        return todos_list.get(position);
    }

    public long getItemId(int i){
        return todos_list.indexOf(getItem(i));
    }

}
