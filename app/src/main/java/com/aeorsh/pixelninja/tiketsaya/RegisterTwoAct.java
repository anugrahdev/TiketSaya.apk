package com.aeorsh.pixelninja.tiketsaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        getUsernameLocal();

        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);
        btn_add_pic = findViewById(R.id.btn_add_pic);
        pic_register_user=findViewById(R.id.pic_register_user);
        nama_lengkap=findViewById(R.id.nama_lengkap);
        bio=findViewById(R.id.bio);

        btn_add_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ubah menjadi loading

                final String input_nama_lengkap = nama_lengkap.getText().toString();
                final String input_bio = bio.getText().toString();

                if (input_nama_lengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama kosong!", Toast.LENGTH_SHORT).show();
                }else{
                    if (input_bio.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Bio/Passion kosong!", Toast.LENGTH_SHORT).show();
                    }else{
                        btn_continue.setEnabled(false);
                        btn_continue.setText("Loading...");
                        //menyimpan pada firebase
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                        storage = FirebaseStorage.getInstance().getReference().child("Photo_users").child(username_key_new);

                        // validasi userfile (ada ?)
                        if (photo_location != null){
                            StorageReference storageReference = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                            storageReference.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            reference.getRef().child("url_photo_profile").setValue(uri.toString());
                                        }
                                    });

                                    reference.getRef().child("nama_lengkap").setValue(input_nama_lengkap);
                                    reference.getRef().child("bio").setValue(input_bio);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    Intent gotoSuccessPage = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                                    startActivity(gotoSuccessPage);
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "Foto kosong!", Toast.LENGTH_SHORT).show();
                            btn_continue.setEnabled(true);
                            btn_continue.setText("CONTINUE");
                        }
                    }
                }





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

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic,photo_max);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_register_user);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");

    }
}
