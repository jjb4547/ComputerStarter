package com.example.computerstarter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    int counter =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        TextView textView = findViewById(R.id.percent);
        textView.setText("");
        final long period = 50;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(counter<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Loading");
                        }
                    });
                    progressBar.setProgress(counter);
                    counter++;
                }else{
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },0,period);

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
