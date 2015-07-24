package com.example.winters.express;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winters on 15/7/20.
 */
public class HttpUtil {
    private String address;

    public HttpUtil(String address) {
        this.address = address;
    }

    public String sendHttpRequest() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(this.address);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String postHttpRequest(ArrayList<NameValuePair> parmList) {
        try {
            HttpPost httpPost = new HttpPost(this.address);
            List<NameValuePair> parms = parmList;
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parms, "utf-8");
            httpPost.setEntity(entity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpResponseEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpResponseEntity, "utf-8");
                return response;
            } else {
                return "Connection error!!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
