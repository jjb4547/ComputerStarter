package com.example.computerstarter.SampleProjects.RaspiProj.RaspberryPi_Emulator;

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

public class RaspberryPi_Emulator_Supplies extends AppCompatActivity {

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
        help.setVisibility(View.GONE);

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
                startActivity(new Intent(this, RaspberryPi_Emulator_SD.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Emulator.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Supplies Guide");
        item_Intro.setDescription("These are the supplies you will need for the emulator.");
        item_Intro.setImage(R.drawable.rasp_logo);

        OnboardingItem item_rasp = new OnboardingItem();
        item_rasp.setTitle("Raspberry Pi using ethernet or WIFI with power cord");
        item_rasp.setDescription("Get your Raspberry Pi with internet capabilites. You will need " +
                "a microUSB cord to power it.");
        item_rasp.setImage(R.drawable.raspberry_pi);

        OnboardingItem item_screen = new OnboardingItem();
        item_screen.setTitle("A seperate screen and HDMI cord to display the OS on the Raspberry Pi");
        item_screen.setDescription("You will need a seperate screen and cord to show whats on the Raspberry Pi");
        item_screen.setImage(R.drawable.raspberry_pi);

        OnboardingItem item_sdcard = new OnboardingItem();
        item_sdcard.setTitle("8GB SD or MicroSD");
        item_sdcard.setDescription("The size depends on how many ROMs you plan to get");
        item_sdcard.setImage(R.drawable.sd_card);

        OnboardingItem item_breadboard = new OnboardingItem();
        item_breadboard.setTitle("Keyboard and Mouse");
        item_breadboard.setDescription("Make sure you have a breadboard and some jumper wires. " +
                "The colors do not matter but as a recommendation make sure that the voltage are red " +
                "and any ground wires are black.");
        item_breadboard.setImage(R.drawable.breadboard);

        OnboardingItem item_ohm = new OnboardingItem();
        item_ohm.setTitle("Seperate computer");
        item_ohm.setDescription("You a computer you can use to flash the SD card");
        item_ohm.setImage(R.drawable.k_ohm_resistor);

        OnboardingItem item_dht22 = new OnboardingItem();
        item_dht22.setTitle("A game controller such as Playstation,Xbox,etc");
        item_dht22.setDescription("You will need a gamepad to control the games you get");
        item_dht22.setImage(R.drawable.dht22);


        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_rasp);
        onboardingItemPcGuideActivityList.add(item_screen);
        onboardingItemPcGuideActivityList.add(item_sdcard);
        onboardingItemPcGuideActivityList.add(item_breadboard);
        onboardingItemPcGuideActivityList.add(item_ohm);
        onboardingItemPcGuideActivityList.add(item_dht22);
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
            buttonOnboardingAction.setText("Wiring");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}
