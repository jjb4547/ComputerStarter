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

public class PC_Guide_Installation_Activity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapterPcGuide;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction, exit, help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
        setContentView(R.layout.onboard_layout);
        ConstraintLayout gradient = findViewById(R.id.gradient);
        gradient.setBackground(getResources().getDrawable(R.drawable.gradient_animation_installation));
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
                startActivity(new Intent(this, PC_Guide_Setup_Activity.class));
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
        item_Intro.setTitle("Welcome to the Installation Guide");
        item_Intro.setDescription("Here is where you will learn where all those components will go.");
        item_Intro.setImage(R.drawable.pc_build_link);

        OnboardingItem item_mot = new OnboardingItem();
        item_mot.setTitle("Motherboard Preparation");
        item_mot.setDescription("Take the motherboard and extras out of the box, but do not throw anything out." +
                "The extras such as the sata cables will be needed to connect the storage to the motheboard." +
                "It also will come with a metal frame called the I/O shield which may come pre attached to the motherboard." +
                "Lastly it will come with a manual that will be useful to refer to. ");
        item_mot.setImage(R.drawable.motherboard_link);

        OnboardingItem item_CPU = new OnboardingItem();
        item_CPU.setTitle("CPU preparation and installation");
        item_CPU.setDescription("Remove the CPU from its plastic case carefully to not damage the top or bottom sides" +
                "of the CPU. Most CPUs have a triangle on the corner to assist you in putting in the motherboard socket." +
                "The most common CPUs will be an Intel or AMD CPUs which both have their differences for install.");
        item_CPU.setImage(R.drawable.cpu_link);

        OnboardingItem item_AMD = new OnboardingItem();
        item_AMD.setTitle("AMD Installation");
        item_AMD.setDescription("Grab the CPU by its sides and locate the golden triangle on the CPU and the motherboard socket." +
                "Match these triangles together and get ready to insert the CPU. Lift the lever on the motherboard and gently let " +
                "the CPU fall into the right place. No force on the top will be needed for this and could damage the components if forcefully " +
                "inserted. Make sure the CPU is evenly in the socket and pull the lever down.");
        item_AMD.setImage(R.drawable.amd_cpu);

        OnboardingItem item_Intel = new OnboardingItem();
        item_Intel.setTitle("Intel Installation");
        item_Intel.setDescription("Grab the CPU by its side and locate the golden triangle on the CPU and the motherboard socket. Match these triangles" +
                "together and get ready to insert the CPU. Slide the lever up to open the socket and carefully allow the CPU to fall onto the pins. Lower the flap" +
                "slowly and then you may lowe the lever if all seems right.");
        item_Intel.setImage(R.drawable.intelicon);

        OnboardingItem item_mem = new OnboardingItem();
        item_mem.setTitle("Memory Installation (RAM)");
        item_mem.setDescription("Most motherboards have four memory slots or DIMM slots. If you are only installing two of the four then one must refer to the manual" +
                "or motherboard to know on which slots to use to take advantage of dual channel memory. To install the memory, open the tabs  of each slot you plan to fill. " +
                "Each stick of memory has a gap on the bottom which lines up with the notch on the slot. Now just lower the memory into the slot and apply enough pressure to " +
                "trigger the tabs on the side to lock the memory into place.");
        item_mem.setImage(R.drawable.memory_link);

        OnboardingItem item_M2 = new OnboardingItem();
        item_M2.setTitle("M.2 installation(Optional)");
        item_M2.setDescription("If you have a M.2 SSD then you will need to locate the place in which the M.2 SSD will be installed if your motherboard has this capability.");
        item_M2.setImage(R.drawable.storage_link);

        OnboardingItem item_GPU = new OnboardingItem();
        item_GPU.setTitle("GPU(VGA)");
        item_GPU.setDescription("GET YOUR RAM");
        item_GPU.setImage(R.drawable.vga_link);

        OnboardingItem item_STORAGE = new OnboardingItem();
        item_STORAGE.setTitle("STORAGE");
        item_STORAGE.setDescription("GET YOUR STORAGE");
        item_STORAGE.setImage(R.drawable.storage_link);;

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_mot);
        onboardingItemPcGuideActivityList.add(item_CPU);
        onboardingItemPcGuideActivityList.add(item_AMD);
        onboardingItemPcGuideActivityList.add(item_Intel);
        onboardingItemPcGuideActivityList.add(item_mem);
        onboardingItemPcGuideActivityList.add(item_M2);
        //onboardingItemPcGuideActivityList.add(item_GPU);
        //onboardingItemPcGuideActivityList.add(item_STORAGE);
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
