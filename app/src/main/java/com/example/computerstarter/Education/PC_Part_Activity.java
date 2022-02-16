package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Guides.PC.PC_Guide_Supplies_Activity;
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
        String from = getIntent().getStringExtra("Act");
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
        if(from.equals("Guide")){
            home.setText("Return to Supplies");
        }else{
            home.setText("Return to Home");
        }
        home.setOnClickListener(view->{
            if(from.equals("Guide")){
                startActivity(new Intent(this, PC_Guide_Supplies_Activity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
            }else {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            }
        });
        cpu.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[0])
                    .putExtra("Act",from));
            //Toast.makeText(PC_Part_Activity.this, diffTitles[0], Toast.LENGTH_SHORT).show();
            /*Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[0]).putExtra("Act", from);
            startActivity(i);
             */
        });
        mot.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[1])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[1], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[1]).putExtra("Act", from);
            startActivity(i);

             */
        });
        mem.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[2])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[2], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[2]).putExtra("Act", from);
            startActivity(i);
             */
        });
        vga.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[7])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[7], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[7]).putExtra("Act", from);
            startActivity(i);

             */
        });
        psu.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[4])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[4], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[4]).putExtra("Act", from);
            startActivity(i);

             */
        });
        stor.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[3])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[3], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[3]).putExtra("Act", from);
            startActivity(i);

             */
        });
        mon.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[6])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[6], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[6]).putExtra("Act", from);
            startActivity(i);

             */
        });
        cool.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[5])
                    .putExtra("Act",from));
            /*
            Toast.makeText(PC_Part_Activity.this, diffTitles[5], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[5]).putExtra("Act", from);
            startActivity(i);

             */
        });
        pc_case.setOnClickListener(v -> {
            startActivity(new Intent(this,Education_Choosing_Activity.class)
                    .putExtra("component",diffTitles[8])
                    .putExtra("Act",from));
            /*Toast.makeText(PC_Part_Activity.this, diffTitles[8], Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PC_Part_Activity.this, Education_Choosing_Activity.class);
            i.putExtra("component", diffTitles[8]).putExtra("Act", from);
            startActivity(i);

             */
        });
    }
}