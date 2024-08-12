package com.main;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NetworkOperationAsyncTask.OnTaskCompleted {
    GetSchoolHtml getSchoolHtml = new GetSchoolHtml();
    public TextView textView1;
    public TextView textView2;
    public String []getDay = {" ראשון ", " שני ", " שלישי ", " רביעי ", " חמישי ", " שישי "};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NetworkOperationAsyncTask(getSchoolHtml, this).execute();
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
    }

    @Override
    public void onTaskCompleted(List<List<String>> result) {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        String formattedTime = DateFormat.getTimeInstance().format(currentTime);
        formattedTime = formattedTime.replace(":","");
        formattedTime = formattedTime.substring(0,4);
        int hourNum = -1;
        String temporarySave = "";
        for (int i = 0; i <result.size(); i++) {
            String []resultTime = result.get(i).get(0).split("-");
            temporarySave = result.get(i).get(0);
            for (int j = 0; j <resultTime.length; j++) {
                resultTime[j] = resultTime[j].replace(":","");
            }

            if(Integer.parseInt(resultTime[0])<Integer.parseInt(formattedTime)&&Integer.parseInt(resultTime[1])>Integer.parseInt(formattedTime)){
                hourNum = i+1;
                break;
            }
            else{
                Log.d("time check", String.valueOf(Integer.parseInt(resultTime[0]) +"-<-"+ Integer.parseInt(formattedTime)+"-<-"+Integer.parseInt(resultTime[1])));
            }
        }
        if(hourNum==-1){
            textView1.setText("not School hour");
        }
        else {
            textView1.setText("שעה :"+hourNum+"\n"+temporarySave);
            textView2.setText(result.get(hourNum-1).get(currentTime.getDay()));
        }}


}
