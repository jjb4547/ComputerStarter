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

        OnboardingItem item_precaut = new OnboardingItem();
        item_precaut.setTitle("Precautions (things to avoid) when Installing");
        item_precaut.setDescription("• Make sure you are never working on components of your computer while " +
                "it is plugged in and possibly powered.\n\n • The static bags that contain the motherboard, hardware, etc " +
                "can damage the hardware after being removed, so keep them away from exposed hardware after taking the hardware " +
                "it contains out. Static is a big problem that can destroy parts of your computer, so taking any steps to keep static " +
                "away is ideal.\n\n • Keep your hands clean and dry. Try to prevent touching the surface of the CPU. Hold the CPU by the sides " +
                "avoid grease on it to keep best cooling and operation.");
        item_precaut.setImage(R.drawable.amd_cpu);

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
        item_AMD.setDescription("Grab the CPU by its sides and locate the golden triangle on the CPU and the motherboard socket. " +
                "Match these triangles together and get ready to insert the CPU. Lift the lever on the motherboard and gently let " +
                "the CPU fall into the right place. No force on the top will be needed for this and could damage the components if forcefully " +
                "inserted. Make sure the CPU is evenly in the socket and pull the lever down.");
        item_AMD.setImage(R.drawable.amd_cpu);

        OnboardingItem item_Intel = new OnboardingItem();
        item_Intel.setTitle("Intel Installation");
        item_Intel.setDescription("Grab the CPU by its side and locate the golden triangle on the CPU and the motherboard socket. Match these triangles " +
                "together and get ready to insert the CPU. Slide the lever up to open the socket and carefully allow the CPU to fall onto the pins. Lower the flap " +
                "slowly and then you may lowe the lever if all seems right.");
        item_Intel.setImage(R.drawable.intelicon);

        OnboardingItem item_mem = new OnboardingItem();
        item_mem.setTitle("Memory Installation (RAM)");
        item_mem.setDescription("Most motherboards have four memory slots or DIMM slots. If you are only installing two of the four then one must refer to the manual " +
                "or motherboard to know on which slots to use to take advantage of dual channel memory. To install the memory, open the tabs  of each slot you plan to fill. " +
                "Each stick of memory has a gap on the bottom which lines up with the notch on the slot. Now just lower the memory into the slot and apply enough pressure to " +
                "trigger the tabs on the side to lock the memory into place.");
        item_mem.setImage(R.drawable.memory_link);

        OnboardingItem item_M2 = new OnboardingItem();
        item_M2.setTitle("M.2 installation(Optional)");
        item_M2.setDescription("If you have a motherboard that supports an M.2 drive, follow these simple steps. " +
                "First find the slot on your motherboard that is angled parallel to the motherboard. Unscrew the Phillips screw head located in the front of the slot. Take your M.2 drive " +
                "and insert it in a 30 degree angle to the motherboard. Finally make sure it is flat to the motherboard, then secure it with the phillips screw you removed earlier.");
        item_M2.setImage(R.drawable.storage_link);

        OnboardingItem item_thermal = new OnboardingItem();
        item_thermal.setTitle("Cooler and thermal paste application");
        item_thermal.setDescription("Most coolers come with the thermal paste already applied. If not then use your tube of thermal paste to apply a pea sized dot in the center of the CPU. " +
                "Then placing the cooler on top of the CPU will evenly distribute the thermal paste. Use 99% isopropyl alcohol to clean up any extra thermal paste or if you need to reapply " +
                "thermal paste.\n" +
                "To install the cooler you will want to line up the holes in the motherboard with the cooler. Use a screwdriver to screw in the cooler in a crisscross pattern. The cooler " +
                "will have a cable to power the fan that must be plugged into the CPU cooler port that is usually labeled CPU fan. After that try to manage the cable and try to tuck it in so that " +
                "it doesn't stick out.");
        item_thermal.setImage(R.drawable.cpu_cooler_link);

        OnboardingItem item_mot_inst = new OnboardingItem();
        item_mot_inst.setTitle("Motherboard Installation");
        item_mot_inst.setDescription("If the I/O shield is not pre-attached to the motherboard then you will need to install it first." +
                "Find the rectangular hole in the back of your computer case and install it from the inside. Make sure it's in the right " +
                "orientation and then push it in on all four corners. You will want to locate your motherboard installation screws in preparation " +
                "to insert the motherboard. You will need a screw and standoff for each hole in the motherboard. Make sure your case " +
                "is flat on your build surface and place the motherboard down gently while aligning the holes in the motherboard with the standoffs." +
                "Carefully screw in the motherboard with the motherboard screws.");
        item_mot_inst.setImage(R.drawable.vga_link);

        OnboardingItem item_fan = new OnboardingItem();
        item_fan.setTitle("Fans(Optional)");
        item_fan.setDescription("Some fans may already be installed onto your computer case. If you have the space and ability to add extra " +
                "fans then follow these steps. Otherwise continue to the next section. First select the location on your case that you want to install your " +
                "fan. The idea is to pull heat out of your computer case, so putting it in a place where your case will have nothing next to it is ideal to " +
                "prevent blocked air flow. Next you should screw in the fan securely to the case with the provided screws. Finally you will connect the wires " +
                "either to your power supply with the 4 pin connector, or using an adapter connect it to the motherboard according to the manual.");
        item_fan.setImage(R.drawable.storage_link);

        OnboardingItem item_psu = new OnboardingItem();
        item_psu.setTitle("Power Supply Unit (PSU) Installation");
        item_psu.setDescription("Non-modular power supplies will come with all cables pre-connected to the power supply which means it will take some cable management to hide them. " +
                "If you have a modular power supply then the cables you will need are 24 pin atx cable(to power motherboard), eps cable (to power CPU), PCIe(to power Graphics card), and " +
                "the rest are optional depending on extra accessories you buy. The power supply cables will be clearly labeled with the words \"psu\" or \"type\"  signaling " +
                "the end that plugs into the power supply, unless already connected.");
        item_psu.setImage(R.drawable.psu_link);
        item_psu.setLearn("Continues on the next page.");

        OnboardingItem item_psu_cont = new OnboardingItem();
        item_psu_cont.setTitle("Power Supply Unit (PSU) Installation Continued");
        item_psu_cont.setDescription("After your power supply is ready remove the rear panel if removable and if not install it by pushing it into the side. The power supply should " +
                "be inserted with the fan facing down so it can pull in cool air. The power supply should then be secured with screws from the back.");
        item_psu_cont.setImage(R.drawable.psu_link);

        OnboardingItem item_storage = new OnboardingItem();
        item_storage.setTitle("Storage Installation");
        item_storage.setDescription("The three options for storage when building a computer are hard disk drive (HDD), solid state drives (SSD), and M.2 SSDs. The more commonly " +
                "used hard drives and solid-state drives follow a very similar set up. First locate your hard drive cage which should have tabs that when pushed release the mounting " +
                "tray. Then align the four holes on the tray with the holes in the hard drive or solid-state drive and screw them together. Solid-state drives can also be installed " +
                "with SSD trays which are smaller trays that function the same as the hard drive cage trays except cases may come or may not come with them available. There is a second " +
                "method available for hard drives only due to their size which involves inserting the hard drive into pegs located on the side walls of the tray. The should be holes on the " +
                "sides which when lined up correctly secure the hard drive securely. Make sure the storage is facing the back of the computer for cable management purposes. " +
                "The tray should slide easily back into the hard drive cage.\n Both the hard drive and the solid-state drive plug in fairly easily with a SATA cable in the smaller plug " +
                "and a larger SATA power cable which plugs into the bigger plug. The other side of the SATA cable will plug into the SATA port of your motherboard.");
        item_psu.setImage(R.drawable.storage_link);

        OnboardingItem item_plug = new OnboardingItem();
        item_plug.setTitle("Plugging in the cables");
        item_plug.setDescription("First start off by plugging in the cable for the usb ports on the front of the case " +
                "which plugs into the usb header on your motherboard labeled usb3 or something similar. " +
                "Additional usb cables may come labeled usb near the tip and also plug into the motherboard " +
                "header in either port labeled usb1 or usb2. Next plug in the cable labeled audio which will " +
                "enable the mic and audio ports on the front of your case. The connector will connect to the " +
                "motherboard in a place labeled jaud1 or something similar. Make sure to plug the cable all the way in, but careful " +
                "not to bend the pins. If you have any usb c ports on your case make sure to plug it into the motherboard in a spot labeled " +
                "usb3.1 or u31. If you have any fans they will go into any of the headers labeled cha_fan, opt_fan, sys_fan, or something " +
                "similar.");
        item_plug.setImage(R.drawable.psu_link);

        OnboardingItem item_conn_psu = new OnboardingItem();
        item_conn_psu.setTitle("Connecting the PSU");
        item_conn_psu.setDescription("Start off with the 24 pin cable which will connect to a large socket on the motherboard. Make sure to fully " +
                "connect the cable so that it locks into place. Next to connect are the cpu cable which plugs into an eight pin " +
                "socket usually located near the top of the motherboard. Sometimes you will have another four pin port which you may plug your extra " +
                "cpu cable into, but it is not required.");
        item_conn_psu.setImage(R.drawable.psu_link);

        OnboardingItem item_gpu_inst = new OnboardingItem();
        item_gpu_inst.setTitle("GPU installation");
        item_gpu_inst.setDescription("First make sure you are installing your graphics card on the highest pci slot for best performance. " +
                "Next remove the pci bracket that corresponds with the pci slot you're using and you may have to remove multiple depending on the " +
                "thickness of your graphics card. Now remove any covers the graphics card might have in preparation for plugging it in. Gently " +
                "raise the graphics card close to the pci slot and then slide it into it until it clicks or until the clip locks into place. If your " +
                "graphics card is sagging then you can hold it up with one hand and tighten the screws with the other. To plug in the graphics card must " +
                "match the number of pins of the graphics card to match the pci cable you will be plugging in.");
        item_gpu_inst.setImage(R.drawable.psu_link);

        onboardingItemPcGuideActivityList.add(item_Intro);
        onboardingItemPcGuideActivityList.add(item_precaut);
        onboardingItemPcGuideActivityList.add(item_mot);
        onboardingItemPcGuideActivityList.add(item_CPU);
        onboardingItemPcGuideActivityList.add(item_AMD);
        onboardingItemPcGuideActivityList.add(item_Intel);
        onboardingItemPcGuideActivityList.add(item_mem);
        onboardingItemPcGuideActivityList.add(item_M2);
        onboardingItemPcGuideActivityList.add(item_thermal);
        onboardingItemPcGuideActivityList.add(item_mot_inst);
        onboardingItemPcGuideActivityList.add(item_fan);
        onboardingItemPcGuideActivityList.add(item_psu);
        onboardingItemPcGuideActivityList.add(item_psu_cont);
        onboardingItemPcGuideActivityList.add(item_storage);
        onboardingItemPcGuideActivityList.add(item_plug);
        onboardingItemPcGuideActivityList.add(item_conn_psu);
        onboardingItemPcGuideActivityList.add(item_gpu_inst);
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
