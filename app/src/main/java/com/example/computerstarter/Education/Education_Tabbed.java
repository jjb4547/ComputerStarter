package com.example.computerstarter.Education;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.computerstarter.R;
import com.example.computerstarter.databinding.ActivityEducationTabbedBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Education_Tabbed extends AppCompatActivity {

    private ActivityEducationTabbedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_tabbed);
        String from = getIntent().getExtras().getString("from");
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
        BottomNavigationView bottomNavigationView = findViewById(R.id.topNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.frame_layout_edu);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        if(from.compareTo("Intermediate")==0){
            navController.navigate(R.id.action_navigation_basic_to_navigation_detail);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String position = bundle.getString("component");
            switch (position){
                case "CPU":
                    getSupportActionBar().setTitle(diffTitles[0]);
                    break;
                case "MotherBoard":
                    getSupportActionBar().setTitle(diffTitles[1]);
                    break;
                case "Memory":
                    getSupportActionBar().setTitle(diffTitles[2]);
                    break;
                case "Storage":
                    getSupportActionBar().setTitle(diffTitles[3]);
                    break;
                case "Video Card":
                    getSupportActionBar().setTitle(diffTitles[7]);
                    break;
                case "Power Supply":
                    getSupportActionBar().setTitle(diffTitles[4]);
                    break;
                case "CPU Cooler":
                    getSupportActionBar().setTitle(diffTitles[5]);
                    break;
                case "Case":
                    getSupportActionBar().setTitle(diffTitles[8]);
                    break;
                case "OS":
                    getSupportActionBar().setTitle(diffTitles[11]);
                    break;
                case "Monitor":
                    getSupportActionBar().setTitle(diffTitles[6]);
                    break;
                case "Peripherals":
                    getSupportActionBar().setTitle(diffTitles[12]);
                    break;
                case "Raspberry Pi":
                    getSupportActionBar().setTitle(diffTitles[9]);
                    break;
                case "Arduino":
                    getSupportActionBar().setTitle(diffTitles[10]);
                    break;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}