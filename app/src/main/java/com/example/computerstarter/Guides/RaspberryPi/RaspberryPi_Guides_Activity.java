package com.example.computerstarter.Guides.RaspberryPi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.RaspberryPi.Projects.RaspberryPi_Projects;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class RaspberryPi_Guides_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspberrypi_guide_layout);
        TextView home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
        CardView supplies = findViewById(R.id.supplies);
        CardView installation = findViewById(R.id.installation);
        CardView setup = findViewById(R.id.setup);
        CardView projects = findViewById(R.id.projects);
        supplies.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Guide_Supplies_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        installation.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Guide_Installation_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        setup.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Guide_Setup_Activity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        projects.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Projects.class));
        });
    }
}
