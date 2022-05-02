package com.example.computerstarter.SampleProjects.Arduino.Projects.TemperatureSensor;

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

public class Arduino_Temperature_Sensor_Coding extends AppCompatActivity {

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
                startActivity(new Intent(this, Arduino_Temperature_Sensor.class));
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
                "2.) (Line 1-2) Import libraries\n" +
                "\t a.) #include <OneWire.h> \n" +
                "\t b.) #include <DallasTemperature.h>\n" +
                "3.) (Line 3) Define the pin you used(pin 2 in the diagram)\n" +
                "\t a.) #define ONE_WIRE_BUS 2 \n" +
                "4.) (Line 4) Define a oneWire instance\n" +
                "\t a.) OneWire oneWire(ONE_WIRE_BUS);\n" +
                "5.) (Line 5)Give Dallas Temperature our oneWire reference" +
                "\t a.) DallasTemperature sensors(&oneWire);\n" +
                "6.) (Line 6-9) These lines take place in the void setup()\n" +
                "The lines after line 6 will start the serial port and the library\n" +
                "\t a.) void setup(void){\n" +
                "\t b.) Serial.begin(9600);\n" +
                "\t c.) Serial.println(\"Dallas Temperature IC Control Library Demo\"); \n" +
                "\t d.)  sensors.begin();}\n" +
                "7.) (Line 10-20) These lines take place in the void loop(void)\n" +
                "8.) Call global temperature and sends command to get temperature readings" +
                "\t a.) Serial.print(\" Requesting temperatures...\"); \n" +
                "\t b.) sensors.requestTemperatures();\n" +
                "\t c.) Serial.println(\"DONE\");\n" +
                "\t d.) Serial.print(\"Temperature is: \"); \n" +
                "\t e.) Serial.print(sensors.getTempCByIndex(0));\n"+
                "\t f.)delay(1000);}\n" +
                "9.) Your computer screen should then begin showing the temperature being measured\n" +
                "\t a.) Temperature is: 22.12 Requesting temperature...DONE \n" +
                "\t b.) Temperature is: 22.12 Requesting temperature...DONE \n" +
                "\t c.) Temperature is: 22.12 Requesting temperature...DONE \n" +
                "\t d.) Temperature is: 22.19 Requesting temperature...DONE \n" +
                "Below is the full github of the libraries used for this project" +
                "https://github.com/milesburton/Arduino-Temperature-Control-Library"+
                "https://github.com/fdebrabander/Arduino-LiquidCrystal-I2C-library ");
        item_code.setImage(R.drawable.arduino_logo);

        OnboardingItem item_credit = new OnboardingItem();
        item_credit.setTitle("References");
        item_credit.setDescription("This project is by Konstantin Dimitrov from the project hub website\n" +
                "\nhttps://create.arduino.cc/projecthub/TheGadgetBoy/ds18b20-digital-temperature-sensor-and-arduino-9cc806?ref=platform&ref_id=424_popular__beginner_&offset=2");
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

