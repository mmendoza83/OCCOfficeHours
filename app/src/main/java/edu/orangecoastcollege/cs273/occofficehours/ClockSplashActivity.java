package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class ClockSplashActivity extends AppCompatActivity {

    private ImageView clockAnim;
    private Animation rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_splash);

        clockAnim = (ImageView) findViewById(R.id.clockAnimImageView);

        toggleRotateAnim(findViewById(R.id.clockAnimImageView));

        TimerTask task = new TimerTask()
        {
            @Override
            public void run() {
                finish();
                Intent clockIntent = new Intent(ClockSplashActivity.this, SearchByInstructorActivity.class);
                startActivity(clockIntent);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    public void toggleRotateAnim(View view ) {
        if (rotateAnim == null)// hasnt been initialize yet
        {
            rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        }
        if (!rotateAnim.hasStarted() || rotateAnim.hasEnded())
            clockAnim.startAnimation(rotateAnim);

            //connect it to the image view
        else
            clockAnim.clearAnimation();
    }

    }

