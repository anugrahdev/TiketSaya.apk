package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_buy_now;
    ImageView btn_minus, btn_plus, img_notice_saldo;
    TextView text_jumlah_tiket, text_saldo, text_total_harga, nama_wisata, lokasi,ketentuan;
    Integer value_jumlah_tiket=1, value_total_harga= 0, value_harga_tiket=0, saldo = 0, sisa_saldo=0;

    DatabaseReference referencewisata, referenceuser, referencetiket, reference4;


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";
    //generate nomor integer random
    //unique transaction number
    Integer nomor_transaksi = new Random().nextInt();

    String date_wisata ="", time_wisata="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);
        getUsernameLocal();
        //mengambil data dari intent
        Bundle bundel = getIntent().getExtras();
        String text = bundel.getString("jenis_tiket");

        //load
        btn_buy_now = findViewById(R.id.btn_buy_now);
        btn_minus = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        img_notice_saldo = findViewById(R.id.img_notice_saldo);
        text_jumlah_tiket = findViewById(R.id.text_jumlahtiket);
        text_saldo = findViewById(R.id.text_saldo);
        text_total_harga = findViewById(R.id.text_total_harga);
        nama_wisata =findViewById(R.id.ticket_destination);
        lokasi = findViewById(R.id.ticket_location);
        ketentuan = findViewById(R.id.ketentuan);

        //set default value
        text_jumlah_tiket.setText(value_jumlah_tiket.toString());
        img_notice_saldo.setVisibility(View.GONE);

        //default hide minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);

        //ambil data user  firebase
        referenceuser = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        referenceuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saldo = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                text_saldo.setText("US$ "+saldo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //mengambil data wisata dari firebase ->(intent)
        referencewisata = FirebaseDatabase.getInstance().getReference().child("Wisata").child(text);
        referencewisata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                value_harga_tiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                value_total_harga = value_harga_tiket * value_jumlah_tiket;
                text_total_harga.setText("US$ "+value_total_harga);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





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
                if (value_total_harga < saldo || value_total_harga == saldo){
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
                Intent backtoHome = new Intent(TicketCheckoutAct.this,HomeAct.class);
                startActivity(backtoHome);
            }
        });
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saldo < value_total_harga){
                    btn_buy_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_now.setEnabled(false);
                    text_saldo.setTextColor(Color.RED);
                    img_notice_saldo.setVisibility(View.VISIBLE);

                }else{
                    btn_buy_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_now.setEnabled(true);
                    text_saldo.setTextColor(Color.parseColor("#203DD1"));
                    img_notice_saldo.setVisibility(View.GONE);
                    //menyimpan data pembelian user di firebas dan membuat tabel MyTickets
                    referencetiket = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                            .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                    referencetiket.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            referencetiket.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                            referencetiket.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                            referencetiket.getRef().child("lokasi").setValue(lokasi.getText().toString());
                            referencetiket.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                            referencetiket.getRef().child("jumlah_tiket").setValue(value_jumlah_tiket.toString());
                            referencetiket.getRef().child("date_wisata").setValue(date_wisata);
                            referencetiket.getRef().child("time_wisata").setValue(time_wisata);


                            Intent backtoSuccessBuy = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                            startActivity(backtoSuccessBuy);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    // update saldo user
                    reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                    reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            sisa_saldo = saldo - value_total_harga;
                            reference4.getRef().child("user_balance").setValue(sisa_saldo);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }




            }
        });


    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

    }


}
