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

public class RaspberryPi_Emulator_SD extends AppCompatActivity {

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
                startActivity(new Intent(this, RaspberryPi_Emulator_Controller.class));
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

        OnboardingItem item_intro = new OnboardingItem();
        item_intro.setTitle("Welcome to the SD card Section");
        item_intro.setDescription("You will learn to prepare the SD card");
        item_intro.setImage(R.drawable.rasp_logo);

        OnboardingItem item_circuit = new OnboardingItem();
        item_circuit.setTitle("Download the RetroPie Image");
        item_circuit.setDescription("First you will need to visit the RetroPie website(https://retropie.org.uk/download/)"+
                "download the correct image for the Raspberry Pi");
        item_circuit.setImage(R.drawable.circuit);

        OnboardingItem item_etcher = new OnboardingItem();
        item_etcher.setTitle("Download Etcher or similar software");
        item_etcher.setDescription("You will then need to write the image to your SD card. You can do this using a tool " +
                "called “Etcher”. It will format the SD card for you and write the RetroPie image to it. You should " +
                "download and open the software with the SD card card connected to your computer. In Etcher you can click " +
                "“Select Image” and select the RetroPie image. Then you will need to “Select drive” with your SD card selected. " +
                "Finally you will be able to Flash the SD card. ");
        item_etcher.setImage(R.drawable.circuit);

        OnboardingItem item_plug = new OnboardingItem();
        item_plug.setTitle("Plug and play");
        item_plug.setDescription("You will now want to put the microSD card into the " +
                "SD card slot of your raspberry pi.Go Plug in the HDMI and power into cord into" +
                "the Raspberry Pi to begin the last step. ");
        item_plug.setImage(R.drawable.circuit);

        onboardingItemPcGuideActivityList.add(item_intro);
        onboardingItemPcGuideActivityList.add(item_circuit);
        onboardingItemPcGuideActivityList.add(item_etcher);
        onboardingItemPcGuideActivityList.add(item_plug);
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
            buttonOnboardingAction.setText("Coding");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}