package com.main;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

class NetworkOperationAsyncTask extends AsyncTask<Void, Void, List<List<String>> > {
    GetSchoolHtml getSchoolHtml;
    private final OnTaskCompleted listener;

    interface OnTaskCompleted {
        void onTaskCompleted(List<List<String>> result);
    }
    public NetworkOperationAsyncTask(GetSchoolHtml getSchoolHtml ,OnTaskCompleted listener) {
        this.getSchoolHtml = getSchoolHtml;
        this.listener = listener;
    }

    @Override
    protected List<List<String>> doInBackground(Void... voids) {
        getSchoolHtml.Get();
        Log.d("Html", getSchoolHtml.convertHtmlToJson(getSchoolHtml.apiConstant.responseJson).toString());
        return getSchoolHtml.convertHtmlToJson(getSchoolHtml.apiConstant.responseJson);
    }

    @Override
    protected void onPostExecute(List<List<String>> result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result);

    }
}