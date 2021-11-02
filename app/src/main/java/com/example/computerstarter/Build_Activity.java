package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Build_Activity extends AppCompatActivity {
    private String[] diffTitles;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Boolean built_something;
    private Boolean isMenuOpen = false;
    FloatingActionButton floatingActionButton;
    FloatingActionButton saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPageFragment main = new MainPageFragment();
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Build");
        getSupportActionBar().setTitle(name);
        TextView textView = findViewById(R.id.title);
        CardView cpu = findViewById(R.id.cpu);
        CardView mot = findViewById(R.id.mot);
        CardView mem = findViewById(R.id.mem);
        CardView vga = findViewById(R.id.gpu);
        CardView psu = findViewById(R.id.psu);
        CardView stor = findViewById(R.id.stor);
        CardView mon = findViewById(R.id.mon);
        CardView cool = findViewById(R.id.cool);
        CardView pc_case = findViewById(R.id.id_case);
        Map<String,Object> user = new HashMap<>();
        floatingActionButton = findViewById(R.id.fab_parts);
        saveButton = findViewById(R.id.fab_save);
        saveButton.setAlpha(0f);
        saveButton.setTranslationY(100f);
        cpu.setOnClickListener(view -> {
            Intent cpu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            cpu_intent.putExtra("name","CPU");
            startActivity(cpu_intent);
        });
        mot.setOnClickListener(view -> {
            Intent mot_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mot_intent.putExtra("name","Motherboards");
            startActivity(mot_intent);
        });
        mem.setOnClickListener(view -> {
                Intent mem_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                mem_intent.putExtra("name","Memory");
                startActivity(mem_intent);
        });
        vga.setOnClickListener(view -> {
                Intent vga_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                vga_intent.putExtra("name","Video Cards");
                startActivity(vga_intent);
        });
        psu.setOnClickListener(view -> {
                Intent psu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                psu_intent.putExtra("name","Power Supplies");
                startActivity(psu_intent);
        });
        stor.setOnClickListener(view -> {
                Intent stor_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                stor_intent.putExtra("name","Storage");
                startActivity(stor_intent);
        });
        if(mAuth.getCurrentUser()!=null) {
            String current = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = db.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> user_data = documentSnapshot.getData();
                        built_something = Boolean.parseBoolean(user_data.get("Built").toString());
                        if(!built_something){
                            user.put("Build 1",name);
                        }
                        //email.setText(user_data.get("Email").toString());
                    } else {
                        //Toast.makeText(MainActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        //user.put()
        floatingActionButton.setOnClickListener(view -> {
            if(isMenuOpen){
                closeMenu();
            }else{
                openMenu();
            }
        });
        saveButton.setOnClickListener(view -> {
            if(mAuth.getCurrentUser()!=null){
                user.put("Built",true);
                firestore.collection("Users").document(mAuth.getCurrentUser().getUid()).update(user);
            }else{
                Toast.makeText(this,"NOT LOGGED IN, CANNOT SAVE",Toast.LENGTH_SHORT);
            }
        });
    }

    private void openMenu(){
        isMenuOpen = !isMenuOpen;
        floatingActionButton.animate().rotation(45f).setDuration(300).start();
        saveButton.animate().translationY(0f).alpha(1f).setDuration(300).start();
    }
    private void closeMenu(){
        isMenuOpen = !isMenuOpen;
        floatingActionButton.animate().rotation(45f).setDuration(300).start();
        saveButton.animate().translationY(100f).alpha(0f).setDuration(300).start();
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
