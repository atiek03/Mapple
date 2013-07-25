package com.allen.mapple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
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


public class RegistrationActivity extends Activity {

    private final static String TAG = "com.allen.mapple.RegistrationActivity";

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private UserApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlogin);

        emailText = (EditText) findViewById(R.id.email_register_edit);
        passwordText = (EditText) findViewById(R.id.password_register_edit);
        loginButton = (Button) findViewById(R.id.do_register_button);

        api = new UserApi(MainActivity.BASE_URL);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterTask().execute();
            }
        });
    }

    class RegisterTask extends AsyncTask<Void, Void, User> {

        protected User doInBackground(Void... params) {
            return api.createUser(emailText.getText().toString(), passwordText.getText().toString());
        }

        @Override
        protected void onPostExecute(User user) {
            if(user == null) {
                setResult(RESULT_CANCELED);
            } else {
                Intent returnIntent = new Intent();
                Log.d(TAG, "user created: " + user.toString());
                returnIntent.putExtra("user", user);
                setResult(RESULT_OK, returnIntent);
            }
            finish();
        }
    }
}
