package com.example.computerstarter.Guides.RaspberryPi.Projects;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Education.Education_Choosing_Activity;
import com.example.computerstarter.Guides.RaspberryPi.Projects.HumiditySensor.RaspberryPi_Humidity_Sensor;
import com.example.computerstarter.R;

public class RaspberryPi_Projects extends AppCompatActivity {
    private CardView humiditySensor;
    private TextView home;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspberrypi_projects_layout);
        home = findViewById(R.id.home);
        humiditySensor = findViewById(R.id.humiditySensor);
        humiditySensor.setOnClickListener(view -> {
            startActivity(new Intent(this, RaspberryPi_Humidity_Sensor.class));
        });
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), Education_Choosing_Activity.class)
                    .putExtra("component", "Raspberry Pi"));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
    }
}
