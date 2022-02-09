package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

        home.setOnClickListener(view->{
            if(getIntent().getExtras().getString("component").compareTo("Raspberry Pi")==0){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
            } else if (getIntent().getExtras().getString("component").compareTo("Arduino") == 0) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
            }else {
                startActivity(new Intent(getApplicationContext(), PC_Part_Activity.class));
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
            }
        });
        CardView basic = findViewById(R.id.supplies);
        CardView intermediate = findViewById(R.id.installation);
        basic.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(),Education_Tabbed.class).putExtra("from","Beginner")
                    .putExtra("component",getIntent().getExtras().getString("component")));
        });
        intermediate.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Education_Tabbed.class).putExtra("from","Intermediate")
                    .putExtra("component",getIntent().getExtras().getString("component")));
        });

    }
}
