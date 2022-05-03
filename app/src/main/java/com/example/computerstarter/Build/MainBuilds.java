package com.example.computerstarter.Build;

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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.computerstarter.Education.PC_Part_Activity;
import com.example.computerstarter.Guides.Guides_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.example.computerstarter.SampleProjects.SampleProjects;
import com.example.computerstarter.app.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainBuilds extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int SPLASH_TIMEOUT = 2000;
    public DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private String[] list;
    private String quiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView log;
    private MenuItem item_acc;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private ImageButton menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builds_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        if(getIntent().getExtras().getString("from").equals("Edu")){
            navController.navigate(R.id.action_navigation_main_to_navigation_education);
        }else if(getIntent().getExtras().getString("from").equals("Social")){
            navController.navigate(R.id.action_navigation_main_to_navigation_social);
        }else{
            navController.navigate(R.id.action_navigation_main_self);
        }
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        menuButton = findViewById(R.id.menuButton);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_acc = menu.findItem(R.id.account);
        log = findViewById(R.id.log_Text);
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
        }
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
                if(drawerLayout.isOpen()){
                    drawerLayout.close();
                }else if(!drawerLayout.isOpen()){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
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
                Toast.makeText(MainBuilds.this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainBuilds.this, Login_SignUpActivity.class));
                log.setText("Log In");
                //login.logged = false;
                item_acc.setVisible(false);
                startActivity(new Intent(MainBuilds.this, Login_SignUpActivity.class));
            }else{
                startActivity(new Intent(MainBuilds.this, Login_SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                //log.setText("Log Out");
            }
        });
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_acc = menu.findItem(R.id.account);
        //real_login login = new real_login();
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainBuilds.this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                //Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainBuilds.this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(MainBuilds.this, AccountActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
                }else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.partsMenu:
                startActivity(new Intent(MainBuilds.this, PC_Part_Activity.class).putExtra("Act","Edu"));
                break;
            case R.id.guidesMenu:
                startActivity(new Intent(MainBuilds.this, Guides_Activity.class));
                break;
            case R.id.projectsMenu:
                startActivity(new Intent(MainBuilds.this, SampleProjects.class));
                break;
            case R.id.community:
                startActivity(new Intent(MainBuilds.this,MainBuilds.class)
                        .putExtra("from","Social"));
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
