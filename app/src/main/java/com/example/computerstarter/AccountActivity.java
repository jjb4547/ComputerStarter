package com.example.computerstarter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.util.LinkedList;

public class AccountActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name = findViewById(R.id.name);
        TextView age = findViewById(R.id.age);
        TextView username =findViewById(R.id.username);
        EditText etName = findViewById(R.id.etName);
        EditText etAge = findViewById(R.id.etAge);
        EditText etUsername = findViewById(R.id.etUsername);
        Button logout = findViewById(R.id.bLogOut);
        logout.setOnClickListener(view -> {
            if(user!=null) {
                mAuth.signOut();
                Toast.makeText(this, user.getEmail() + " Sign Out!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"YOU ARE NOT LOGGED IN!!!",Toast.LENGTH_SHORT).show();
            }
        });
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
