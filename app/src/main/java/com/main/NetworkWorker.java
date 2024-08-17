package com.main;


import android.util.Log;

import java.io.IOException;

import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NetworkWorker {

    public static Map<String, Integer> Classes = new HashMap<>(60);
    APIConstants apiConstant = new APIConstants();

    public List<List<String>> convertHtmlToJson(String html) {
        Log.d("schedule", "start");
        if (apiConstant.responseJson != null) {
            try {
                Document doc = Jsoup.parse(html);
                Elements timeElements = doc.select(".hour-time");
                Elements subjectCells = doc.select(".TTLesson");
                List<List<String>> schedule = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                for (int i = 2; i < 30; i += 2) {
                    schedule.get(i / 2 - 1).add(0, timeElements.get(i).text() + "-" + timeElements.get(i + 1).text());
                }
                for (int i = 0; i < 14; i++) {
                    for (int j = 0; j < 6; j++) {
                        schedule.get(i).add(subjectCells.get(j + 6 * i + 6).text());
                    }
                }
                Log.d("schedule", schedule.toString());
                return schedule;
            }
            catch (IndexOutOfBoundsException e){
                return List.of(List.of("error"));
            }
        } else {
            return null;
        }
    }

    public void Get() {
        for (int i = 0; i < apiConstant.classNumbers.length; i++) {
            Classes.put(apiConstant.Classes[i], apiConstant.classNumbers[i]);
        }

        // יצירת HttpClient
        OkHttpClient client = new OkHttpClient();
        String boundary = "-----------------------------29512838934197600815316117232";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("__EVENTTARGET", Objects.requireNonNull(apiConstant.menuOptions.get(APIConstants.option.ScheduleUpdated.name())))
                .addFormDataPart("__EVENTARGUMENT", "")
                .addFormDataPart("__LASTFOCUS", "")
                .addFormDataPart("__VIEWSTATE", "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")
                .addFormDataPart("__VIEWSTATEGENERATOR", "CA0B0334")
                .addFormDataPart("dnn$ctr7126$TimeTableView$ClassesList", Objects.requireNonNull(Classes.get(APIConstants.classNames.C10_7.name())).toString())
                .build();
        // יצירת בקשת HTTP
        Request request = new Request.Builder()
                .url("https://beitbiram.iscool.co.il/default.aspx") // החלף עם ה-URL שלך
                .post(requestBody)
                .build();

        // שליחת הבקשה וקבלת התגובה
        try (Response response = client.newCall(request).execute()) {
            apiConstant.isOffline = false;
            if (response.isSuccessful()) {
                // Handle the successful response
                assert response.body() != null;
                apiConstant.responseJson = response.body().string();
                response.body().close();
                Log.d("No error!!","in get");
//                Log.d("No error!!","isOffline :"+apiConstant.responseJson);

//                apiConstant.responseJson = convertHtmlToJson(apiConstant.responseJson);
//                FileWriter fileWriter = new FileWriter("jsonSave");
//                fileWriter.write(apiConstant.responseJson);

//                apiConstant.responseString = apiConstant.responseString.replaceAll("<td class=\"TTCell\" nowrap>", "{").replaceAll("b", " ").replaceAll("div", " ");
//                for (int i = 0; i < apiConstant.removeWords.length; i++) {
//                    apiConstant.responseString = apiConstant.responseString.replaceAll(apiConstant.removeWords[i], "");
//                }
//                apiConstant.responseString = apiConstant.responseString.substring(apiConstant.responseString.indexOf("{"),apiConstant.responseString.lastIndexOf("class=\"UpdateDate")).substring(120);
            }

        } catch (IOException e) {
            Log.d("error!!","in get");
            apiConstant.isOffline = true;
        }
    }
}
