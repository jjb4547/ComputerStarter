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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class real_register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,user,pass,name,age;
    private DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.etPassword);
        user = findViewById(R.id.etUsername);
        age = findViewById(R.id.etAge);
        name = findViewById(R.id.etName);
        Button register = findViewById(R.id.bRegister);
        register.setOnClickListener(view -> {
            onRegisterClicked(view);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(this, MainPageFragment.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        mAuth.createUserWithEmailAndPassword(emailInput,password)
                .addOnCompleteListener(real_register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(real_register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if(!task.isSuccessful()){
                            Toast.makeText(real_register.this,"Authentication Failed."+task.getException(),Toast.LENGTH_LONG).show();
                            Log.e("MyTag",task.getException().toString());
                        }else{
                            String current = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            if(current!=null)
                                writeUser(current, username, emailInput,userage,user_name);
                            startActivity(new Intent(real_register.this,real_login.class));
                            finish();
                        }
                    }
                });

    }

    public void writeUser(String userId, String name, String email, int age, String username){
        User user = new User(name,email,age,username);
        mData.child("Users").child(userId).setValue(user);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}