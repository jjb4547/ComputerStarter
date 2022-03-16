package com.example.computerstarter.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.preference.PreferenceManager;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.Build.MyBuildActivity;
import com.example.computerstarter.Education.Education_Choosing_Activity;
import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guides_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    CardView build,social,education,login;
    AutoCompleteTextView searchParts;
    ImageView searchButton;
    String[] diffTitles;
    TextView userName, logText;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem item_acc;
    private TextView log;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private List<PartsItem> partsItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        fillPartsList();
        user = mAuth.getCurrentUser();
        userName = findViewById(R.id.textUserName);
        logText = findViewById(R.id.logText);
        if(user!=null){
            userName.setText(user.getDisplayName());
            logText.setText("LOG OUT");
        }else{
            logText.setText("LOG IN");
            userName.setText("GUEST");
        }
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_acc = menu.findItem(R.id.account);
        item_acc.setVisible(true);
        log = findViewById(R.id.log_Text);
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
        }
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profile = headerView.findViewById(R.id.myimage);
        if(user!=null) {
            String current = user.getUid();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            StorageReference profileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile");
            profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profile));
        }else{
            name.setText("Guest");
            email.setText("Guest Not Signed In");
        }
        diffTitles = getResources().getStringArray(R.array.comp_names);
        build = findViewById(R.id.buildCard);
        social = findViewById(R.id.socialCard);
        education = findViewById(R.id.eduCard);
        login = findViewById(R.id.loginCard);
        searchParts = findViewById(R.id.partsLookup);
        searchButton = findViewById(R.id.searchButton);
        AutoCompletePartsAdapter adapter = new AutoCompletePartsAdapter(this, partsItemList);
        searchParts.setAdapter(adapter);
        build.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this, MainBuilds.class)
                    .putExtra("from","Main"));
        });
        education.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this, MainBuilds.class)
                    .putExtra("from","Edu"));
        });
        social.setOnClickListener(v->{
            startActivity(new Intent(HomeActivity.this, MainBuilds.class)
                    .putExtra("from","Social"));
        });
        searchParts.setThreshold(1);
        searchParts.setOnItemClickListener((parent, view, position, id) -> {
            switch (partsItemList.get(position).getPartsName().toLowerCase()) {
                case "cpu":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[0])
                            .putExtra("Act", "Edu"));
                    break;
                case "motherboard":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[1])
                            .putExtra("Act", "Edu"));
                    break;
                case "memory":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[2])
                            .putExtra("Act", "Edu"));
                    break;
                case "storage":
                case "hdd":
                case "hard drive":
                case "solid state":
                case "ssd":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[3])
                            .putExtra("Act", "Edu"));
                    break;
                case "power supply":
                case "psu":
                case "power supply unit":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[4])
                            .putExtra("Act", "Edu"));
                    break;
                case "cpu cooler":
                case "cooler":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[5])
                            .putExtra("Act", "Edu"));
                    break;
                case "monitor":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[6])
                            .putExtra("Act", "Edu"));
                    break;
                case "video card":
                case "gpu":
                case "vga":
                case "graphics card":
                case "graphic card":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[7])
                            .putExtra("Act", "Edu"));
                    break;
                case "case":
                case "pc case":
                    startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                            .putExtra("component", diffTitles[8])
                            .putExtra("Act", "Edu"));
                    break;
                case "guides":
                    startActivity(new Intent(HomeActivity.this, RaspberryPi_Guides_Activity.class));
                    break;
            }
        });
        searchParts.setOnKeyListener((v, keyCode, event) -> {
            if((keyCode==KeyEvent.KEYCODE_DEL)){
                searchParts.setText("");
            } else if((keyCode==KeyEvent.KEYCODE_ENTER)) {
                checkName(searchParts);
            }
            return true;
        });
        searchButton.setOnClickListener(v->{
            if(searchParts.getText().toString().toLowerCase().equals("cpu")) {
                startActivity(new Intent(this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[0])
                        .putExtra("Act", "Edu"));
            }else{
                searchParts.setText("");
            }
        });
        login.setOnClickListener(v->{
            if(user!=null){
                mAuth.signOut();
                logText.setText("LOG IN");
                startActivity(new Intent(HomeActivity.this, Login_SignUpActivity.class));
            }else {
                startActivity(new Intent(HomeActivity.this, Login_SignUpActivity.class));
            }
        });
    }

    private void checkName(AutoCompleteTextView searchParts) {
        switch (searchParts.getText().toString().toLowerCase()) {
            case "cpu":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[0])
                        .putExtra("Act", "Edu"));
                break;
            case "motherboard":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[1])
                        .putExtra("Act", "Edu"));
                break;
            case "memory":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[2])
                        .putExtra("Act", "Edu"));
                break;
            case "storage":
            case "hdd":
            case "hard drive":
            case "solid state":
            case "ssd":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[3])
                        .putExtra("Act", "Edu"));
                break;
            case "power supply":
            case "psu":
            case "power supply unit":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[4])
                        .putExtra("Act", "Edu"));
                break;
            case "cpu cooler":
            case "cooler":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[5])
                        .putExtra("Act", "Edu"));
                break;
            case "monitor":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[6])
                        .putExtra("Act", "Edu"));
                break;
            case "video card":
            case "gpu":
            case "vga":
            case "graphics card":
            case "graphic card":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[7])
                        .putExtra("Act", "Edu"));
                break;
            case "case":
            case "pc case":
                startActivity(new Intent(HomeActivity.this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[8])
                        .putExtra("Act", "Edu"));
                break;
            case "raspberry pi guides":
                startActivity(new Intent(HomeActivity.this, RaspberryPi_Guides_Activity.class));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_acc = menu.findItem(R.id.account);
        //real_login login = new real_login();
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
                }else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started),false);
        if(!previouslyStarted){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started_login),Boolean.TRUE);
            edit.commit();
            startActivity(new Intent(HomeActivity.this,Login_SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        }
    }
    private void fillPartsList(){
        partsItemList = new ArrayList<>();
        partsItemList.add(new PartsItem("CPU",R.drawable.cpu_link));
        partsItemList.add(new PartsItem("MOTHERBOARD",R.drawable.motherboard_link));
        partsItemList.add(new PartsItem("MEMORY",R.drawable.memory_link));
        partsItemList.add(new PartsItem("STORAGE",R.drawable.storage_link));
        partsItemList.add(new PartsItem("PSU",R.drawable.psu_link));
        partsItemList.add(new PartsItem("CPU COOLER",R.drawable.cpu_cooler_link));
        partsItemList.add(new PartsItem("MONITOR",R.drawable.monitor_link));
        partsItemList.add(new PartsItem("VIDEO CARD",R.drawable.vga_link));
        partsItemList.add(new PartsItem("CASE",R.drawable.pc_case_link));
        partsItemList.add(new PartsItem("HDD", R.drawable.storage_link));
        partsItemList.add(new PartsItem("HARD DRIVE", R.drawable.storage_link));
        partsItemList.add(new PartsItem("SOLID STATE", R.drawable.storage_link));
        partsItemList.add(new PartsItem("SSD", R.drawable.storage_link));
        partsItemList.add(new PartsItem("GUIDES",R.drawable.rasp_logo));
        partsItemList.add(new PartsItem("RASPBERRY PI GUIDES",R.drawable.rasp_logo));
    }
}
