package com.main;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NetworkOperationAsyncTask.OnTaskCompleted {
    GetSchoolHtml getSchoolHtml;
    public GridView lessonGridView;
    GridView hourGridView;
    RecyclerView recyclerView;
    private List<String> lessonData;
    private List<String> hourData;
    public Map<Integer, String> getDay = Map.of(6, " ראשון ", 5, " שני ", 4, " שלישי ", 3, " רביעי ", 2, " חמישי ", 1, " שישי ");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        hourGridView = findViewById(R.id.hour_Grid_View);

        TabLayout tabLayOut = findViewById(R.id.tabLayout);
        switch(tabLayOut.getSelectedTabPosition()){
            case 1 -> getSchoolHtml = new GetSchoolHtml(GetSchoolHtml.Option.ScheduleUpdated);
            case 2 -> getSchoolHtml = new GetSchoolHtml(GetSchoolHtml.Option.Schedule);
        }

//        lessonGridView = findViewById(R.id.simpleGridView);


        hourData = new ArrayList<>();
        lessonData = new ArrayList<>();


        new NetworkOperationAsyncTask(getSchoolHtml, this).execute();
    }

    @Override
    public void onTaskCompleted(List<List<String>> result) {

        hourData.add("שעה");
        for (int j = 0; j < result.size(); j++) {
            hourData.add(result.get(j).get(0));
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < result.size(); j++) {
                if (j == 0) {
                    lessonData.add(getDay.get(i));
                } else {
                    lessonData.add(result.get(j).get(i));
                }
            }
        }


        recyclerView.setAdapter(new RecyclerViewAdapter(this, lessonData));
        hourGridView.setAdapter(new GridViewAdapter(this, hourData));
    }


}
