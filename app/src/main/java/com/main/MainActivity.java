package com.main;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkOperationAsyncTask.OnTaskCompleted {
    GetSchoolHtml getSchoolHtml = new GetSchoolHtml();
    public static GridView mGridView;
    private List<String> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new NetworkOperationAsyncTask(getSchoolHtml,this).execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = findViewById(R.id.simpleGridView);

        mData = new ArrayList<>();
        mData.add("yes");
        mData.add("what");

        MySimpleGridViewAdapter adapter = new MySimpleGridViewAdapter(this, mData);
        mGridView.setAdapter(adapter);
    }

    @Override
    public void onTaskCompleted(String result) {
        mData.set(0, result);
        MySimpleGridViewAdapter adapter = new MySimpleGridViewAdapter(this, mData);
        mGridView.setAdapter(adapter);
    }
}