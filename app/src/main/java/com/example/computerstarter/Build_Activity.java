package com.example.computerstarter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Build_Activity extends AppCompatActivity {
    private String[] diffTitles;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference build_ref = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
    private int built_something;
    private Boolean isMenuOpen = false;
    private Build_Data build_data;
    private String current;
    private int size;
    private CardView cpu;
    private CardView mot;
    private CardView mem;
    private CardView vga;
    private CardView psu;
    private CardView stor;
    private CardView mon;
    private CardView cool;
    private CardView pc_case;
    private MenuItem save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPageFragment main = new MainPageFragment();
        current = mAuth.getCurrentUser().getUid();
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name_action_bar = intent.getExtras().getString("Build");
        getSupportActionBar().setTitle(name_action_bar);
        save = findViewById(R.id.save_button);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Build");
        int id = item.getItemId();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            build_ref.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    build_data = documentSnapshot.toObject(Build_Data.class);
                    if (build_data.getBuild_name().size() < 5) {
                        build_ref.update("build_name", FieldValue.arrayUnion(name));
                        build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                        build_ref.update("price", FieldValue.arrayUnion(built_something));
                        Intent build_intent = new Intent(Build_Activity.this, MyBuildActivity.class);
                        startActivity(build_intent);
                    } else {
                        Toast.makeText(Build_Activity.this, "TOO MANY BUILDS, DELETE ONE!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            startActivity(new Intent(this,MyBuildActivity.class));
            return true;
        }else if(id==R.id.save_button){
            if(checkAuth()){
                build_ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        build_data = documentSnapshot.toObject(Build_Data.class);
                        if (build_data.getBuild_name().size() < 5) {
                            build_ref.update("build_name", FieldValue.arrayUnion(name));
                            build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                            build_ref.update("price", FieldValue.arrayUnion(built_something));
                            Intent build_intent = new Intent(Build_Activity.this, MyBuildActivity.class);
                            startActivity(build_intent);
                        } else {
                            Toast.makeText(Build_Activity.this, "TOO MANY BUILDS, DELETE ONE!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(this,"NOT LOGGED IN, CANNOT SAVE",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started),false);
        if(!previouslyStarted){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started),Boolean.TRUE);
            edit.commit();
            firstRun();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean checkAuth(){
        if(mAuth.getCurrentUser()!=null)
            return true;
        else
            return false;
    }
    public void firstRun(){
        cpuHelper();
    }
    public void cpuHelper(){
        TapTargetView.showFor(this, TapTarget.forView(cpu,"Add a CPU")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                motHelper();
            }
        });
    }
    public void motHelper(){
        TapTargetView.showFor(this, TapTarget.forView(mot,"Add a Motherboard")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                memHelper();
            }
        });
    }
    public void memHelper(){
        TapTargetView.showFor(this, TapTarget.forView(mem,"Add Memory")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                storHelper();
            }
        });
    }
    public void storHelper(){
        TapTargetView.showFor(this, TapTarget.forView(stor,"Add Storage")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                psuHelper();
            }
        });
    }
    public void vgaHelper(){
        TapTargetView.showFor(this, TapTarget.forView(vga,"Add a Graphics Card")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                caseHelper();
            }
        });
    }
    public void monHelper(){
        TapTargetView.showFor(this, TapTarget.forView(mon,"Add a Monitor")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                vgaHelper();
            }
        });
    }
    public void coolHelper(){
        TapTargetView.showFor(this, TapTarget.forView(cool,"Add a CPU Cooler")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                monHelper();
            }
        });
    }
    public void psuHelper(){
        TapTargetView.showFor(this, TapTarget.forView(psu,"Add a Power Supply")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                coolHelper();
            }
        });
    }
    public void caseHelper(){
        TapTargetView.showFor(this, TapTarget.forView(pc_case,"Add a Case","Once done save the build on the top right!")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(40)
                .descriptionTextColor(R.color.white)
                .descriptionTextSize(20)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(100), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                view.dismiss(true);
            }
        });
    }
    public void init(){
        cpu = findViewById(R.id.cpu);
        mot = findViewById(R.id.mot);
        mem = findViewById(R.id.mem);
        vga = findViewById(R.id.gpu);
        psu = findViewById(R.id.psu);
        stor = findViewById(R.id.stor);
        mon = findViewById(R.id.mon);
        cool = findViewById(R.id.cool);
        pc_case = findViewById(R.id.id_case);
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
    }

}
