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
        CardView mot = findViewById(R.id.motherboard);
        CardView mem = findViewById(R.id.memory);
        CardView vga = findViewById(R.id.vga);
        CardView psu = findViewById(R.id.psu);
        CardView stor = findViewById(R.id.stor);
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("names");
        getSupportActionBar().setTitle(name);
        cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Build_Activity.this, PC_Build_Parts.class);
                intent.putExtra("name","CPU");
                startActivity(intent);
            }
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
