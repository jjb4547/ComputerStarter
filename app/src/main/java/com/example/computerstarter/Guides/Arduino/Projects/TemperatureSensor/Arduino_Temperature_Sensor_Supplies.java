package com.example.computerstarter.Guides.Arduino.Projects.TemperatureSensor;

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

public class Arduino_Temperature_Sensor_Supplies extends AppCompatActivity {
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
                startActivity(new Intent(this, Arduino_Temperature_Sensor_Wiring.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, Arduino_Temperature_Sensor.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Supplies Guide");
        item_Intro.setDescription("These are the supplies you will need for this project.");
        item_Intro.setImage(R.drawable.arduino_logo);

        OnboardingItem item_ard = new OnboardingItem();
        item_ard.setTitle("Arduino");
        item_ard.setDescription("Most commonly the UNO, but most models are compatible");
        item_ard.setImage(R.drawable.arduino_uno);

        OnboardingItem item_ide = new OnboardingItem();
        item_ide.setTitle("Arduino IDE on separate computer");
        item_ide.setDescription("Have a seperate computer with the Arduino IDE downloaded");
        item_ide.setImage(R.drawable.monitor_link);

        OnboardingItem item_tempsen = new OnboardingItem();
        item_tempsen.setTitle("DS18B20 Programmable Resolution 1-Wire Digital Thermometer");
        item_tempsen.setDescription(
                "Pin 1 is GND(Ground)\n" +
                "Pin 2 is DATA(Signal Pin)\n" +
                "Pin 3 is VDD(Power)\n");
        item_tempsen.setImage(R.drawable.arduino_ds18b20);

        OnboardingItem item_breadboard = new OnboardingItem();
        item_breadboard.setTitle("Breadboard and Jumper Wires");
        item_breadboard.setDescription("Make sure you have a breadboard and some jumper wires. " +
                "The colors do not matter but as a recommendation make sure that the voltage are red " +
                "and any ground wires are black.");
        item_breadboard.setImage(R.drawable.breadboard);

        OnboardingItem item_ohm = new OnboardingItem();
        item_ohm.setTitle("4.75k Ohm Resistor");
        item_ohm.setDescription("Make sure to either read the colors or use a voltmeter to check.");
        item_ohm.setImage(R.drawable.k_ohm_resistor);

        //need to make the top look like the bottom
        /*OnboardingItem item_dht22 = new OnboardingItem();
        item_dht22.setTitle("DS18B20");
        item_dht22.setDescription("Pin 1 is GND (Ground))\n" +
                "Pin 2 is DATA (The signal pin)\n" +
                "Pin 3 is VCC (Power Supply/Voltage)\n");

        item_dht22.setImage(R.drawable.dht22);
        */

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_ard);
        onboardingItemPcGuideActivityList.add(item_ide);
        onboardingItemPcGuideActivityList.add(item_tempsen);
        onboardingItemPcGuideActivityList.add(item_breadboard);
        onboardingItemPcGuideActivityList.add(item_ohm);
        //onboardingItemPcGuideActivityList.add(item_dht22);
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
