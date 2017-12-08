package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class ClockSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_splash);

        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                finish();
                Intent searchIntent = new Intent(ClockSplashActivity.this, SearchByInstructorActivity.class);
                startActivity(searchIntent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
    }

