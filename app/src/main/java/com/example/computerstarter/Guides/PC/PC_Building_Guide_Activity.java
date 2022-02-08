package com.example.computerstarter.Guides.PC;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class PC_Building_Guide_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_building_guides_layout);
        TextView home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(PC_Building_Guide_Activity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
        });
        CardView supplies = findViewById(R.id.supplies);
        supplies.setOnClickListener(view->{
            startActivity(new Intent(this, PC_Guide_Supplies_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
        CardView installation = findViewById(R.id.installation);
        installation.setOnClickListener(view -> {
            startActivity(new Intent(this, PC_Guide_Installation_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
        CardView setup = findViewById(R.id.setup);
        setup.setOnClickListener(view -> {
            startActivity(new Intent(this, PC_Guide_Setup_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
    }
}
