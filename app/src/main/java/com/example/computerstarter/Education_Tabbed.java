package com.example.computerstarter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
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
            int position = bundle.getInt("position");
            switch (position){
                case 0:
                    view.setText(diffTitles[0]);
                    break;
                case 1:
                    view.setText(diffTitles[1]);
                    break;
                case 2:
                    view.setText(diffTitles[2]);
                    break;
                case 3:
                    view.setText(diffTitles[3]);
                    break;
                case 4:
                    view.setText(diffTitles[4]);
                    break;
                case 5:
                    view.setText(diffTitles[5]);
                    break;
                case 6:
                    view.setText(diffTitles[6]);
                    break;
                case 7:
                    view.setText(diffTitles[7]);
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