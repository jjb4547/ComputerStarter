package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.Arduino.Arduino_Guide_Supplies_Activity;
import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guide_Supplies_Activity;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class Education_Choosing_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_choosing_layout);
        TextView home = findViewById(R.id.home);
        TextView title = findViewById(R.id.pcpartTitle);
        title.setText(getIntent().getExtras().getString("component"));
        if(getIntent().getExtras().getString("Act").equals("Guide") &&
                (getIntent().getExtras().getString("component").equals("Arduino")||getIntent().getExtras().getString("component").equals("Raspberry Pi"))){
            home.setText("Return to Guides");
        }else
        {
            home.setText("Return Back");
        }
        home.setOnClickListener(view->{
            if(getIntent().getExtras().getString("component").equals("Raspberry Pi")){
                if(getIntent().getExtras().getString("Act").equals("Guide")){
                    startActivity(new Intent(getApplicationContext(), RaspberryPi_Guide_Supplies_Activity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
                }
            } else if (getIntent().getExtras().getString("component").equals("Arduino")) {
                if(getIntent().getExtras().getString("Act").equals("Guide")){
                    startActivity(new Intent(getApplicationContext(), Arduino_Guide_Supplies_Activity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
                }
            }else {
                startActivity(new Intent(getApplicationContext(), PC_Part_Activity.class)
                        .putExtra("Act",getIntent().getExtras().getString("Act")));
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
            }
        });
        CardView basic = findViewById(R.id.supplies);
        CardView intermediate = findViewById(R.id.installation);
        basic.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(),Education_Tabbed.class).putExtra("from","Beginner")
                    .putExtra("component",getIntent().getExtras().getString("component"))
                    .putExtra("Act", getIntent().getExtras().getString("Act")));
        });
        intermediate.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Education_Tabbed.class).putExtra("from","Intermediate")
                    .putExtra("component",getIntent().getExtras().getString("component"))
                    .putExtra("Act", getIntent().getExtras().getString("Act")));
        });

    }
}
