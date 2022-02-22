package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.Arduino.Arduino_Guides_Activity;
import com.example.computerstarter.Guides.RaspberryPi.Projects.RaspberryPi_Projects;
import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guides_Activity;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class Education_Choosing_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_choosing_layout);
        TextView home = findViewById(R.id.home);
        TextView title = findViewById(R.id.pcpartTitle);
        String comp = getIntent().getExtras().getString("component");
        title.setText(comp);
        TextView basicText = findViewById(R.id.basic_Text);
        TextView detailedText = findViewById(R.id.detailed_Text);
        home.setOnClickListener(view->{
            if(comp.equals("Raspberry Pi")||comp.equals("Arduino")){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), PC_Part_Activity.class).putExtra("Act",getIntent().getExtras().getString("Act")));
            }
            overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
        });
        if(comp.equals("Raspberry Pi")||comp.equals("Arduino")){
            basicText.setText("Initial Setup");
            detailedText.setText("Projects");
        }
        CardView basic = findViewById(R.id.supplies);
        CardView intermediate = findViewById(R.id.installation);
        basic.setOnClickListener(view->{
            if(!(comp.equals("Arduino")||comp.equals("Raspberry Pi"))) {
                startActivity(new Intent(getApplicationContext(), Education_Tabbed.class).putExtra("from", "Beginner")
                        .putExtra("component", comp).putExtra("Act","Edu"));
            }else if(comp.equals("Arduino")){
                startActivity(new Intent(getApplicationContext(), Arduino_Guides_Activity.class));
            }else if(comp.equals("Raspberry Pi")){
                startActivity(new Intent(getApplicationContext(), RaspberryPi_Guides_Activity.class));
            }
        });
        intermediate.setOnClickListener(view -> {
            if(!(comp.equals("Arduino")||comp.equals("Raspberry Pi"))) {
                startActivity(new Intent(getApplicationContext(), Education_Tabbed.class).putExtra("from", "Intermediate")
                        .putExtra("component", comp));
            }else if(comp.equals("Arduino")){
                startActivity(new Intent(getApplicationContext(), Arduino_Guides_Activity.class));
            }else if(comp.equals("Raspberry Pi")){
                startActivity(new Intent(getApplicationContext(), RaspberryPi_Projects.class));
            }
        });

    }
}
