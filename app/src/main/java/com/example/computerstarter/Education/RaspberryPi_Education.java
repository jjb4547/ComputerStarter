package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guides_Activity;
import com.example.computerstarter.R;

public class RaspberryPi_Education extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.raspberrypi_arduino_education);
        //TextView title = findViewById(R.id.pcpartTitle);
        CardView setup = findViewById(R.id.setup);
        //CardView projects = findViewById(R.id.projects);
        //title.setText(getIntent().getExtras().getString("component"));
        setup.setOnClickListener(v->{
            startActivity(new Intent(this, RaspberryPi_Guides_Activity.class));
        });
    }
}
