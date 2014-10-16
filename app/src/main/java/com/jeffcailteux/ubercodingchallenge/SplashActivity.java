package com.jeffcailteux.ubercodingchallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //if logged in, fade to home
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    //Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    //startActivity(i);
                    //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 1500);



    }
}
