package com.example.computerstarter;

import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class real_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, password;
    boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.bLogin);
        Button register = findViewById(R.id.bNewAccount);
        login.setOnClickListener(view -> {
            loginButtonClicked(view);
            logged = true;
        });
        register.setOnClickListener(view -> {
            Intent intent = new Intent(real_login.this,real_register.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            Intent intent = new Intent(real_login.this,MainActivity.class);
            startActivity(intent);
            return true;
        }else
            return super.onOptionsItemSelected(item);
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
                .addOnCompleteListener(real_login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(real_login.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("MyTag", task.getException().toString());
                        } else {
                            Intent intent = new Intent(real_login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
