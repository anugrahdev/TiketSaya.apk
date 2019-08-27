package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterAct extends AppCompatActivity {

    Button btn_explore;
    ImageView app_img_success;
    TextView app_text;
    TextView app_subtext;
    Animation app_splash;
    Animation ttb;
    Animation btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //load
        btn_explore = findViewById(R.id.btn_explore);
        app_img_success = findViewById(R.id.app_img_success);
        app_text = findViewById(R.id.app_text);
        app_subtext = findViewById(R.id.app_subtext);
        app_splash = AnimationUtils.loadAnimation(this,  R.anim.app_splash);
        ttb = AnimationUtils.loadAnimation(this,  R.anim.top_to_bottom);
        btt = AnimationUtils.loadAnimation(this,  R.anim.bottom_to_top);

        app_img_success.startAnimation(app_splash);
        app_text.startAnimation(ttb);
        app_subtext.startAnimation(ttb);
        btn_explore.startAnimation(btt);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(SuccessRegisterAct.this,HomeAct.class);
                startActivity(gotoHome);
            }
        });
    }
}
