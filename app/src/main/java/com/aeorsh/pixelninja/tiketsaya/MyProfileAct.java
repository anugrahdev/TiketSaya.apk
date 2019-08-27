package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyProfileAct extends AppCompatActivity {
    LinearLayout item_my_ticket;
    Button btn_edit_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        item_my_ticket = findViewById(R.id.item_my_ticket);
        item_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoTicketDetail = new Intent(MyProfileAct.this, MyTicketDetailAct.class);
                startActivity(gotoTicketDetail);
            }
        });

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoEditProfile = new Intent(MyProfileAct.this, EditProfileAct.class);
                startActivity(gotoEditProfile);
            }
        });

    }
}
