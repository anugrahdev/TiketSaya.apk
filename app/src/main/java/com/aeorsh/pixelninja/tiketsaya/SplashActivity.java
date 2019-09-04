package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
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

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getUsernameLocal();

        //load
        app_logo = findViewById(R.id.app_logo);
        app_tagline = findViewById(R.id.app_tagline);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        tagline_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        //run animation
        app_logo.startAnimation(app_splash);
        app_tagline.startAnimation(tagline_to_top);



    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (username_key_new.isEmpty()){
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
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //berpindah acvitivy (intent)
                    Intent goGetHome = new Intent(SplashActivity.this, HomeAct.class);
                    startActivity(goGetHome);
                    finish();
                }
            },2000);
        }

    }
}
