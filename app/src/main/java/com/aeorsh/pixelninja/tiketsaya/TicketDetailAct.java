package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TicketDetailAct extends AppCompatActivity {
    Button btn_buy_ticket;
    LinearLayout btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCheckout = new Intent(TicketDetailAct.this,TicketCheckoutAct.class);
                startActivity(gotoCheckout);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoHome = new Intent(TicketDetailAct.this,HomeAct.class);
                startActivity(backtoHome);
            }
        });
    }
}
