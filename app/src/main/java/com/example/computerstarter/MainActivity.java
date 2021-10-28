package com.example.computerstarter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int SPLASH_TIMEOUT = 2000;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = pref.getBoolean(getString(R.string.pref_previously_started),false);
        if(!previouslyStarted){
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(getString(R.string.pref_previously_started),true);
            editor.commit();
            showAlertDialog();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tutorial");
        alert.setMessage("Is this your first time using the app?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Okay!",Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()){
            case R.id.social:
                Toast.makeText(this,"Social",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.building:
                Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null)
                    startActivity(new Intent(MainActivity.this,AccountActivity.class));
                else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        switch (id){
            case R.id.quiz:
                Toast.makeText(MainActivity.this, "Quiz", Toast.LENGTH_SHORT).show();
                Intent intent_quiz = new Intent(MainActivity.this, QuizActivity.class);
                intent_quiz.putExtra("ID","Education");
                startActivity(intent_quiz);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
