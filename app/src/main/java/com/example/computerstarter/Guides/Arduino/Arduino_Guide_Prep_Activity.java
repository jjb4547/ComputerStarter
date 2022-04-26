package com.example.computerstarter.Guides.Arduino;

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

public class Arduino_Guide_Prep_Activity extends AppCompatActivity {

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
                startActivity(new Intent(this, Arduino_Guide_Setup_Activity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, Arduino_Guides_Activity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
        help.setVisibility(View.GONE);
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Prep Guide for Arduino");
        item_Intro.setDescription("This is where we will guide you through " +
                "your first time set up of your Arduino");
        item_Intro.setImage(R.drawable.arduino_logo);

        OnboardingItem item_1 = new OnboardingItem();
        item_1.setTitle("Arduino");
        item_1.setDescription("• First and foremost you will need one of the many Arduino models. " +
                "If you bought the Arduino alone you will need to buy some extra supplies. Arduinos " +
                "require a micro usb cord in order to power and connect it to your personal computer. It will need to be " +
                "cable of powering the Arduino and doing data transfer.");

        item_1.setImage(R.drawable.arduino_uno);

        OnboardingItem item_2 = new OnboardingItem();
        item_2.setTitle("Separate Computer");
        item_2.setDescription("• Next you will need a separate computer in order to write your code and upload it to" +
                "the Arduino. Most if not all computers should be capable as long as they have one free usb port " +
                "and ability to download the Arduino IDE." +
                "\n• You may also purchase some optional add ons that arent necessary for set up " +
                ",but may be useful in the future such memory cards, shields, and carriers.");
        item_2.setImage(R.drawable.monitor_link);

        /*OnboardingItem item_2 = new OnboardingItem();
        item_2.setTitle("MicroSD setup");
        item_2.setDescription("  There are many OS options, but as a beginner the best place to start is the Raspberry Pi OS which can serve most needs. You will want to navigate " +
                "to https://www.raspberrypi.com/software/ on your computer to download the Raspberry Pi OS. You will then want to insert your microSD card into your computer " +
                "or use your SD card reader. When you launch the installer your system may try to warn you, but make sure to run it anyway. Select Raspberry Pi OS and the SD card you " +
                "inserted then select WRITE. Once it's done you can eject your SD card from your computer and prepare for the next steps.");
        item_2.setImage(R.drawable.sd_card);
         */

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_1);
        onboardingItemPcGuideActivityList.add(item_2);
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
