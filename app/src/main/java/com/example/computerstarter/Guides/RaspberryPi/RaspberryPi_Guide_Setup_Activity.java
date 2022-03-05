package com.example.computerstarter.Guides.RaspberryPi;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.computerstarter.Guides.OnboardHolder.OnboardingAdapter;
import com.example.computerstarter.Guides.OnboardHolder.OnboardingItem;
import com.example.computerstarter.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RaspberryPi_Guide_Setup_Activity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapterPcGuide;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction, exit,help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
        setContentView(R.layout.onboard_layout);
        ConstraintLayout gradient = findViewById(R.id.gradient);
        AnimationDrawable animationDrawable = (AnimationDrawable) gradient.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
        exit = findViewById(R.id.exit);
        help = findViewById(R.id.help);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapterPcGuide);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicators(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });
        buttonOnboardingAction.setOnClickListener(view->{
            if(onboardingViewPager.getCurrentItem()+1< onboardingAdapterPcGuide.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
            }else{
                startActivity(new Intent(this, RaspberryPi_Guides_Activity.class)
                        .putExtra("from","Edu"));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Guides_Activity.class)
                    .putExtra("from","Edu"));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
        help.setVisibility(View.GONE);
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Setup Guide");
        item_Intro.setDescription("");
        item_Intro.setImage(R.drawable.rasp_logo);

        OnboardingItem item_Conn = new OnboardingItem();
        item_Conn.setTitle("Connect the Raspberry Pi");
        item_Conn.setDescription("The micro SD card may come in a bigger adapter that can but slide out of that and into " +
                "the side of the Raspberry Pi. Then connect the mouse and keyboard using the available usb ports. Make sure " +
                "to have a screen plugged into power and then use the HDMI connected to that and plug the other end into the Raspberry Pi. " +
                "Use an adapter if necessary to connect the HDMI. If you want to connect to the internet and your Raspberry Pi does not have " +
                "built in WIFI then you'll want to take an ethernet cable connected to your router and connect it to the ethernet port to the Raspberry Pi. " +
                "If you want to have sound you can connect speakers using the 3.5 mm jack.");
        item_Conn.setImage(R.drawable.raspberry_pi);

        OnboardingItem item_start = new OnboardingItem();
        item_start.setTitle("Starting the Raspberry Pi");
        item_start.setDescription("Plug in the power into a wall socket or usb port then the other end into the Raspberry Pi. A light should light up and you will " +
                "see raspberries on your screen which signify that it is booting up. After a few seconds the initial setup will appear guiding you through the set up. " +
                "Select your timezone, language, country, and password in the set up. You will have to connect to your WIFI if your board has wireless capabilities. " +
                "Finish updating your Raspberry Pi with any updates and then restart it at the end of setup.");
        item_start.setImage(R.drawable.raspberry_pi);

        OnboardingItem item_end = new OnboardingItem();
        item_end.setTitle("End");
        item_end.setDescription("You will be greeted with the Raspberry Pi desktop where you can decide where you want to take your projects.");
        item_end.setImage(R.drawable.raspberry_pi);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_Conn);
        onboardingItemPcGuideActivityList.add(item_start);
        onboardingItemPcGuideActivityList.add(item_end);
        onboardingAdapterPcGuide = new OnboardingAdapter(onboardingItemPcGuideActivityList);
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapterPcGuide.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i =0; i<indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicators(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i=0;i<childCount;i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index== onboardingAdapterPcGuide.getItemCount()-1){
            buttonOnboardingAction.setText("Finish");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}
