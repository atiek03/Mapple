package com.allen.mapple;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;


public class RegistrationActivity extends Activity {

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlogin);

        emailText = (EditText) findViewById(R.id.email_register_edit);
        passwordText = (EditText) findViewById(R.id.password_register_edit);
        loginButton = (Button) findViewById(R.id.do_register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterTask().execute();
            }
        });
    }

    class RegisterTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);
            URI uri = URI.create("http://tuvistavie.com:3000/api/users");
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept", "application/json");

            JSONObject json = new JSONObject();
            byte[] message = new byte[1];
            try {
                JSONObject user = new JSONObject();
                user.put("email", emailText.getText().toString());
                user.put("password", passwordText.getText().toString());
                json.put("user", user);
                message = json.toString().getBytes("UTF8");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            request.setEntity(new ByteArrayEntity(message));
            try {
                HttpResponse response = client.execute(request);
                Log.d("FOOBARFOO", response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
