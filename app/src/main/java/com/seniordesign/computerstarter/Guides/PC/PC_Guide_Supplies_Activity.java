package com.seniordesign.computerstarter.Guides.PC;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.viewpager2.widget.ViewPager2;

import com.seniordesign.computerstarter.Education.PC_Part_Activity;
import com.seniordesign.computerstarter.Guides.OnboardHolder.OnboardingAdapter;
import com.seniordesign.computerstarter.Guides.OnboardHolder.OnboardingItem;
import com.example.computerstarter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PC_Guide_Supplies_Activity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2000;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private String[] list;
    private String quiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView log;
    private ImageView logBut;
    private MenuItem item_log;
    private MenuItem item_quiz;
    private MenuItem item_acc;
    private OnboardingAdapter onboardingAdapterPcGuide;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction, exit, help;

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
                startActivity(new Intent(this, PC_Guide_Installation_Activity.class)
                        .putExtra("from","Main"));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                finish();
            }
        });
        exit.setOnClickListener(view->{
            startActivity(new Intent(this, PC_Building_Guide_Activity.class)
                    .putExtra("from","Main"));
            overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
            finish();
        });
        help.setOnClickListener(view -> {
            startActivity(new Intent(this, PC_Part_Activity.class)
                    .putExtra("Act","Guide"));
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
        item_CPU.setDescription("First you will need the CPU. It acts as the brains of the computer should be one of the first parts you decide on.");
        item_CPU.setImage(R.drawable.cpu_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_motherboard = new OnboardingItem();
        item_motherboard.setTitle("Motherboard");
        item_motherboard.setDescription("The motherboard must match the socket of the CPU you chose. " +
                "This is an important choice because it will connect all the parts together.");
        item_motherboard.setImage(R.drawable.motherboard_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_RAM = new OnboardingItem();
        item_RAM.setTitle("RAM (Memory)");
        item_RAM.setDescription("The RAM will slot into the motherboard and depending on " +
                "which motherboard you chose can house up to four sticks.");
        item_RAM.setImage(R.drawable.memory_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_CPUCOOL = new OnboardingItem();
        item_CPUCOOL.setTitle("CPU COOLER");
        item_CPUCOOL.setDescription("The CPU cooler takes a lot of forms, but is vital to " +
                "keeping the your CPU at healthy temperatures ");
        item_CPUCOOL.setImage(R.drawable.cpu_cooler_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_GPU = new OnboardingItem();
        item_GPU.setTitle("GPU(VGA)");
        item_GPU.setDescription("The video card is important for rendering work and any kind of gaming. " +
                "Intel based PCs don’t need this part, but it does help.");
        item_GPU.setImage(R.drawable.vga_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_STORAGE = new OnboardingItem();
        item_STORAGE.setTitle("STORAGE");
        item_STORAGE.setDescription("The storage comes in a variety of form factors, " +
                "but you will need at least one form of it to store the OS.");
        item_STORAGE.setImage(R.drawable.storage_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_PSU = new OnboardingItem();
        item_PSU.setTitle("Power Supply Unit (PSU)");
        item_PSU.setDescription("The power supply allows the computer to run from wall power. " +
                "The wattage you will require depends on the components you choose.");
        item_PSU.setImage(R.drawable.psu_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_Case = new OnboardingItem();
        item_Case.setTitle("Case");
        item_Case.setDescription("Go get your case, just be aware that " +
                "some cases are designed for different sized motherboards, " +
                "so they need to be compatible.");
        item_Case.setImage(R.drawable.pc_case_link);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_os = new OnboardingItem();
        item_os.setTitle("Operating System");
        item_os.setDescription("Windows, MacOS, and Linux are the main operating systems.\n ");
        item_os.setImage(R.drawable.os_pc);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_others = new OnboardingItem();
        item_others.setTitle("Peripherals");
        item_others.setDescription("You will need a mouse, keyboard, and monitor to access and use the computer.");
        item_others.setImage(R.drawable.key_mouse);
        item_Intro.setLearn("Learn More Click on Help");

        OnboardingItem item_tools = new OnboardingItem();
        item_tools.setTitle("Tools");
        item_tools.setDescription("The tools needed will be a phillips head screwdriver and an empty usb drive to download the OS. " +
                "You will also need an anti-static bracelet since you do not want to ruin your components.\n" +
                "You are now ready to start building your PC");
        item_tools.setImage(R.drawable.tools_pc);


        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_CPU);
        onboardingItemPcGuideActivityList.add(item_motherboard);
        onboardingItemPcGuideActivityList.add(item_RAM);
        onboardingItemPcGuideActivityList.add(item_CPUCOOL);
        onboardingItemPcGuideActivityList.add(item_GPU);
        onboardingItemPcGuideActivityList.add(item_STORAGE);
        onboardingItemPcGuideActivityList.add(item_PSU);
        onboardingItemPcGuideActivityList.add(item_Case);
        onboardingItemPcGuideActivityList.add(item_os);
        onboardingItemPcGuideActivityList.add(item_others);
        onboardingItemPcGuideActivityList.add(item_tools);
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
        for (int i=0;i<layoutOnboardingIndicators.getChildCount();i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index== onboardingAdapterPcGuide.getItemCount()-1){
            buttonOnboardingAction.setText("Installation");
        }else{
            buttonOnboardingAction.setText("Next");
        }
    }
}
