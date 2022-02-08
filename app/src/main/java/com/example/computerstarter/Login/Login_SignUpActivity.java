package com.example.computerstarter.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login_SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password,name,age,signEmail,signPass;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String current;
    private ArrayList<String> build_name;
    private ArrayList<String> build_date;
    private ArrayList<Integer> price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login_layout);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signEmail = findViewById(R.id.etEmailSign);
        signPass = findViewById(R.id.etPasswordSign);
        name = findViewById(R.id.etName);
        age = findViewById(R.id.etAge);
        build_name = new ArrayList<>();
        build_date = new ArrayList<>();
        price = new ArrayList<>();
        TextView loginButton = findViewById(R.id.login);
        TextView home = findViewById(R.id.home);
        TextView signUpButton = findViewById(R.id.signUp);
        LinearLayout loginLayouts = findViewById(R.id.loginLayout);
        LinearLayout signUpLayouts = findViewById(R.id.signUpLayout);
        Button signInButton = findViewById(R.id.signIn);
        loginButton.setOnClickListener(view -> {
            loginButton.setBackground(getResources().getDrawable(R.drawable.switch_trcks));
            loginButton.setTextColor(getResources().getColor(R.color.white));
            signUpButton.setBackground(null);
            loginLayouts.setVisibility(View.VISIBLE);
            signUpLayouts.setVisibility(View.GONE);
            signUpButton.setTextColor(getResources().getColor(R.color.cardview_light));
            signInButton.setText("Log In");
        });
        signUpButton.setOnClickListener(view -> {
            signUpButton.setBackground(getResources().getDrawable(R.drawable.switch_trcks));
            signUpButton.setTextColor(getResources().getColor(R.color.white));
            loginButton.setBackground(null);
            signUpLayouts.setVisibility(View.VISIBLE);
            loginLayouts.setVisibility(View.GONE);
            loginButton.setTextColor(getResources().getColor(R.color.cardview_light));
            signInButton.setText("Sign Up");
        });
        signInButton.setOnClickListener(view->{
            if(signUpLayouts.getVisibility()==View.VISIBLE){
                onRegisterClicked(view);
            }else{
                loginButtonClicked(view);
            }
        });
        home.setOnClickListener(view->{
            startActivity(new Intent(Login_SignUpActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
    }
    public void loginButtonClicked(View view){
        String uemail = email.getText().toString();
        final String pass = password.getText().toString();
        if (TextUtils.isEmpty(uemail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            password.setError("Should be greater than 6");
        }
        mAuth.signInWithEmailAndPassword(uemail, pass)
                .addOnCompleteListener(Login_SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(Login_SignUpActivity.this, "Authentication failed. \nWRONG EMAIL/PASSWORD!!!", Toast.LENGTH_LONG).show();
                            Toast.makeText(Login_SignUpActivity.this,"WRONG EMAIL/PASSWORD!!!",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Login_SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
    public void onRegisterClicked(View view){
        String emailInput = signEmail.getText().toString().trim();
        String password = signPass.getText().toString().trim();
        int userage = Integer.parseInt(age.getText().toString());
        String user_name = name.getText().toString();
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
                        //ArrayList<Build_Activity> build_data = new ArrayList<>();
                        Toast.makeText(Login_SignUpActivity.this,"User Created", Toast.LENGTH_SHORT).show();
                        current = mAuth.getCurrentUser().getUid();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user_name).build();
                        firebaseUser.updateProfile(profileUpdates);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Age",userage);
                        user.put("build_date",build_date);
                        user.put("build_name",build_name);
                        user.put("price",price);
                        DocumentReference documentReference = db.collection("Users").document(current);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Toast.makeText(Login_SignUpActivity.this, "Sign Up Complete!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                mAuth.signOut();
                            }
                        });
                    }
                });
    }
}
