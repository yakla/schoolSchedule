package com.main;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

class NetworkOperationAsyncTask extends AsyncTask<Void, Void, List<List<String>>> {
    NetworkWorker getSchoolHtml;
    private final OnTaskCompleted listener;

    interface OnTaskCompleted {
        void onTaskCompleted(List<List<String>> result);
    }

    public NetworkOperationAsyncTask(NetworkWorker getSchoolHtml, OnTaskCompleted listener) {
        this.getSchoolHtml = getSchoolHtml;
        this.listener = listener;
    }

    @Override
    protected List<List<String>> doInBackground(Void... voids) {
        getSchoolHtml.Get();
        try {
        if (getSchoolHtml.convertHtmlToJson(getSchoolHtml.apiConstant.responseJson) != null) {

                Log.d("Html", getSchoolHtml.convertHtmlToJson(getSchoolHtml.apiConstant.responseJson).toString());
                return getSchoolHtml.convertHtmlToJson(getSchoolHtml.apiConstant.responseJson);
            }

        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<List<String>> result) {
        if (result != null) {
            super.onPostExecute(result);
            if (!getSchoolHtml.apiConstant.isOffline) {
                listener.onTaskCompleted(result);
            }
        }
    }
}