package com.example.computerstarter.Guides.PC;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.Build.MyBuildActivity;
import com.example.computerstarter.Guides.Guides_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.example.computerstarter.app.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PC_Building_Guide_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int SPLASH_TIMEOUT = 2000;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private String[] list;
    private String quiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView log;
    private ImageView logBut;
    private MenuItem item_log;
    private MenuItem item_quiz;
    private MenuItem item_acc;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_building_guides_layout);
        ImageButton home = findViewById(R.id.homeBut);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //navController = Navigation.findNavController(this,R.id.frame_layout);
        //NavigationUI.setupWithNavController(bottomNavigationView,navController);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        toggle.syncState();
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
        log = findViewById(R.id.log_Text);
        logBut = findViewById(R.id.logButtton);
        ImageButton menuBut = findViewById(R.id.menuButton);
        menuBut.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));
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
        log.setOnClickListener(view->{
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainBuilds.class)
                        .putExtra("from","Main"));
                log.setText("Log In");
                //login.logged = false;
                item_acc.setVisible(false);
            }else{
                startActivity(new Intent(this, Login_SignUpActivity.class)
                        .putExtra("from","Main"));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                //log.setText("Log Out");
            }
        });
        home.setOnClickListener(view->{
            startActivity(new Intent(PC_Building_Guide_Activity.this, Guides_Activity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
        });
        CardView supplies = findViewById(R.id.supplies);
        supplies.setOnClickListener(view->{
            startActivity(new Intent(this, PC_Guide_Supplies_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
        CardView installation = findViewById(R.id.installation);
        installation.setOnClickListener(view -> {
            startActivity(new Intent(this, PC_Guide_Installation_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
        CardView setup = findViewById(R.id.setup);
        setup.setOnClickListener(view -> {
            startActivity(new Intent(this, PC_Guide_Setup_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
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
                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(this, AccountActivity.class));
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
}