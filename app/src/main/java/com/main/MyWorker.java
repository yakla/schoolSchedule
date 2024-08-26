package com.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyWorker extends Worker {
    private final NetworkWorker getHtml = new NetworkWorker();
    private static final String CHANNEL_ID = "notification_channel";
    private static final int NOTIFICATION_ID = 1;
    public int[] timeCheck = {745,
            830,
            930,
            1015,
            1115,
            1200,
            1315,
            1400,
            1445,
            1530,
            1615,
            1700,
            1745,
            1830,
            1845
    };
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        String formattedTime = DateFormat.getTimeInstance().format(currentTime);
        formattedTime = formattedTime.replace(":", "");
        formattedTime = formattedTime.substring(0, 4);
        int newHour = -1;
        for (int i = 0; i < timeCheck.length-1; i++) {
            if (timeCheck[i] <= Integer.parseInt(formattedTime) && Integer.parseInt(formattedTime) <= timeCheck[i + 1]) {
                newHour = i + 1;
            }
        }
//        Log.d("bug!!","newHour :"+newHour+"\noldHour :"+sharedPreferences.getInt("currentHour",-1));
        if (newHour != sharedPreferences.getInt("currentHour",-1)) {
//            Log.d("MyWorker", "doWork() started");
            getHtml.Get();
            if (!getHtml.apiConstant.isOffline) {

                editor.putString("key", getHtml.apiConstant.responseJson);
                editor.apply();
//                Log.d("MyWorker", "doWork() completed1");


                try {
                    showNotification("השיעור הבא", ReturnCurrentSubject(getHtml.convertHtmlToJson(sharedPreferences.getString("key", "default_value"))));
                }
                catch (IndexOutOfBoundsException e){
                    editor.putInt("currentHour", newHour);
                    editor.apply();
                    showNotification( "השיעור הבא","לא יכול לטעון");
                }
//                Log.d("MyWorker", "doWork() completed2");
            }
        }
        return Result.success();
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel if necessary
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.icon_new) // Ensure you have this icon in your drawable resources
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    String ReturnCurrentSubject(List<List<String>> result) {
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
                }
            }
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("currentHour", hourNum);
            editor.apply();
            if (hourNum == -1) {
                return "Not School Hours";
            } else {
                return result.get(hourNum - 1).get(currentTime.getDay());
            }
        }
        else {return "error while trying to request";}
    }
}