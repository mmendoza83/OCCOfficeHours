package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Activity for a splash screen. Displays the activity_splash.xml.
 *
 * Created by mmendoza on 12/1/2017.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                finish();
                Intent searchIntent = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        };

        // Delays the start of the SearchActivity, displaying the splash screen for 3 seconds.
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
