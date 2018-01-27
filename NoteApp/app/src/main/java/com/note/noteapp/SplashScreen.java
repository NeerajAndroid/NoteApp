package com.note.noteapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
   // Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
       /* setContentView(R.layout.activity_splash_screen);
        mContext = this;


        // Create a Timer
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class
                Intent intent = new Intent(mContext, MainActivity.class);
               *//* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*//*
                intent.putExtra("Splash","Splash");
                startActivity(intent);
                finish();
            }
        };*/

        // Start the timer
        //RunSplash.schedule(ShowSplash, 1000);
    }
}
