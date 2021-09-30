package com.example.computerstarter;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    RelativeLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        layout = findViewById(R.id.relativelayout);
        layout.setOnTouchListener(new OnSwipeTouchListener(SettingsActivity.this){
            @Override
            public void onSwipeRight(){
                super.onSwipeRight();
                finish();
            }
        });
        Button buttonback = findViewById(R.id.buttonback);
        buttonback.setOnClickListener(view -> {
            finish();
        });
    }
}
