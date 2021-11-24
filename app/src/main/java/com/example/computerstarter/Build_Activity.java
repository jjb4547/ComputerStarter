package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Build_Activity extends AppCompatActivity {
    private String[] diffTitles;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    DocumentReference build_ref = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
    private int built_something;
    private Boolean isMenuOpen = false;
    private ArrayList<Build_Data> build_data = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    FloatingActionButton saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPageFragment main = new MainPageFragment();
        //build_data = new ArrayList<>();
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name_action_bar = intent.getExtras().getString("Build");
        getSupportActionBar().setTitle(name_action_bar);
        ArrayList<String> build_names = new ArrayList<>();
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
            DocumentReference documentReference = firestore.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Map<String, Object> user_data = documentSnapshot.getData();
                    built_something = Integer.parseInt(user_data.get("numOfBuilds").toString());
                    //final String timestamp = String.valueOf(System.currentTimeMillis());
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Map<String,Object> user = new HashMap<>();
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Build");
        int id = item.getItemId();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            startActivity(new Intent(this,MyBuildActivity.class));
            return true;
        }else if(id==R.id.save_button){
            if(mAuth.getCurrentUser()!=null){
                build_ref.update("build_name",name);
                build_ref.update("build_date",currentDateandTime);
                build_ref.update("price",0);
                firestore.collection("Users").document(mAuth.getCurrentUser().getUid()).update(user);
            }else{
                Toast.makeText(this,"NOT LOGGED IN, CANNOT SAVE",Toast.LENGTH_SHORT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
