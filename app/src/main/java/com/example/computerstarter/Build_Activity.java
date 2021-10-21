package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Build_Activity extends AppCompatActivity {
    private String[] diffTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.title);
        ListView listView = findViewById(R.id.lvEdu);
        diffTitles = getResources().getStringArray(R.array.comp_names);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                Build_Activity.this,
                android.R.layout.simple_list_item_1,
                diffTitles
        );
        listView.setAdapter(listViewAdapter);
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("names");
        getSupportActionBar().setTitle(name);
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
