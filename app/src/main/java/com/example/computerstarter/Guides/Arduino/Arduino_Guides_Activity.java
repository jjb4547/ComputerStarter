package com.example.computerstarter.Guides.Arduino;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class Arduino_Guides_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arduino_guide_layout);
        TextView home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
        });
        CardView supplies = findViewById(R.id.supplies);
        CardView installation = findViewById(R.id.installation);
        CardView setup = findViewById(R.id.setup);
        supplies.setOnClickListener(view->{
            Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, Arduino_Guide_Supplies_Activity.class));
            //overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        installation.setOnClickListener(view -> {
            Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, Arduino_Guide_Installation_Activity.class));
            //overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        setup.setOnClickListener(view -> {
            Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, Arduino_Guide_Setup_Activity.class));
            //overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });

    }
}
