package com.aeorsh.pixelninja.tiketsaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {
    Button btn_continue,btn_add_pic;
    LinearLayout btn_back;
    ImageView pic_register_user;
    EditText bio,nama_lengkap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        btn_add_pic = findViewById(R.id.btn_add_pic);
        pic_register_user=findViewById(R.id.pic_register_user);
        nama_lengkap=findViewById(R.id.nama_lengkap);
        bio=findViewById(R.id.bio);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent gotoSuccessPage = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                startActivity(gotoSuccessPage);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoRegOne = new Intent(RegisterTwoAct.this, RegisterOneAct.class);
                startActivity(backtoRegOne);
            }
        });



    }


}
