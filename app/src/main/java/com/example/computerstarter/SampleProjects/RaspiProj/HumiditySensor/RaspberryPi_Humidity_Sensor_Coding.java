package com.example.computerstarter.SampleProjects.RaspiProj.HumiditySensor;

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

public class RaspberryPi_Humidity_Sensor_Coding extends AppCompatActivity {

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
                startActivity(new Intent(this, RaspberryPi_Humidity_Sensor.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, RaspberryPi_Humidity_Sensor.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            finish();
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItemPcGuideActivityList = new ArrayList<>();
        OnboardingItem item_Intro = new OnboardingItem();
        item_Intro.setTitle("Welcome to the Coding Guide");
        item_Intro.setDescription("We will now be coding the Pi");
        item_Intro.setImage(R.drawable.rasp_logo);

        OnboardingItem item_prep = new OnboardingItem();
        item_prep.setTitle("Preparing the Raspberry Pi");
        item_prep.setDescription("1.) Check for updates using these two commands\n" +
                "\t a.) sudo apt-get update\n" +
                "\t b.) sudo apt-get upgrade\n" +
                "2.) Install python 3 and pip to interact with the humidity sensor\n" +
                "\t a.) sudo apt-get install python3-dev python3-pip\n" +
                "3.) Check for the latest versions of setuptools,wheel, and pip python packages\n" +
                "\t a.) sudo python3 -m pip install --upgrade pip setuptoos wheel\n" +
                "4.) Using pip, install Adafruit's DHT library\n" +
                "\t a.) sudo pip3 install Adafruit_DHT\n" +
                "5.) Next we will use a python script to talk to the DHT22 sensor");
        item_prep.setImage(R.drawable.rasp_logo);

        OnboardingItem item_code = new OnboardingItem();
        item_code.setTitle("Coding the Raspberry Pi");
        item_code.setDescription("1.) Create a python script for the code.\n" +
                "\t a.) nano ~/humidity.py\n" +
                "\t b.) The next steps will outline each line of code within scripts\n" +
                "2.) (Line 1) Import the Adafruit_DHT library we got previously\n" +
                "\t a.) import Adafruit_DHT\n" +
                "3.) (Line 2) Define a constant DHT_SENSOR for the sensor we are using\n" +
                "\t a.) DHT_SENSOR = Adafruit_DHT.DHT22\n" +
                "4.) (Line 3) Define DHT_PIN with 4 since we are using GPIO4 pin in our connection\n" +
                "\t a.) DHT_PIN = 4\n" +
                "5.) (Line 4-5)Start a loop that will run until the user kills the script. Inside the loop " +
                "we use the library to get the humidity and temperature from the sensor\n" +
                "\t a.) while True:\n" +
                "\t b.) humidity, temperature = Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN)\n" +
                "6.) (Line 6-9) Lastly, check to see if the data fails to return or not\n" +
                "\t a.) if humidity is not None and temperature is not None:\n" +
                "\t b.) print(\"Temp={0:0.1f}*C Humidity = {1:0.1f}%\".format(temperature, humidity))\n" +
                "\t c.) else:\n" +
                "\t d.) print(\"Failed to retrieve data from humidity sensor\")\n" +
                "7.) Once your done typing out the code you can save it by hitting CTRL + X followed by ENTER\n" +
                "8.) Lastly run it with this command to see it working\n" +
                "\t a.) python3 ~/humidity.py\n" +
                "9.) The output should be displayed on the command line and look like this\n" +
                "\t a.) Temp = 25.6C Humidity = 51.8%\n" +
                "\t b.) Temp = 25.2C Humidity = 45.4%\n" +
                "\t c.) Temp = 25.1C Humidity = 45.4%\n" +
                "\t d.) Temp = 25.0C Humidity = 45.3%");
        item_code.setImage(R.drawable.rasp_logo);

        OnboardingItem item_save = new OnboardingItem();
        item_save.setTitle("Saving the Data from the Humidity Sensor");
        item_save.setDescription("1.) We will create another script that will allow us to save data to a file\n" +
                "\t a.) nano ~/humidity_logger.py\n" +
                "2.) (Line 1-3) Inside of this new script we will be importing the os, time, and os libraries. The os " +
                "library lets us check the file and the time library us timestamp our data.\n" +
                "\t a.) import os\n" +
                "\t a.) import time\n" +
                "\t a.) import Adafruit_DHT\n" +
                "3.) (Line 4-5) Same lines as before\n" +
                "\t a.) DHT_SENSOR = Adafruit_DHT.DHT22\n" +
                "\t b.) DHT_PIN = 4\n" +
                "4.) (Line 6-11) The try will handle erros and \"home/pi/humdity.csv\" will be where \"a+\" will append data to the file\n" +
                "\t a.) try:\n" +
                "\t b.) f = open('/home/pi/humidity.csv','a+')\n" +
                "\t c.) if os,stat('/home/pi/humidity.csv').st_size == 0:\n" +
                "\t d.) f.write('Date,Time,Temperature,Humidity\\r\\n')\n"+
                "\t e.) except:\n"+
                "\t f.) pass:\n"+
                "5.) (Line 12-13) Read data from the sensor and storing it in humidity and temperature variables" +
                "we use the library to get the humidity and temperature from the sensor\n" +
                "\t a.) while True:\n" +
                "\t b.) humidity, temperature = Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN)\n" +
                "6.) (Line 14-19) We write the data collected to out humidity.csv file with a sleep for 30 seconds which can be modified to suit your needs\n" +
                "\t a.) if humidity is not None and temperature is not None\n" +
                "\t b.) f.write('{0},{1},{2:0.1f}C,{3:0.1f}%\\r\\n'.format(time:strftime('%m/%d/%y'), time.strftime('%H.%M'), temperature, humidity))\n" +
                "\t c.) else:\n" +
                "\t d.)print(\"Failed to retrieve data from the humidity sensor\")\n"+
                "\t e.) \n"+
                "\t f.) time.sleep(30)\n"+
                "7.) Once your done typing out the code you can save it by hitting CTRL + X followed by ENTER\n" +
                "8.) Lastly run it with this command to see it working\n" +
                "\t a.) python3 ~/humidity_logger.py\n" +
                "9.) To see the data in the humidity.csv file then run this command\n" +
                "\t a.) nano ~/humidity.csv\n" +
                "10.) The contents of the file should looks like this\n" +
                "\t a.) Date, Time, Temperature, Humidity\n" +
                "\t b.) 05/04/19, 05:47,22.3C,50.6%\n" +
                "\t c.) 05/04/19, 05:48,22.2C,50.4%\n" +
                "\t d.) 05/04/19, 05:48,22.2C,50.6%\n" +
                "\t e.) 05/04/19, 05:49,22.2C,50.4%\n" +
                "\t f.) 05/04/19, 05:49,22.2C,50.4%\n" +
                "\t g.) 05/04/19, 05:50,22.2C,50.3%\n" +
                "\t h.) 05/04/19, 05:50,22.2C,50.3%\n" +
                "\t i.) 05/04/19, 05:51,22.2C,50.2%\n");
        item_save.setImage(R.drawable.rasp_logo);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_prep);
        onboardingItemPcGuideActivityList.add(item_code);
        onboardingItemPcGuideActivityList.add(item_save);
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
