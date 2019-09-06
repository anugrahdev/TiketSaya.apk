package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username,password, email_address;
    DatabaseReference reference, reference_user;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        //load
        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data pada local storage (handphone)

                final String inputusername =  username.getText().toString();
                final String inputpassword = password.getText().toString();
                final String inputemail = email_address.getText().toString();

                if (inputusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                }else{
                    if (inputpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                    }else if(inputemail.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Email kosong!", Toast.LENGTH_SHORT).show();
                    }else{
                        btn_continue.setEnabled(false);
                        btn_continue.setText("Loading..");
                        reference_user = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                        reference_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    Toast.makeText(getApplicationContext(), "Username telah digunakan!", Toast.LENGTH_SHORT).show();
                                    btn_continue.setEnabled(true);
                                    btn_continue.setText("continue");
                                }else{
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, username.getText().toString());
                                    editor.apply();

                                    //Simpan ke database
                                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            dataSnapshot.getRef().child("username").setValue(inputusername);
                                            dataSnapshot.getRef().child("password").setValue(inputpassword);
                                            dataSnapshot.getRef().child("email_address").setValue(inputemail);
                                            dataSnapshot.getRef().child("user_balance").setValue(0);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    Intent gotonextreg = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                                    startActivity(gotonextreg);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
