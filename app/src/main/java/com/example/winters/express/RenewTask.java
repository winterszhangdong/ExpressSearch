package com.example.winters.express;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by winters on 15/7/24.
 */
public class RenewTask extends AsyncTask<String, Void, String> {

    private TextView textView;
    private ProgressBar progressBar;
    private ArrayList<NameValuePair> parmList;

    public RenewTask(TextView textView,ProgressBar progressBar, ArrayList<NameValuePair> parmList) {
        super();
        this.textView = textView;
        this.progressBar = progressBar;
        this.parmList = parmList;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpUtil httpUtil = new HttpUtil(params[0]);
//        String response = httpUtil.sendHttpRequest();
        return httpUtil.postHttpRequest(this.parmList);
    }

    @Override
    protected void onPostExecute(String response) {
        Log.d("Response: -----> ", response);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("1")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject expressItem = dataArray.getJSONObject(i);
                    String time = expressItem.getString("time");
                    String context = expressItem.getString("context");
                    stringBuilder.append(time + "\n");
                    stringBuilder.append(context + "\n\n");
                }
            } else stringBuilder.append(jsonObject.getString("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.progressBar.setVisibility(View.GONE);
        this.textView.setVisibility(View.VISIBLE);
        this.textView.setText(stringBuilder.toString());
    }
}
