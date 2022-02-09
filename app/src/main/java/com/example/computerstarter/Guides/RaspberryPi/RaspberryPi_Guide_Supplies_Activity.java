package com.example.computerstarter.Guides.RaspberryPi;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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

public class RaspberryPi_Guide_Supplies_Activity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapterPcGuide;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction, exit;

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
                startActivity(new Intent(this, RaspberryPi_Guide_Installation_Activity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Guides_Activity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            finish();
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Supplies Guide");
        item_Intro.setDescription("Here is where you will be able find out what components will be needed " +
                "to build a computer and be able to achieve that life long dream of building a computer.");
        item_Intro.setImage(R.drawable.pc_build_link);

        OnboardingItem item_CPU = new OnboardingItem();
        item_CPU.setTitle("CPU");
        item_CPU.setDescription("First thing you need to get is the CPU");
        item_CPU.setImage(R.drawable.cpu_link);

        OnboardingItem item_motherboard = new OnboardingItem();
        item_motherboard.setTitle("Motherboard");
        item_motherboard.setDescription("Second is the motherboard");
        item_motherboard.setImage(R.drawable.motherboard_link);

        OnboardingItem item_RAM = new OnboardingItem();
        item_RAM.setTitle("RAM (Memory)");
        item_RAM.setDescription("GET YOUR RAM");
        item_RAM.setImage(R.drawable.memory_link);

        OnboardingItem item_CPUCOOL = new OnboardingItem();
        item_CPUCOOL.setTitle("CPU COOLER");
        item_CPUCOOL.setDescription("GET YOUR RAM");
        item_CPUCOOL.setImage(R.drawable.cpu_cooler_link);

        OnboardingItem item_GPU = new OnboardingItem();
        item_GPU.setTitle("GPU(VGA)");
        item_GPU.setDescription("GET YOUR RAM");
        item_GPU.setImage(R.drawable.vga_link);

        OnboardingItem item_STORAGE = new OnboardingItem();
        item_STORAGE.setTitle("STORAGE");
        item_STORAGE.setDescription("GET YOUR STORAGE");
        item_STORAGE.setImage(R.drawable.storage_link);;

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_CPU);
        onboardingItemPcGuideActivityList.add(item_motherboard);
        onboardingItemPcGuideActivityList.add(item_RAM);
        onboardingItemPcGuideActivityList.add(item_CPUCOOL);
        onboardingItemPcGuideActivityList.add(item_GPU);
        onboardingItemPcGuideActivityList.add(item_STORAGE);
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
