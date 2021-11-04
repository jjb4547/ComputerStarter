package com.example.computerstarter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class real_register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,user,pass,name,age;
    private DatabaseReference mData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.etPassword);
        user = findViewById(R.id.etUsername);
        age = findViewById(R.id.etAge);
        name = findViewById(R.id.etName);
    }

    public void onRegisterClicked(View view){
        String emailInput = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        final String username = user.getText().toString().trim();
        int userage = Integer.parseInt(age.getText().toString());
        String user_name = name.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(),"Enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(emailInput)){
            Toast.makeText(getApplicationContext(),"Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userage<13){
            Toast.makeText(getApplicationContext(), "Sorry too young for an account!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailInput,password)
                .addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        Toast.makeText(real_register.this,"User Created", Toast.LENGTH_SHORT).show();
                        current = mAuth.getCurrentUser().getUid();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user_name).build();
                        firebaseUser.updateProfile(profileUpdates);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Username",username);
                        user.put("Name",user_name);
                        user.put("Age",userage);
                        user.put("Email",emailInput);
                        user.put("Built",false);
                        user.put("Build 1","build_1");
                        user.put("Build 2","build_2");
                        user.put("Build 3","build_3");
                        user.put("Build 4","build_4");
                        DocumentReference documentReference = db.collection("Users").document(current);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Log.d("My Tag","Email Sent");
                                    }
                                });
                                mAuth.signOut();
                            }
                        });
                        startActivity(new Intent(real_register.this,real_login.class));
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            Intent intent = new Intent(real_register.this,real_login.class);
            startActivity(intent);
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}