package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

public class Build_Activity extends AppCompatActivity {
    private String[] diffTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPageFragment main = new MainPageFragment();
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.title);
        CardView cpu = findViewById(R.id.cpu);
        CardView mot = findViewById(R.id.mot);
        CardView mem = findViewById(R.id.mem);
        CardView vga = findViewById(R.id.gpu);
        CardView psu = findViewById(R.id.psu);
        CardView stor = findViewById(R.id.stor);
        CardView mon = findViewById(R.id.mon);
        CardView cool = findViewById(R.id.cool);
        CardView pc_case = findViewById(R.id.id_case);
        cpu.setOnClickListener(view -> {
            Intent cpu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            cpu_intent.putExtra("name","CPU");
            startActivity(cpu_intent);
        });
        mot.setOnClickListener(view -> {
            Intent mot_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
            mot_intent.putExtra("name","Motherboards");
            startActivity(mot_intent);
        });
        mem.setOnClickListener(view -> {
                Intent mem_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                mem_intent.putExtra("name","Memory");
                startActivity(mem_intent);
        });
        vga.setOnClickListener(view -> {
                Intent vga_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                vga_intent.putExtra("name","Video Cards");
                startActivity(vga_intent);
        });
        psu.setOnClickListener(view -> {
                Intent psu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                psu_intent.putExtra("name","Power Supplies");
                startActivity(psu_intent);
        });
        stor.setOnClickListener(view -> {
                Intent stor_intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                stor_intent.putExtra("name","Storage");
                startActivity(stor_intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
