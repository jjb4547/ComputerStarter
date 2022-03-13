package com.example.computerstarter.Others;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.R;
import com.example.computerstarter.app.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        initVar();
        getProfile();
        getDocs();
        clickListeners();
    }

    private void initVar() {
        mAuth = FirebaseAuth.getInstance();
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
    }

    private void getProfile() {
        StorageReference profileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).fit().into(profileImage);
        });
    }

    private void getDocs() {
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
    }


    private void clickListeners() {
        logOut.setOnClickListener(view->{
            mAuth.signOut();
            startActivity(new Intent(AccountActivity.this, MainBuilds.class)
                    .putExtra("from","Main"));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
        profile.setOnClickListener(view -> {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGalleryIntent,1000);
        });
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri image = data.getData();
                //profileImage.setImageURI(image);
                try {
                    Bitmap original = MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    original.compress(Bitmap.CompressFormat.JPEG,15,stream);
                    byte[] imageByte = stream.toByteArray();
                    uploadImageToFirebase(imageByte);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImageToFirebase(byte[] image) {
        ProgressDialog progressDialog = new ProgressDialog(AccountActivity.this);
        progressDialog.setMessage("Image uploading ....");
        progressDialog.show();
        StorageReference fileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putBytes(image).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(AccountActivity.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
        });
    }
}
