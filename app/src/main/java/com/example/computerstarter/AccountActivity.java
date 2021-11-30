package com.example.computerstarter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;


public class AccountActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nameUser;
    TextView ageUser;
    TextView userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        nameUser = findViewById(R.id.etName);
        ageUser = findViewById(R.id.etAge);
        userName = findViewById(R.id.etUsername);
        nameUser.setText(user.getDisplayName());
        DocumentReference documentReference = db.collection("Users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> user_data = documentSnapshot.getData();
                String age = user_data.get("Age").toString();
                String nameData = user_data.get("Username").toString();
                userName.setText(nameData);
                ageUser.setText(age);
            } else {
                Toast.makeText(AccountActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
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
