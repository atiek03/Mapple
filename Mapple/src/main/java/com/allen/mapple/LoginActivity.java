package com.allen.mapple;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity {

    private final static String TAG = "com.allen.mapple.LoginActivity";

    private UserApi api;

    private Button loginButton;
    private TextView emailText;
    private TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.email_login_edit);
        passwordText = (EditText) findViewById(R.id.password_login_edit);
        loginButton = (Button) findViewById(R.id.make_login_button);

        api = new UserApi(MainActivity.BASE_URL);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginTask().execute();
            }
        });
    }

    class LoginTask extends AsyncTask<Void, Void, User> {

        protected User doInBackground(Void... params) {
            return api.loginUser(emailText.getText().toString(), passwordText.getText().toString());
        }

        @Override
        protected void onPostExecute(User user) {
            if(user == null) {
                setResult(RESULT_CANCELED);
            } else {
                Intent returnIntent = new Intent();
                Log.d(TAG, "user logged in: " + user.toString());
                returnIntent.putExtra("user", user);
                setResult(RESULT_OK, returnIntent);
            }
            finish();
        }
    }
}
