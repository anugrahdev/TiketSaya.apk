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

public class GetStartedAct extends AppCompatActivity {

    Button button_sign_in;
    Button button_new_account;
    ImageView white_logo;
    TextView app_tagline;
    Animation top_to_bottom;
    Animation bottom_to_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        // LOAD

        top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        button_sign_in = findViewById(R.id.button_sign_in);
        button_new_account = findViewById(R.id.btn_new_account);
        white_logo = findViewById(R.id.app_white_logo);
        app_tagline = findViewById(R.id.app_tagline);
        //ANIMATE

        white_logo.startAnimation(top_to_bottom);
        app_tagline.startAnimation(top_to_bottom);
        button_sign_in.startAnimation(bottom_to_top);
        button_new_account.startAnimation(bottom_to_top);

        // SIGN IN

        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSign = new Intent(GetStartedAct.this, SignInAct.class);
                startActivity(gotoSign);
            }
        });

        // NEW ACCOUNT

        button_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterone = new Intent(GetStartedAct.this, RegisterOneAct.class);
                startActivity(gotoRegisterone);
            }
        });
    }
}
