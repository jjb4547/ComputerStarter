package com.example.computerstarter.Guides.RaspberryPi.Projects.HumiditySensor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.RaspberryPi.Projects.RaspberryPi_Projects;
import com.example.computerstarter.R;

public class RaspberryPi_Humidity_Sensor extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspberrypi_humidity_project_layout);
        CardView supplies = findViewById(R.id.supplies);
        supplies.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Humidity_Sensor_Supplies.class));
        });
        CardView wiring = findViewById(R.id.installation);
        wiring.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Humidity_Sensor_Wiring.class));
        });
        CardView coding = findViewById(R.id.setup);
        coding.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Humidity_Sensor_Coding.class));
        });
        TextView home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), RaspberryPi_Projects.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
    }
}
