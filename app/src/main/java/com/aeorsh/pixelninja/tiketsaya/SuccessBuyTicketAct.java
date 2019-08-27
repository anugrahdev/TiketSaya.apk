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

public class SuccessBuyTicketAct extends AppCompatActivity {

    Button btn_view_ticket, btn_my_dashboard;
    ImageView img_success_buy;
    TextView text_title, text_subtitle;
    Animation app_splash, btt, ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        //load
        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        img_success_buy = findViewById(R.id.img_success_buy);
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        ttb = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);

        img_success_buy.setAnimation(app_splash);
        text_title.setAnimation(ttb);
        text_subtitle.setAnimation(ttb);
        btn_my_dashboard.setAnimation(btt);
        btn_view_ticket.setAnimation(btt);

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDashboard = new Intent(SuccessBuyTicketAct.this, HomeAct.class);
                startActivity(gotoDashboard);
            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoTicket = new Intent(SuccessBuyTicketAct.this, MyTicketDetailAct.class);
                startActivity(gotoTicket);
            }
        });

    }
}
