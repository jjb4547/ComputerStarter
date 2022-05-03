package com.seniordesign.computerstarter.SampleProjects.Arduino.Projects.RangeDetector;

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

public class Arduino_Range_Detector_Coding extends AppCompatActivity {

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
                startActivity(new Intent(this, Arduino_Range_Detector.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, Arduino_Range_Detector.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Coding Guide");
        item_Intro.setDescription("We will now be coding the Arduino with the Arduino IDE");
        item_Intro.setImage(R.drawable.arduino_logo);

        OnboardingItem item_prep = new OnboardingItem();
        item_prep.setTitle("Preparing the Arduino");
        item_prep.setDescription("1.) Make sure to have a data cable \n" +
                "2.) Make sure to have a separate computer with Arduino IDE ready");
        item_prep.setImage(R.drawable.arduino_logo);

        OnboardingItem item_code = new OnboardingItem();
        item_code.setTitle("Coding on the Arduino IDE");
        item_code.setDescription("1.) Create the file for the code\n" +
                "\t a.) The following is all contained in the file\n" +
                "2.) (Line 1-2) Define pins\n" +
                "\t a.) #define trigPin 13 \n" +
                "\t b.) #define echoPin 12\n" +
                "3.) (Line 3) These lines take place in the void setup()\n" +
                "\t a.) void setup(){  \n" +
                "4.) (Line 4-6) Begin serial monitor and set pins as input and ouput\n" +
                "\t a.) Serial.begin (9600);\n" +
                "\t b.) pinMode(trigPin, OUTPUT);\n" +
                "\t c.) pinMode(echoPin, INPUT); }\n" +
                "5.) (Line 7-14) These lines take place in the void loop(void)" +
                "These lines will write to the pins in prepartion for the prints later\n" +
                "\t a.) long duration, distance;\n" +
                "\t b.) digitalWrite(trigPin, LOW);\n" +
                "\t c.) delayMicroseconds(2); \n" +
                "\t d.) digitalWrite(trigPin, HIGH);\n" +
                "\t e.) delayMicroseconds(10);\n" +
                "\t f.) digitalWrite(trigPin, LOW);\n" +
                "\t g.) duration = pulseIn(echoPin, HIGH);\n" +
                "\t h.) distance = (duration/2) / 29.1;\n" +
                "6.) (Line 15-20) These lines take place in the if and else\n" +
                "These lines will finally print the distance or show it as out of range\n" +
                "\t a.) if (distance >= 200 || distance <= 0){\n" +
                "\t b.) Serial.println(\"Out of range\");}\n" +
                "\t c.) else{ \n" +
                "\t d.) Serial.print(distance);\n" +
                "\t e.) Serial.println(\" cm\");}\n" +
                "\t d.) delay(500)}\n" +
                "Your output should look like this in the Serial Monitor\n" +
                "12 cm\n"+
                "13 cm\n"+
                "Out of Range\n");
        item_code.setImage(R.drawable.arduino_logo);

        OnboardingItem item_credit = new OnboardingItem();
        item_credit.setTitle("References");
        item_credit.setDescription("This project is by RZtronics from the project hub website\n" +
                "\nhttps://create.arduino.cc/projecthub/rztronics/ultrasonic-range-detector-using-arduino-and-sr-04f-8a804d?ref=search&ref_id=ultrasonic&offset=2");
        item_credit.setImage(R.drawable.arduino_logo);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_prep);
        onboardingItemPcGuideActivityList.add(item_code);
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