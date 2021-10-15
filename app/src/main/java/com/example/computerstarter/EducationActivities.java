package com.example.computerstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class EducationActivities extends AppCompatActivity {
    //gonna try to make this like quiz activity
    // for the title, paragraph and image
    //private TextView eduTitle,eduPara;
    //private ImageView eduPic;
    //arraylist of display options
    //private ArrayList
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_activities);
        //changing the title
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
        TextView brief_desc = findViewById(R.id.first_title);
        TextView info = findViewById(R.id.first_info);
        TextView ports_desc =findViewById(R.id.second_title);
        TextView info_desc = findViewById(R.id.second_info);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String position = bundle.getString("component");
            switch (position){
                case "CPU":
                    //CPU
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_cpu));
                    info.setText(getString(R.string.cpu_desc));
                    info_desc.setText(getString(R.string.cpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_cpu));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[0]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Motherboard":
                    //Motherboard
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_mot));
                    info.setText(getString(R.string.mot_desc));
                    info_desc.setText(getString(R.string.mot_ports_description));
                    ports_desc.setText(getString(R.string.ports_mot));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[1]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Memory":
                    //Memory
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_mem));
                    info.setText(getString(R.string.mem_desc));
                    info_desc.setText(getString(R.string.mem_ports_description));
                    ports_desc.setText(getString(R.string.ports_mem));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[2]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Storage":
                    //Storage
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_stor));
                    info.setText(getString(R.string.stor_desc));
                    info_desc.setText(getString(R.string.stor_ports_description));
                    ports_desc.setText(getString(R.string.ports_stor));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[3]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Video Card":
                    //Video Card
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_gpu));
                    info.setText(getString(R.string.gpu_desc));
                    info_desc.setText(getString(R.string.gpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_gpu));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[4]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Power Supply":
                    //Power Supply
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_psu));
                    info.setText(getString(R.string.psu_desc));
                    info_desc.setText(getString(R.string.psu_ports_description));
                    ports_desc.setText(getString(R.string.ports_psu));
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[5]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Raspberry Pi":
                    //Raspberry Pi
                    if(getSupportActionBar()!=null)
                    getSupportActionBar().setTitle(diffTitles[6]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    break;
                case "Arduino":
                    //Arduino
                    if(getSupportActionBar()!=null)
                        getSupportActionBar().setTitle(diffTitles[7]);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}