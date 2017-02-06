package com.example.carlijnquik.carlijnquik_pset4;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TodoItem {

    public int id;
    public String content;
    public String colortodo;

    public TodoItem(String content, String colortodo){
        this.content = content;
        this.colortodo = colortodo;
    }

    public String getColortodo(){
        return this.colortodo;
    }

    public void setColortodo(String newColor) {
        this.colortodo = newColor;
    }

    public int getId() {
        return this.id;
    }

}
