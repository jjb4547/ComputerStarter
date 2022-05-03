package com.example.computerstarter.Others;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.Build.MyBuildActivity;
import com.example.computerstarter.Education.PC_Part_Activity;
import com.example.computerstarter.Guides.Guides_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.R;
import com.example.computerstarter.SampleProjects.SampleProjects;
import com.example.computerstarter.app.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;


public class AccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    TextView nameUser;
    ImageButton home,menuButton;
    TextView ageUser;
    TextView email;
    TextView name;
    Button logOut;
    ImageView profile;
    ImageView profileImage;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView log;
    private MenuItem item_log;
    private MenuItem item_acc;
    private MaterialButton loginBut;
    private TextView loginText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        initVar();
        getProfile();
        getDocs();
        clickListeners();
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        toggle.syncState();
        menuButton = findViewById(R.id.menuButton);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_acc = menu.findItem(R.id.account);
        log = findViewById(R.id.log_Text);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
        }
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profile = headerView.findViewById(R.id.myimage);
        if(user!=null) {
            String current = user.getUid();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            StorageReference profileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile");
            profileRef.getDownloadUrl().addOnSuccessListener(uri ->{
                Picasso.get().load(uri).into(profile);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri).build();
                user.updateProfile(profileUpdates);
            });
            //profile.setImageURI(user.getPhotoUrl()); //bugging out not sure why but only on the emulator
        }else{
            name.setText("Guest");
            email.setText("Guest Not Signed In");
        }
        log.setOnClickListener(view->{
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                log.setText("Log In");
                item_acc.setVisible(false);
                startActivity(new Intent(AccountActivity.this, Login_SignUpActivity.class));
            }else{
                startActivity(new Intent(AccountActivity.this, Login_SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });
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
        StorageReference profileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile");
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
                    original.compress(Bitmap.CompressFormat.PNG,15,stream);
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
        StorageReference fileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile");
        fileRef.putBytes(image).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(AccountActivity.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_acc = menu.findItem(R.id.account);
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                //Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(this, AccountActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
                }else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.partsMenu:
                startActivity(new Intent(AccountActivity.this, PC_Part_Activity.class).putExtra("Act","Edu"));
                break;
            case R.id.guidesMenu:
                startActivity(new Intent(AccountActivity.this, Guides_Activity.class));
                break;
            case R.id.projectsMenu:
                startActivity(new Intent(AccountActivity.this, SampleProjects.class));
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return true;
    }
}
