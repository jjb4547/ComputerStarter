package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.R;

public class WebPageEducation extends AppCompatActivity {
    WebView web;
    ImageButton homeBut;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpagelayout);
        web = findViewById(R.id.webView);
        String comp = getIntent().getExtras().getString("component");
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Callback());
        if(getIntent().getExtras().getString("component").equals("CPU")){
            web.loadUrl(getResources().getString(R.string.cpu_link));
        }else if(getIntent().getExtras().getString("component").equals("MotherBoard")){
            web.loadUrl(getResources().getString(R.string.mot_link));
        }else if(getIntent().getExtras().getString("component").equals("Memory")){
            web.loadUrl(getResources().getString(R.string.mem_link));
        } else if(getIntent().getExtras().getString("component").equals("Storage")){
            web.loadUrl(getResources().getString(R.string.stor_link));
        }else if(getIntent().getExtras().getString("component").equals("Power Supply")){
            web.loadUrl(getResources().getString(R.string.psu_link));
        }else if(getIntent().getExtras().getString("component").equals("CPU Cooler")){
            web.loadUrl(getResources().getString(R.string.cpucool_link));
        }else if(getIntent().getExtras().getString("component").equals("Monitor")){
            web.loadUrl(getResources().getString(R.string.mon_link));
        }else if(getIntent().getExtras().getString("component").equals("Video Card")){
            web.loadUrl(getResources().getString(R.string.gpu_link));
        }else if(getIntent().getExtras().getString("component").equals("Case")){
            web.loadUrl(getResources().getString(R.string.case_link));
        }
        homeBut = findViewById(R.id.homeBut);
        homeBut.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), Education_Tabbed.class)
                    .putExtra("from", getIntent().getStringExtra("from"))
                    .putExtra("component", comp)
                    .putExtra("Act",getIntent().getExtras().getString("Act")));
        });


    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}
