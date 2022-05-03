package com.seniordesign.computerstarter.SampleProjects.RaspiProj.RaspberryPi_Emulator;

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

import com.seniordesign.computerstarter.Guides.OnboardHolder.OnboardingAdapter;
import com.seniordesign.computerstarter.Guides.OnboardHolder.OnboardingItem;
import com.example.computerstarter.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RaspberryPi_Emulator_Controller extends AppCompatActivity {

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
                startActivity(new Intent(this, RaspberryPi_Emulator.class));
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
        item_Intro.setTitle("Welcome to controller configuration and last notes");
        item_Intro.setDescription("The Raspberry Pi should be on and connected to a display");
        item_Intro.setImage(R.drawable.rasp_logo);

        OnboardingItem item_prep = new OnboardingItem();
        item_prep.setTitle("Get your controller ready");
        item_prep.setDescription("You will be meet with a welcome screen. Here you will need to" +
                " connect your controller of choice. Hold down a button and you will be able to " +
                "configure all the buttons for that controller. Now it is ready for use.");
        item_prep.setImage(R.drawable.rasp_logo);

        OnboardingItem item_code = new OnboardingItem();
        item_code.setTitle("Adding ROMS part one");
        item_code.setDescription("Once you obtain a ROM you will need a thumb drive to send it " +
                "into the emulator. You will need to enable “Enable USB ROM Service scripts“ on the " +
                "Raspberry Pi for the system to see the thumb drive. ");
        item_code.setImage(R.drawable.emulator_games);

        OnboardingItem item_save = new OnboardingItem();
        item_save.setTitle("Adding ROMS part two");
        item_save.setDescription("Have a folder on the named retropie on the thumb drive and plug it " +
                "in. This will take a few minutes but it will create folders for the systems it supports. " +
                "You will simply drop the ROM in the right folder and then plug it back into the Raspberry Pi. " +
                "After a few minutes it should show up on the system.");
        item_save.setImage(R.drawable.emulator_games);

        OnboardingItem item_credit = new OnboardingItem();
        item_credit.setTitle("References");
        item_credit.setDescription("This project is by Emmet from the pimylifeup website with help from " +
                "the official RetroPie website\n" +
                "\nhttps://pimylifeup.com/raspberry-pi-retropie/\n" +
                "\nhttps://retropie.org.uk/docs/First-Installation/");
        item_credit.setImage(R.drawable.rasp_logo);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_prep);
        onboardingItemPcGuideActivityList.add(item_code);
        onboardingItemPcGuideActivityList.add(item_save);
        onboardingItemPcGuideActivityList.add(item_credit);
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

