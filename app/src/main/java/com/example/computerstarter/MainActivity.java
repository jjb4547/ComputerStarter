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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
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
    private String[] list;
    private String quiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
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
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        MenuItem item_log = menu.findItem(R.id.log);
        MenuItem item_quiz = menu.findItem(R.id.quiz);
        MenuItem item_acc = menu.findItem(R.id.account);
        if(user!=null) {
            item_log.setTitle("Log Out");
            item_acc.setVisible(true);
            item_quiz.setVisible(true);
        }else {
            item_log.setTitle("Log In");
            item_acc.setVisible(false);
            item_quiz.setVisible(false);
        }
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        if(user!=null) {
            String current = user.getUid();
            DocumentReference documentReference = db.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> user_data = documentSnapshot.getData();
                        name.setText(user_data.get("Name").toString());
                        email.setText(user_data.get("Email").toString());
                    } else {
                        Toast.makeText(MainActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            name.setText("");
            email.setText("");
            //Toast.makeText(MainActivity.this,"You are not Logged In",Toast.LENGTH_SHORT).show();
        }
        //name.setText("Jesus");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_quiz = menu.findItem(R.id.quiz);
        MenuItem item_acc = menu.findItem(R.id.account);
        real_login login = new real_login();
        switch (item.getItemId()){
            case R.id.social:
                Toast.makeText(this,"Social",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.building:
                //Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,PriceList.getName(0) + " $" + PriceList.getPrice(0),Toast.LENGTH_SHORT).show();
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null)
                    startActivity(new Intent(MainActivity.this,AccountActivity.class));
                else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.quiz:
                    showAlertDialogQuiz();
            case R.id.log:
                if(mAuth.getCurrentUser()!=null){
                    mAuth.signOut();
                    Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
                    item.setTitle("Log In");
                    login.logged = false;
                    item_acc.setVisible(false);
                    item_quiz.setVisible(false);
                }else{
                    startActivity(new Intent(MainActivity.this,real_login.class));
                    if(login.logged)
                        item.setTitle("Log Out");
                    else
                        item.setTitle("Log In");
                }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return true;
    }
    public void showAlertDialogQuiz(){
        //CharSequence[] charSequences = new CharSequence[]{"Building","Social","Education"};
        list = getResources().getStringArray(R.array.quiz);
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Quiz");
        //alert.setMessage("Please Choose the Quiz");
        alert.setSingleChoiceItems(list, 0, (dialogInterface, i) -> {
            quiz = list[i];
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("ID",quiz);
            startActivity(intent);
        });
        alert.create().show();
    }
}
