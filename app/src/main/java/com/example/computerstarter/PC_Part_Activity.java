package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        listView = findViewById(R.id.lvEdu);
        listView.setClickable(true);
        diffTitles = getResources().getStringArray(R.array.comp_names);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                PC_Part_Activity.this,
                android.R.layout.simple_list_item_1,
                diffTitles
        );
        listView.setAdapter(listViewAdapter);

        //handling click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //they all open the same thing for now
                if (position == 0) {
                    //testing
                    Toast.makeText(PC_Part_Activity.this, diffTitles[0], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PC_Part_Activity.this, Education_Tabbed.class);
                    i.putExtra("component", diffTitles[0]);
                    startActivity(i);
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort:
                if (unSorted)
                    Toast.makeText(PC_Part_Activity.this, "A-Z", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PC_Part_Activity.this, "Z-A", Toast.LENGTH_SHORT).show();
                sortData();
                break;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.add);
        if(item!=null)
            item.setVisible(false);
        item = menu.findItem(R.id.settings);
        if(item!=null)
            item.setVisible(false);
        item = menu.findItem(R.id.quiz);
        if(item!=null)
            item.setVisible(false);
        item = menu.findItem(R.id.real_login);
        if(item!=null)
            item.setVisible(false);
        return true;
    }
}