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

public class RaspberryPi_Guide_Prep_Activity extends AppCompatActivity {

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
                startActivity(new Intent(this, RaspberryPi_Guide_Setup_Activity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Guides_Activity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
        help.setVisibility(View.GONE);
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Prep Guide");
        item_Intro.setDescription("This is where you will learn what you will need for this project.");
        item_Intro.setImage(R.drawable.rasp_logo);


        OnboardingItem item_1 = new OnboardingItem();
        item_1.setTitle("Supplies");
        item_1.setDescription("• First and foremost you will need a Raspberry Pi which comes in many different models. " +
                "Depending if you bought the Pi alone or as a kit then you might need to buy additional supplies. Most Raspberry " +
                "Pis require a micro usb cord and block, but the newest model takes usb c so be sure to check which model you have." +
                "\n• Next you will need a MicroSD card with at least a 8gb capacity to store the OS and all files you use. Some kits and " +
                "specific vendors will sell the microSD cards preloaded with Raspberry Pi OS, but any microSD will work. You might also need " +
                "an SD card reader if your computer does not have one since you will need it to add the OS to your microSD. \n• A wired keyboard and " +
                "mouse is required for initial setup, but after that you may switch to bluetooth peripherals if you'd like. You will need a TV or monitor " +
                "that takes HDMI in order to view and interact with the Raspberry Pi. You will also need an HDMI cable to connect your screen to your Raspberry " +
                "Pi or adapters if your screen has different ports. \n• Lastly, you will need an ethernet cable depending if your Raspberry Pi has wireless capabilities or not. " +
                "A case and headphones or speakers are optional, but may give your Pi some protection and sound.");
        item_1.setImage(R.drawable.raspberry_pi);

        OnboardingItem item_2 = new OnboardingItem();
        item_2.setTitle("MicroSD setup");
        item_2.setDescription("  There are many OS options, but as a beginner the best place to start is the Raspberry Pi OS which can serve most needs. You will want to navigate " +
                "to https://www.raspberrypi.com/software/ on your computer to download the Raspberry Pi OS. You will then want to insert your microSD card into your computer " +
                "or use your SD card reader. When you launch the installer your system may try to warn you, but make sure to run it anyway. Select Raspberry Pi OS and the SD card you " +
                "inserted then select WRITE. Once it's done you can eject your SD card from your computer and prepare for the next steps.");
        item_2.setImage(R.drawable.sd_card);

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
            buttonOnboardingAction.setText("Setup");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}
