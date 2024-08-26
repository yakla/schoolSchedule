package com.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NetworkOperationAsyncTask.OnTaskCompleted {
    NetworkWorker getSchoolHtml = new NetworkWorker();
    public TextView textView1;
    public TextView textView2;
    public ImageButton imageButton;
    private static final int PERMISSION_REQUEST_CODE = 100;
    boolean isNetworking = false;
    public String[] getDay = {" ראשון ", " שני ", " שלישי ", " רביעי ", " חמישי ", " שישי "};


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        imageButton = findViewById(R.id.imageButton);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView1.getText().equals(" loading...") && !isNetworking) {
                    isNetworking = true;
                    new NetworkOperationAsyncTask(getSchoolHtml, MainActivity.this).execute();
                }
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isNetworking = true;
                new NetworkOperationAsyncTask(getSchoolHtml, MainActivity.this).execute();
                Log.d("day", String.valueOf(Calendar.getInstance().getTime().getDay()));
                if (!textView1.getText().equals(" loading...")) {
                    timer.cancel();
                }
            }
        }, 0, 5000);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        } else {
            // Schedule the work if permission is already granted
            scheduleWork();
        }

    }


    @Override
    public void onTaskCompleted(List<List<String>> result) {
        if (!result.get(0).get(0).equals("error")) {
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();
            String formattedTime = DateFormat.getTimeInstance().format(currentTime);
            formattedTime = formattedTime.replace(":", "");
            formattedTime = formattedTime.substring(0, 4);
            int hourNum = -1;
            String temporarySave = "";
            for (int i = 0; i < result.size(); i++) {
                String[] resultTimeBack = result.get(i).get(0).split("-");
                String[] resultTimeFront;
                if (result.size() > i + 1) {
                    resultTimeFront = result.get(i + 1).get(0).split("-");
                } else {
                    resultTimeFront = new String[]{result.get(i).get(0).split("-")[1], result.get(i).get(0).split("-")[0]};
                }
                temporarySave = result.get(i).get(0);
                for (int j = 0; j < resultTimeBack.length; j++) {
                    resultTimeBack[j] = resultTimeBack[j].replace(":", "");
                    resultTimeFront[j] = resultTimeFront[j].replace(":", "");
                }
                int timeBeforeShow1 = 15;
                int timeBeforeShow2 = 15;
                if (Integer.parseInt(resultTimeBack[0].substring(resultTimeBack[0].length() - 2)) == 0) {
                    timeBeforeShow1 = 55;
                }

                if (Integer.parseInt(resultTimeFront[0].substring(resultTimeBack[0].length() - 2)) == 0) {
                    timeBeforeShow2 = 55;
                }

                if (Integer.parseInt(resultTimeBack[0]) - timeBeforeShow1 <= Integer.parseInt(formattedTime) && Integer.parseInt(resultTimeFront[0]) - timeBeforeShow2 >= Integer.parseInt(formattedTime)) {
                    hourNum = i + 1;
                    break;
                } else {
                    Log.d("time check", Integer.parseInt(resultTimeBack[0].substring(resultTimeBack[0].length() - 2)) + ".first." + (Integer.parseInt(resultTimeBack[0]) - timeBeforeShow1) + "-<-" + Integer.parseInt(formattedTime) + "-<-" + (Integer.parseInt(resultTimeFront[0]) - timeBeforeShow2));
                }
            }

            if (hourNum == -1) {
                textView1.setText("not School hour");
            } else {
                textView1.setText("שיעור :" + hourNum + "\n" + temporarySave);
                if (currentTime.getDay() + 1 < 7) {
                    textView2.setText(result.get(hourNum - 1).get(currentTime.getDay() + 1));
                } else {
                    textView2.setText("not School day 1");
//                    Log.d("dayCheck", String.valueOf(currentTime.getDay()));
                }
            }
        }
        else {
            textView2.setText("error while trying to request");
            isNetworking = false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Schedule the work if permission is granted
                scheduleWork();
            } else {
                // Handle the case where permission is denied
                // You might want to show a message to the user
            }
        }
    }

    private void scheduleWork() {
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);
    }
}





