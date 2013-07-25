package com.allen.mapple;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

public class UserApi {

    private static final String TAG = "com.allen.mapple.UserApi";

    protected  String baseUrl;

    public UserApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public User createUser(String email, String password) {
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("email", email);
            userObject.put("password", password);
            return sendForUser("/api/users", userObject);
        } catch (JSONException e) {
            return null;
        }
    }

    public User loginUser(String email, String password) {
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("email", email);
            userObject.put("password", password);
            return sendForUser("/api/users/sign_in", userObject);
        } catch (JSONException e) {
            return null;
        }
    }

    public User sendForUser(String path, JSONObject userObject) {
        int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
        HttpClient client = new DefaultHttpClient(httpParams);
        URI uri = URI.create(baseUrl + path);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("user", userObject);
            request.setEntity(new StringEntity(json.toString()));
            HttpResponse response = client.execute(request);
            JSONObject responseObject = new JSONObject(EntityUtils.toString(response.getEntity()));
            Log.d(TAG, "received response: " + responseObject.toString());
            if(responseObject.getBoolean("success")) {
                return User.fromJson(responseObject.getJSONObject("user"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
