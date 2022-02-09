package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;

public class PC_Part_Activity extends AppCompatActivity {
    private ListView listView;
    private String[] diffTitles;
    private boolean sortAscending = true;
    private boolean unSorted = true;
    private TextView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_part);
        //TextView text = findViewById(R.id.title);
        home = findViewById(R.id.home);
        diffTitles = getResources().getStringArray(R.array.comp_names);
        CardView cpu = findViewById(R.id.cpu);
        CardView mot = findViewById(R.id.mot);
        CardView mem = findViewById(R.id.mem);
        CardView vga = findViewById(R.id.gpu);
        CardView psu = findViewById(R.id.psu);
        CardView stor = findViewById(R.id.stor);
        CardView mon = findViewById(R.id.mon);
        CardView cool = findViewById(R.id.cool);
        CardView pc_case = findViewById(R.id.id_case);
        home.setOnClickListener(view->{
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        cpu.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[0], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[0]);
            startActivity(i);
        });
        mot.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[1], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[1]);
            startActivity(i);
        });
        mem.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[2], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[2]);
            startActivity(i);
        });
        vga.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[7], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[7]);
            startActivity(i);
        });
        psu.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[4], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[4]);
            startActivity(i);
        });
        stor.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[3], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[3]);
            startActivity(i);
        });
        mon.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[6], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[6]);
            startActivity(i);
        });
        cool.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[5], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[5]);
            startActivity(i);
        });
        pc_case.setOnClickListener(v -> {
            Toast.makeText(PC_Part_Activity.this, diffTitles[8], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[8]);
            startActivity(i);
        });
    }
}