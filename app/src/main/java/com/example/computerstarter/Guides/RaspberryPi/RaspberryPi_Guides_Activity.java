package com.example.computerstarter.Guides.RaspberryPi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Education.Education_Choosing_Activity;
import com.example.computerstarter.R;

public class RaspberryPi_Guides_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspberrypi_guide_layout);
        TextView home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), Education_Choosing_Activity.class)
                    .putExtra("component","Raspberry Pi"));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
        });
        CardView prep = findViewById(R.id.prep);
        CardView setup = findViewById(R.id.setup);
        CardView troubleshoot = findViewById(R.id.troubleshoot);
        //CardView projects = findViewById(R.id.projects);
        prep.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Guide_Prep_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        setup.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Guide_Setup_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        troubleshoot.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Guide_Troubleshoot_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        //projects.setOnClickListener(view -> {
            //startActivity(new Intent(this, RaspberryPi_Projects.class));
        //});
    }
}
