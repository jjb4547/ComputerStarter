package com.example.computerstarter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.Date;

public class Build_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference build_ref;
    private Boolean isMenuOpen = false;
    private Build_Data build_data;
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
    private boolean first=true;
    double[] parts;
    String name;
    String name_action_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPageFragment main = new MainPageFragment();
        if(mAuth.getCurrentUser()!=null)
            build_ref= firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
        else
            Toast.makeText(this, "YOU ARE NOT LOGGED IN, WILL NOT SAVE!!!",Toast.LENGTH_SHORT).show();
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        Build_Data build_data = new Build_Data();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        name_action_bar = intent.getExtras().getString("Build");
        name = intent.getExtras().getString("Name");
        parts = intent.getDoubleArrayExtra("Parts");
        getSupportActionBar().setTitle(name_action_bar);
        save = findViewById(R.id.save_button);
        init();
        for(int i =0;i<parts.length;i++){
            System.out.println("Part"+i+": "+parts[i]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Build");
        int id = item.getItemId();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            if(checkAuth()) {
                build_ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        build_data = documentSnapshot.toObject(Build_Data.class);
                        if (build_data.getBuild_name().size() < 5) {
                            build_ref.update("build_name", FieldValue.arrayUnion(name));
                            build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                            build_ref.update("price", FieldValue.arrayUnion(getPriceSum()));
                            Intent build_intent = new Intent(Build_Activity.this, MyBuildActivity.class);
                            startActivity(build_intent);
                        } else {
                            Toast.makeText(Build_Activity.this, "TOO MANY BUILDS, DELETE ONE!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else
                Toast.makeText(this,"NOT SAVED",Toast.LENGTH_SHORT).show();
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
                            build_ref.update("price", FieldValue.arrayUnion(getPriceSum()));
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
            cpu_intent.putExtra("Parts",parts);
            cpu_intent.putExtra("Build",name_action_bar);
            startActivity(cpu_intent);
        });
        mot.setOnClickListener(view -> {
            Intent mot_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mot_intent.putExtra("name","Motherboards");
            mot_intent.putExtra("Parts",parts);
            mot_intent.putExtra("Build",name_action_bar);
            startActivity(mot_intent);
        });
        mem.setOnClickListener(view -> {
            Intent mem_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mem_intent.putExtra("name","Memory");
            mem_intent.putExtra("Parts",parts);
            mem_intent.putExtra("Build",name_action_bar);
            startActivity(mem_intent);
        });
        vga.setOnClickListener(view -> {
            Intent vga_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            vga_intent.putExtra("name","Video Cards");
            vga_intent.putExtra("Parts",parts);
            vga_intent.putExtra("Build",name_action_bar);
            startActivity(vga_intent);
        });
        psu.setOnClickListener(view -> {
            Intent psu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            psu_intent.putExtra("name","Power Supplies");
            psu_intent.putExtra("Parts",parts);
            psu_intent.putExtra("Build",name_action_bar);
            startActivity(psu_intent);
        });
        stor.setOnClickListener(view -> {
            Intent stor_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            stor_intent.putExtra("name","Storage");
            stor_intent.putExtra("Parts",parts);
            stor_intent.putExtra("Build",name_action_bar);
            startActivity(stor_intent);
        });
        cool.setOnClickListener(view -> {
            Intent cool_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            cool_intent.putExtra("name","CPU Cooler");
            cool_intent.putExtra("Parts",parts);
            cool_intent.putExtra("Build",name_action_bar);
            startActivity(cool_intent);
        });
        mon.setOnClickListener(view -> {
            Intent mon_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mon_intent.putExtra("name","Monitor");
            mon_intent.putExtra("Parts",parts);
            mon_intent.putExtra("Build",name_action_bar);
            startActivity(mon_intent);
        });
        pc_case.setOnClickListener(view -> {
            Intent pc_case_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            pc_case_intent.putExtra("name","Cases");
            pc_case_intent.putExtra("Parts",parts);
            pc_case_intent.putExtra("Build",name_action_bar);
            startActivity(pc_case_intent);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDoubleArray("Parts",parts);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        parts = savedInstanceState.getDoubleArray("Parts");
    }
    public double getPriceSum(){
        double price=0;
        for(int i=0;i<parts.length;i++) {
            price = price+parts[i];
        }
        return price;
    }
}
