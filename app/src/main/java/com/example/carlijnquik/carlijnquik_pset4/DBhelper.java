package com.example.carlijnquik.carlijnquik_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBhelper extends SQLiteOpenHelper {

    // set fields of database schema
    private static String DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "TodoItem";

    private String content_id = "content";
    private String color = "color";

    // constructor
    public DBhelper(Context context, String DATABASE_NAME){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    // onCreate
    public void onCreate(SQLiteDatabase sqlLiteDatabase){
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + content_id + " TEXT, " + color + " TEXT)";
        sqlLiteDatabase.execSQL(CREATE_TABLE);
    }

    // onUpgrade
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    // create
    public void create(TodoItem TodoItem){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(content_id, TodoItem.content);
        values.put(color, TodoItem.colortodo);
        db.insert(TABLE, null, values);
        db.close();
    }

    // read
    public ArrayList<HashMap<String, String>> read(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id , " + content_id + " , " + color + " FROM " + TABLE ;
        ArrayList<HashMap<String, String>> todo_list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> tododata = new HashMap<>();
                tododata.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                tododata.put("content", cursor.getString(cursor.getColumnIndex(content_id)));
                tododata.put("color", cursor.getString(cursor.getColumnIndex(color)));
                todo_list.add(tododata);

            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todo_list;
    }

    // update
    public void update(TodoItem todo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(content_id, todo.content);
        values.put(color, todo.colortodo);
        db.update(TABLE, values, "_id = ? ", new String[] {String.valueOf(todo.id)});
        db.close();
    }

    // delete
    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " _id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

