package com.emc.recipester.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.emc.recipester.R;

public class SplashActivity extends AppCompatActivity {

    private static long SPLASH_TIMED_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this, CategoryActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIMED_OUT);
    }
}
