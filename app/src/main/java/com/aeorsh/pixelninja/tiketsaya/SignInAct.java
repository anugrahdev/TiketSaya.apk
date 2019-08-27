package com.aeorsh.pixelninja.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername,xpassword;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.username);
        xpassword = findViewById(R.id.password);


        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegisterone = new Intent(SignInAct.this,RegisterOneAct.class);
                startActivity(gotoRegisterone);
            }
        });


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username kosong!", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }
                else{
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password kosong!", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }else{
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
//                            Toast.makeText(getApplicationContext(), "Username ada!", Toast.LENGTH_SHORT).show();
                                    //ambil data password
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                    //Validasi dengan password firebase
                                    if (password.equals(passwordFromFirebase)){

                                        // simpan username pada lokal
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        btn_sign_in.setEnabled(false);
                                        btn_sign_in.setText("Loading...");


                                        //pindah activity
                                        Intent gotoHome = new Intent(SignInAct.this, HomeAct.class);
                                        startActivity(gotoHome);
                                        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "Username atau Password salah!", Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }


                                }else {
                                    Toast.makeText(getApplicationContext(), "Username atau Password salah!", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }



            }
        });
    }


}
