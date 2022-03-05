package com.example.computerstarter.Others;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;


public class AccountActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    TextView nameUser,home;
    TextView ageUser;
    TextView email;
    TextView name;
    Button logOut;
    ImageView profile;
    ImageView profileImage;
    StorageReference storageReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        name = findViewById(R.id.usersName);
        nameUser = findViewById(R.id.Name);
        ageUser = findViewById(R.id.text_Age);
        nameUser.setText(user.getDisplayName());
        name.setText(user.getDisplayName());
        email = findViewById(R.id.text_Email);
        email.setText(user.getEmail());
        logOut = findViewById(R.id.logOut);
        profile = findViewById(R.id.change_profile);
        profileImage = findViewById(R.id.profileImage);
        home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .putExtra("from","Main"));
            overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
        });
        //profileImage.setImageURI(user.getPhotoUrl());
        DocumentReference documentReference = db.collection("Users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> user_data = documentSnapshot.getData();
                String age = user_data.get("Age").toString();
                ageUser.setText(age);
            } else {
                Toast.makeText(AccountActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
            }
        });
        logOut.setOnClickListener(view->{
            mAuth.signOut();
            startActivity(new Intent(AccountActivity.this,MainActivity.class)
                    .putExtra("from","Main"));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
        profile.setOnClickListener(view -> {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent,1000);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri image = data.getData();
                profileImage.setImageURI(image);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(image).build();
                user.updateProfile(profileUpdates);
            }
        }
    }
    }
