package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TicketCheckoutAct extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_buy_now;
    ImageView btn_minus, btn_plus, img_notice_saldo;
    TextView text_jumlah_tiket, text_saldo, text_total_harga;
    Integer value_jumlah_tiket=1, value_total_harga= 0, value_harga_tiket=75, saldo = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);
        //load
        btn_buy_now = findViewById(R.id.btn_buy_now);
        btn_minus = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        img_notice_saldo = findViewById(R.id.img_notice_saldo);
        text_jumlah_tiket = findViewById(R.id.text_jumlahtiket);
        text_saldo = findViewById(R.id.text_saldo);
        text_total_harga = findViewById(R.id.text_total_harga);

        //set default value
        text_jumlah_tiket.setText(value_jumlah_tiket.toString());
//        text_total_harga.setText("US$ "+value_total_harga+ "");
        text_saldo.setText("US$ "+saldo+ "");
        img_notice_saldo.setVisibility(View.GONE);

        //default hide minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        value_total_harga = value_harga_tiket * value_jumlah_tiket;
        text_total_harga.setText("US$ "+value_total_harga+"");

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jumlah_tiket += 1;
                text_jumlah_tiket.setText(value_jumlah_tiket.toString());
                if (value_jumlah_tiket > 1 ){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                value_total_harga = value_harga_tiket * value_jumlah_tiket;
                text_total_harga.setText("US$ "+value_total_harga+"");
                if (value_total_harga > saldo){
                    btn_buy_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_now.setEnabled(false);
                    text_saldo.setTextColor(Color.RED);
                    img_notice_saldo.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jumlah_tiket -= 1;
                text_jumlah_tiket.setText(value_jumlah_tiket.toString());
                if (value_jumlah_tiket < 2 ){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }

                value_total_harga = value_harga_tiket * value_jumlah_tiket;
                text_total_harga.setText("US$ "+value_total_harga+"");
                if (value_total_harga < saldo){
                    btn_buy_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_now.setEnabled(true);
                    text_saldo.setTextColor(Color.parseColor("#203DD1"));
                    img_notice_saldo.setVisibility(View.GONE);
                }
            }
        });



        //event

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoHome = new Intent(TicketCheckoutAct.this,TicketDetailAct.class);
                startActivity(backtoHome);
            }
        });
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoSuccessBuy = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                startActivity(backtoSuccessBuy);
            }
        });
    }


}
