package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        TextView etName = findViewById(R.id.etName);
        TextView etAge = findViewById(R.id.etAge);
        TextView etUsername = findViewById(R.id.etUsername);
        Button logout = findViewById(R.id.bLogOut);
        logout.setOnClickListener(view -> {
            if(user!=null) {
                mAuth.signOut();
                Toast.makeText(this, user.getEmail() + " Sign Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountActivity.this,real_login.class));
            }else{
                Toast.makeText(this,"YOU ARE NOT LOGGED IN!!!",Toast.LENGTH_SHORT).show();
            }
        });
        if(user!=null) {
            String current = user.getUid();
            DocumentReference documentReference = db.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> user_data = documentSnapshot.getData();
                        etName.setText(user_data.get("Name").toString());
                        etAge.setText(user_data.get("Age").toString());
                        etUsername.setText(user_data.get("Username").toString());
                    } else {
                        Toast.makeText(AccountActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(AccountActivity.this,"You are not Logged In",Toast.LENGTH_SHORT).show();
        }
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
