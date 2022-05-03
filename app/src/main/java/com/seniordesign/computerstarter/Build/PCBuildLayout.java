package com.seniordesign.computerstarter.Build;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.R;

public class PCBuildLayout extends AppCompatActivity {
    private CardView cpu,mot,mem,stor,psu,cpuCool,mon,gpu,pcCase;
    private int[] partsID;
    private int[] numParts;
    private String name_action_bar;
    ImageButton leaveParts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcbuildlayout);
        partsID = getIntent().getIntArrayExtra("ID");
        numParts = getIntent().getIntArrayExtra("Num");
        name_action_bar = getIntent().getStringExtra("Build");
        cpu = findViewById(R.id.cpu);
        mot = findViewById(R.id.mot);
        mem = findViewById(R.id.mem);
        stor = findViewById(R.id.stor);
        psu = findViewById(R.id.psu);
        cpuCool = findViewById(R.id.cool);
        mon = findViewById(R.id.mon);
        gpu = findViewById(R.id.gpu);
        pcCase = findViewById(R.id.id_case);
        leaveParts = findViewById(R.id.leaveParts);
        leaveParts.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, Build_Activity.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        checkCardViewVisibility();
        cpu.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","CPU").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        mot.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Motherboards").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        mem.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Memory").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID).putExtra("Num",numParts));
        });
        gpu.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Video Cards").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        psu.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Power Supplies").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        stor.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Storage").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        cpuCool.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","CPU Cooler").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID)
                    .putExtra("Num",numParts));
        });
        mon.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Monitor").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID).putExtra("Num",numParts));
        });
        pcCase.setOnClickListener(view -> {
            startActivity(new Intent(PCBuildLayout.this, PC_Build_Parts.class)
                    .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("name","Cases").putExtra("Build",name_action_bar)
                    .putExtra("ID",partsID).putExtra("Num",numParts));
        });
    }

    private void checkCardViewVisibility() {
        if(partsID[0]!=-1){
            cpu.setVisibility(View.GONE);
        }else{
            cpu.setVisibility(View.VISIBLE);
        }
        if(partsID[1]!=-1){
            mot.setVisibility(View.GONE);
        }else{
            mot.setVisibility(View.VISIBLE);
        }
        if(partsID[2]!=-1){
            mem.setVisibility(View.GONE);
        }else{
            mem.setVisibility(View.VISIBLE);
        }
        if(partsID[3]!=-1){
            stor.setVisibility(View.GONE);
        }else{
            stor.setVisibility(View.VISIBLE);
        }
        if(partsID[4]!=-1){
            psu.setVisibility(View.GONE);
        }else{
            psu.setVisibility(View.VISIBLE);
        }
        if(partsID[5]!=-1){
            cpuCool.setVisibility(View.GONE);
        }else{
            cpuCool.setVisibility(View.VISIBLE);
        }
        if(partsID[6]!=-1){
            mon.setVisibility(View.GONE);
        }else{
            mon.setVisibility(View.VISIBLE);
        }
        if(partsID[7]!=-1){
            gpu.setVisibility(View.GONE);
        }else{
            gpu.setVisibility(View.VISIBLE);
        }
        if(partsID[8]!=-1){
            pcCase.setVisibility(View.GONE);
        }else{
            pcCase.setVisibility(View.VISIBLE);
        }
    }
}
