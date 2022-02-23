package com.example.computerstarter.Guides.PC;

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

public class PC_Guide_Setup_Activity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapterPcGuide;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction, exit, help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
        setContentView(R.layout.onboard_layout);
        ConstraintLayout gradient = findViewById(R.id.gradient);
        gradient.setBackground(getResources().getDrawable(R.drawable.gradient_animation_setup));
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
                startActivity(new Intent(this, PC_Building_Guide_Activity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, PC_Building_Guide_Activity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
        help.setVisibility(View.GONE);
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Setup Guide");
        item_Intro.setDescription("You just installed everything into your computer. Now lets get the PC booting and installing the OS.");
        item_Intro.setImage(R.drawable.pc_build_link);

        OnboardingItem item_STORAGE = new OnboardingItem();
        item_STORAGE.setTitle("Startup and Organization");
        item_STORAGE.setDescription("Congratulations your PC has been put together, but we now need to make sure it is working properly and your " +
                "wires are neatly organized. Your case may not be put back together, but that's fine for now. Plug in your power cable to an outlet, and " +
                "switch the power switch on the back of your computer on. Make sure your computer is upright before pressing the power button on the front of your computer. " +
                "(Remember, computers should not be moved or worked on when powered on.) Refer to Troubleshooting and problems if it does not power on. Now that we " +
                "know it works, power off the computer and flip the power switch on the power supply and remove power from the wall. Now that the computer " +
                "is safe to work on, gather the cables orderly, and put them together, securing them with zip ties. Now you can put your case back together and power up your computer " +
                "for the next step.");
        item_STORAGE.setImage(R.drawable.bios);

        OnboardingItem item_OS = new OnboardingItem();
        item_OS.setTitle("Installing Operating System");
        item_OS.setDescription("To start off with, you will need an empty 4 GB flash drive to download either Linux or Windows Operating Systems. First " +
                "you need to download the linux or windows system. There are many versions of linux that you may choose; our recommendation is Ubuntu (\"https://ubuntu.com/download.\" For windows " +
                "we recommend the newest version from here (\"https://www.microsoft.com/en-us/software-download/\". Make sure when downloading it is set to install on your USB flash drive. Now " +
                "that you have the OS, plug it into your computer and power it on. Press either f12/f8/f1/f2/del/esc to get to the boot menu. Select your OS and start it. On your computer you should " +
                "see a disk install application. Click it and run through the steps until you are finished. After it is done, restart your computer and your OS should be installed and ready to go.");
        item_OS.setImage(R.drawable.os_pc);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_STORAGE);
        onboardingItemPcGuideActivityList.add(item_OS);
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
