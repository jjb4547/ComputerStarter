package com.example.computerstarter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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
    private TextView cpuTitle;
    private TextView motTitle;
    private TextView memTitle;
    private TextView storTitle;
    private TextView psuTitle;
    private TextView coolTitle;
    private TextView monTitle;
    private TextView vgaTitle;
    private TextView caseTitle;
    private ImageView cpuImage;
    private ImageView motImage;
    private ImageView memImage;
    private ImageView storImage;
    private ImageView psuImage;
    private ImageView coolImage;
    private ImageView monImage;
    private ImageView vgaImage;
    private ImageView caseImage;
    double[] parts;
    String name;
    String name_action_bar;
    String[] titles;
    int[] images;

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
        titles = intent.getStringArrayExtra("Titles");
        images = intent.getIntArrayExtra("Images");
        getSupportActionBar().setTitle(name_action_bar);
        save = findViewById(R.id.save_button);
        init();
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
        cpuTitle = findViewById(R.id.cpuText);
        motTitle = findViewById(R.id.motText);
        memTitle = findViewById(R.id.memText);
        storTitle = findViewById(R.id.storText);
        psuTitle = findViewById(R.id.psuText);
        coolTitle = findViewById(R.id.coolText);
        monTitle = findViewById(R.id.monText);
        vgaTitle = findViewById(R.id.vgaText);
        caseTitle = findViewById(R.id.caseText);
        cpuImage = findViewById(R.id.cpu_link_image);
        motImage = findViewById(R.id.mot_link_image);
        memImage = findViewById(R.id.mem_link_image);
        storImage = findViewById(R.id.stor_link_image);
        psuImage = findViewById(R.id.psu_link_image);
        coolImage = findViewById(R.id.cool_link_image);
        monImage = findViewById(R.id.mon_link_image);
        vgaImage = findViewById(R.id.vga_link_image);
        caseImage = findViewById(R.id.case_link_image);
        cpuTitle.setText(titles[0]+"\n$"+parts[0]);
        motTitle.setText(titles[1]+"\n$"+parts[1]);
        memTitle.setText(titles[2]+"\n$"+parts[2]);
        storTitle.setText(titles[3]+"\n$"+parts[3]);
        psuTitle.setText(titles[4]+"\n$"+parts[4]);
        coolTitle.setText(titles[5]+"\n$"+parts[5]);
        monTitle.setText(titles[6]+"\n$"+parts[6]);
        vgaTitle.setText(titles[7]+"\n$"+parts[7]);
        caseTitle.setText(titles[8]+"\n$"+parts[8]);
        cpuImage.setImageResource(images[0]);
        motImage.setImageResource(images[1]);
        memImage.setImageResource(images[2]);
        storImage.setImageResource(images[3]);
        psuImage.setImageResource(images[4]);
        coolImage.setImageResource(images[5]);
        monImage.setImageResource(images[6]);
        vgaImage.setImageResource(images[7]);
        caseImage.setImageResource(images[8]);
        cpu.setOnClickListener(view -> {
            Intent cpu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            cpu_intent.putExtra("name","CPU");
            cpu_intent.putExtra("Parts",parts);
            cpu_intent.putExtra("Build",name_action_bar);
            cpu_intent.putExtra("Titles",titles);
            cpu_intent.putExtra("Images",images);
            startActivity(cpu_intent);
        });
        mot.setOnClickListener(view -> {
            Intent mot_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mot_intent.putExtra("name","Motherboards");
            mot_intent.putExtra("Parts",parts);
            mot_intent.putExtra("Build",name_action_bar);
            mot_intent.putExtra("Titles",titles);
            mot_intent.putExtra("Images",images);
            startActivity(mot_intent);
        });
        mem.setOnClickListener(view -> {
            Intent mem_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mem_intent.putExtra("name","Memory");
            mem_intent.putExtra("Parts",parts);
            mem_intent.putExtra("Build",name_action_bar);
            mem_intent.putExtra("Titles",titles);
            mem_intent.putExtra("Images",images);
            startActivity(mem_intent);
        });
        vga.setOnClickListener(view -> {
            Intent vga_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            vga_intent.putExtra("name","Video Cards");
            vga_intent.putExtra("Parts",parts);
            vga_intent.putExtra("Build",name_action_bar);
            vga_intent.putExtra("Titles",titles);
            vga_intent.putExtra("Images",images);
            startActivity(vga_intent);
        });
        psu.setOnClickListener(view -> {
            Intent psu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            psu_intent.putExtra("name","Power Supplies");
            psu_intent.putExtra("Parts",parts);
            psu_intent.putExtra("Build",name_action_bar);
            psu_intent.putExtra("Titles",titles);
            psu_intent.putExtra("Images",images);
            startActivity(psu_intent);
        });
        stor.setOnClickListener(view -> {
            Intent stor_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            stor_intent.putExtra("name","Storage");
            stor_intent.putExtra("Parts",parts);
            stor_intent.putExtra("Build",name_action_bar);
            stor_intent.putExtra("Titles",titles);
            stor_intent.putExtra("Images",images);
            startActivity(stor_intent);
        });
        cool.setOnClickListener(view -> {
            Intent cool_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            cool_intent.putExtra("name","CPU Cooler");
            cool_intent.putExtra("Parts",parts);
            cool_intent.putExtra("Build",name_action_bar);
            cool_intent.putExtra("Titles",titles);
            cool_intent.putExtra("Images",images);
            startActivity(cool_intent);
        });
        mon.setOnClickListener(view -> {
            Intent mon_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mon_intent.putExtra("name","Monitor");
            mon_intent.putExtra("Parts",parts);
            mon_intent.putExtra("Build",name_action_bar);
            mon_intent.putExtra("Titles",titles);
            mon_intent.putExtra("Images",images);
            startActivity(mon_intent);
        });
        pc_case.setOnClickListener(view -> {
            Intent pc_case_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            pc_case_intent.putExtra("name","Cases");
            pc_case_intent.putExtra("Parts",parts);
            pc_case_intent.putExtra("Build",name_action_bar);
            pc_case_intent.putExtra("Titles",titles);
            pc_case_intent.putExtra("Images",images);
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
    public Double getPriceSum(){
        Double price=0.0;
        for(int i=0;i<parts.length;i++) {
            price = price+parts[i];
        }
        return price;
    }
}
