package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation app_splash,tagline_to_top;
    ImageView app_logo;
    TextView app_tagline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load
        app_logo = findViewById(R.id.app_logo);
        app_tagline = findViewById(R.id.app_tagline);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        tagline_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        //run animation
        app_logo.startAnimation(app_splash);
        app_tagline.startAnimation(tagline_to_top);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //berpindah acvitivy (intent)
                Intent gotoGetStarted = new Intent(SplashActivity.this, GetStartedAct.class);
                startActivity(gotoGetStarted);
                finish();
            }
        },2000);
    }
}
