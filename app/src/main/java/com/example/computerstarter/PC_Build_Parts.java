package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PC_Build_Parts extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("name");
        setContentView(R.layout.activity_pc_part_build);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        TextView text = findViewById(R.id.pc_build_parts);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
