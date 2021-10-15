package com.example.computerstarter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.computerstarter.databinding.ActivityEducationTabbedBinding;
import com.example.computerstarter.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Education_Tabbed extends AppCompatActivity {

    private ActivityEducationTabbedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEducationTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
        TextView view = findViewById(R.id.title);
        ImageButton button = findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String position = bundle.getString("component");
            switch (position){
                case "CPU":
                    view.setText(diffTitles[0]);
                    break;
                case "MotherBoard":
                    view.setText(diffTitles[1]);
                    break;
                case "Memory":
                    view.setText(diffTitles[2]);
                    break;
                case "Storage":
                    view.setText(diffTitles[3]);
                    break;
                case "Video Card":
                    view.setText(diffTitles[4]);
                    break;
                case "Power Supply":
                    view.setText(diffTitles[5]);
                    break;
                case "Raspberry Pi and More":
                    view.setText(diffTitles[6]);
                    break;
                case "Arduino and More":
                    view.setText(diffTitles[7]);
                    break;
                case "CPU Cooler":
                    view.setText(diffTitles[8]);
                    break;
                case "Case":
                    view.setText(diffTitles[9]);
                    break;
                case "OS":
                    view.setText(diffTitles[10]);
                    break;
                case "Monitor":
                    view.setText(diffTitles[11]);
                    break;
                case "Peripherals":
                    view.setText(diffTitles[12]);
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