package com.uv.programme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    public static String CONTEST_QUESTION_TAG, CONTEST_STARS_TAG;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        CONTEST_QUESTION_TAG = getResources().getString(R.string.contest_question_tag);
        CONTEST_STARS_TAG = getResources().getString(R.string.contest_stars_tag);
        Photo.CONTEST_QUESTION_TAG = CONTEST_QUESTION_TAG;
        Photo.CONTEST_STARS_TAG = CONTEST_STARS_TAG;

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, NewHomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
