package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.example.computerstarter.Build.MyBuildActivity;
import com.example.computerstarter.Guides.Arduino.Arduino_Guides_Activity;
import com.example.computerstarter.Guides.RaspberryPi.Projects.RaspberryPi_Projects;
import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guides_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Education_Choosing_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_choosing_layout);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_quiz = menu.findItem(R.id.quiz);
        item_acc = menu.findItem(R.id.account);
        log = findViewById(R.id.log_Text);
        logBut = findViewById(R.id.logButtton);
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
            item_quiz.setVisible(true);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
            item_quiz.setVisible(false);
        }
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profile = headerView.findViewById(R.id.myimage);
        if(user!=null) {
            String current = user.getUid();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            //profile.setImageURI(user.getPhotoUrl()); //bugging out not sure why but only on the emulator
        }else{
            name.setText("Guest");
            email.setText("Guest Not Signed In");
        }
        log.setOnClickListener(view->{
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                log.setText("Log In");
                //login.logged = false;
                item_acc.setVisible(false);
                item_quiz.setVisible(false);
            }else{
                startActivity(new Intent(this, Login_SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                //log.setText("Log Out");
            }
        });
        TextView home = findViewById(R.id.home);
        TextView title = findViewById(R.id.pcpartTitle);
        String comp = getIntent().getExtras().getString("component");
        title.setText(comp);
        TextView basicText = findViewById(R.id.basic_Text);
        TextView detailedText = findViewById(R.id.detailed_Text);
        if(!(comp.equals("Raspberry Pi")||comp.equals("Arduino"))){
            home.setText("Return To Page");
        } else{
            home.setText("Return to Home");
        }
        home.setOnClickListener(view->{
            if(comp.equals("Raspberry Pi")||comp.equals("Arduino")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), PC_Part_Activity.class).putExtra("Act",getIntent().getExtras().getString("Act")));
            }
            overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
        });
        if(comp.equals("Raspberry Pi")||comp.equals("Arduino")){
            basicText.setText("Initial Setup");
            detailedText.setText("Projects");
        }
        CardView basic = findViewById(R.id.supplies);
        CardView intermediate = findViewById(R.id.installation);
        basic.setOnClickListener(view->{
            if(!(comp.equals("Arduino")||comp.equals("Raspberry Pi"))) {
                startActivity(new Intent(getApplicationContext(), Education_Tabbed.class).putExtra("from", "Beginner")
                        .putExtra("component", comp).putExtra("Act","Edu"));
            }else if(comp.equals("Arduino")){
                startActivity(new Intent(getApplicationContext(), Arduino_Guides_Activity.class));
            }else if(comp.equals("Raspberry Pi")){
                startActivity(new Intent(getApplicationContext(), RaspberryPi_Guides_Activity.class));
            }
        });
        intermediate.setOnClickListener(view -> {
            if(!(comp.equals("Arduino")||comp.equals("Raspberry Pi"))) {
                startActivity(new Intent(getApplicationContext(), Education_Tabbed.class).putExtra("from", "Intermediate")
                        .putExtra("component", comp).putExtra("Act","Edu"));
            }else if(comp.equals("Arduino")){
                //startActivity(new Intent(getApplicationContext(), Arduino_Guides_Activity.class));
            }else if(comp.equals("Raspberry Pi")){
                startActivity(new Intent(getApplicationContext(), RaspberryPi_Projects.class));
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_quiz = menu.findItem(R.id.quiz);
        MenuItem item_acc = menu.findItem(R.id.account);
        //real_login login = new real_login();
        switch (item.getItemId()){
            case R.id.social:
                Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
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
            case R.id.quiz:
                //showAlertDialogQuiz();
                Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
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
