package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.computerstarter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Education_Tabbed extends AppCompatActivity {

    //private ActivityEducationTabbedBinding binding;
    private String from;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_tabbed);
        final ImageButton homeBut = findViewById(R.id.homeBut);
        homeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Education_Tabbed.this, Education_Choosing_Activity.class)
                        .putExtra("component",getIntent().getExtras().getString("component"))
                        .putExtra("Act", getIntent().getExtras().getString("Act")));
            }
        });
        title = findViewById(R.id.titleComp);
        title.setText(getIntent().getExtras().getString("component"));
        from = getIntent().getExtras().getString("from");
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
        BottomNavigationView bottomNavigationView = findViewById(R.id.topNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.frame_layout_edu);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        if(from.compareTo("Intermediate")==0){
            navController.navigate(R.id.action_navigation_basic_to_navigation_detail);
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Bundle bundle = getIntent().getExtras();
        //if(bundle!=null){
            //String position = bundle.getString("component");
            //getSupportActionBar().setTitle(getIntent().getExtras().getString("component"));
        //}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}