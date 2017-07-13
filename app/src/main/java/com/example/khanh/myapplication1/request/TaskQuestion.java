package com.example.khanh.myapplication1.request;

import android.content.Context;

import com.example.khanh.myapplication1.model.Lesson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khanh on 7/11/2017.
 */

public class TaskQuestion extends TaskBase<ArrayList<Lesson>> {


    public TaskQuestion(Context context) {
        super(context);
    }

    @Override
    protected String getBaseUrl() {
        return "http://s3.yobimind.com/d/tmp/en-grammar.json";
    }

    @Override
    protected ArrayList<Lesson> genDataFromJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type type = new TypeToken<List<Lesson>>() {}.getType();

        return gson.fromJson(json, type);
    }

}
