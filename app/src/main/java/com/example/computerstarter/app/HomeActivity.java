package com.example.computerstarter.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.Education.Education_Choosing_Activity;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    CardView build,social,education,login;
    EditText searchParts;
    ImageView searchButton;
    String[] diffTitles;
    TextView userName, logText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
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
        diffTitles = getResources().getStringArray(R.array.comp_names);
        build = findViewById(R.id.buildCard);
        social = findViewById(R.id.socialCard);
        education = findViewById(R.id.eduCard);
        login = findViewById(R.id.loginCard);
        searchParts = findViewById(R.id.partsLookup);
        searchButton = findViewById(R.id.searchButton);
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
        searchParts.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)){
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
                    }
                }
                return true;
            }
        });
        searchButton.setOnClickListener(v->{
            if(searchParts.getText().toString().toLowerCase().equals("cpu")) {
                startActivity(new Intent(this, Education_Choosing_Activity.class)
                        .putExtra("component", diffTitles[0])
                        .putExtra("Act", "Edu"));
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
}
