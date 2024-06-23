package com.main;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

class NetworkOperationAsyncTask extends AsyncTask<Void, Void, String > {
    GetSchoolHtml getSchoolHtml;
    JSONObject jsonObject;
    private OnTaskCompleted listener;

    interface OnTaskCompleted {
        void onTaskCompleted(String result);
    }
    public NetworkOperationAsyncTask(GetSchoolHtml getSchoolHtml ,OnTaskCompleted listener) {
        this.getSchoolHtml = getSchoolHtml;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        getSchoolHtml.Get();
        return getSchoolHtml.apiConstant.responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result);

    }
}