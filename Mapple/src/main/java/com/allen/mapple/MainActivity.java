package com.allen.mapple;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = null;
        if(savedInstanceState != null) {
            token = savedInstanceState.getString("auth_token");
        }
        if(token == null) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
