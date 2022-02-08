package com.example.computerstarter.Build;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.Education.Education_Tabbed;
import com.example.computerstarter.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PC_Part_Activity extends AppCompatActivity {
    private ListView listView;
    private String[] diffTitles;
    private boolean sortAscending = true;
    private boolean unSorted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_part);
        TextView text = findViewById(R.id.title);
        getSupportActionBar().setTitle("PC Parts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[0], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[0]);
                startActivity(i);
            }
        });
        mot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[1], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[1]);
                startActivity(i);
            }
        });
        mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[2], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[2]);
                startActivity(i);
            }
        });
        vga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[7], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[7]);
                startActivity(i);
            }
        });
        psu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[4], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[4]);
                startActivity(i);
            }
        });
        stor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[3], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[3]);
                startActivity(i);
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[6], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[6]);
                startActivity(i);
            }
        });
        cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[5], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[5]);
                startActivity(i);
            }
        });
        pc_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PC_Part_Activity.this, diffTitles[8], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                i.putExtra("component", diffTitles[8]);
                startActivity(i);
            }
        });
/*
        //handling click events
        .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //they all open the same thing for now
                if (position == 0) {
                    //testing

                } else if (position == 1) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[1], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[1]);
                    startActivity(i);
                } else if (position == 2) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[2], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[2]);
                    startActivity(i);
                } else if (position == 3) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[3], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[3]);
                    startActivity(i);
                } else if (position == 4) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[4], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[4]);
                    startActivity(i);
                } else if (position == 5) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[5], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[5]);
                    startActivity(i);
                } else if (position == 6) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[6], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[6]);
                    startActivity(i);
                } else if (position == 7) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[7], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[7]);
                    startActivity(i);
                } else if (position == 8) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[8], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[8]);
                    startActivity(i);
                } else if (position == 9) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[9], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[9]);
                    startActivity(i);
                } else if (position == 10) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[10], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[10]);
                    startActivity(i);
                } else if (position == 11) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[11], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[11]);
                    startActivity(i);
                } else if (position == 12) {
                    Toast.makeText(PC_Part_Activity.this, diffTitles[12], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[12]);
                    startActivity(i);
                }
            }
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                    this.finish();
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void sortData(){
        List<String>sortedTitles = Arrays.asList(getResources().getStringArray(R.array.comp_names));
        if(unSorted)
            Collections.sort(sortedTitles);
        else {
            Collections.sort(sortedTitles);
            Collections.reverse(sortedTitles);
        }
        diffTitles = sortedTitles.toArray(new String[0]);
        sortAscending=!sortAscending;
        unSorted=!unSorted;
        listView.setAdapter(new ArrayAdapter<String>(PC_Part_Activity.this, android.R.layout.simple_list_item_1,diffTitles));
    }
}