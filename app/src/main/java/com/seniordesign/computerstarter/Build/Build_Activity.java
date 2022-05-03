package com.seniordesign.computerstarter.Build;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.computerstarter.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Build_Activity extends AppCompatActivity {
    private TextView cpuPrice, motPrice, memPrice, storPrice, psuPrice, coolPrice, monPrice, vgaPrice, casePrice;
    private String name, currentDateandTime;
    private String name_action_bar;
    private int[] partsID;
    private int[] numParts;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference build_ref;
    private Boolean isMenuOpen = false;
    private Build_Data build_data;
    private CardView cpu, mot, mem, vga, psu, stor, mon, cool, pc_case, addPart;
    private MenuItem save;
    private boolean first = true;
    private CheckedTextView checkedCPU, checkedMot, checkedMem, checkedStor, checkedPSU, checkedCool, checkedMon, checkedVGA, checkedCase;
    private TextView cpuTitle, motTitle, memTitle, storTitle, psuTitle, coolTitle, monTitle, vgaTitle, caseTitle, title, totalNum, wattageNum;
    private ImageView cpuImage;
    private ImageView motImage;
    private ImageView memImage;
    private ImageView storImage;
    private ImageView psuImage;
    private ImageView coolImage;
    private ImageView monImage;
    private ImageView vgaImage;
    private ImageView caseImage;
    private String price;
    private String ids,num;
    private RelativeLayout totalLayout;
    private ImageButton editName,saveButton,backButton,plusMem,minusMem;
    private TextView homeBut, numMem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBuildsFragment main = new MainBuildsFragment();
        if (mAuth.getCurrentUser() != null)
            build_ref = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        Build_Data build_data = new Build_Data();
        title = findViewById(R.id.welcome);
        backButton= findViewById(R.id.leaveBuild);
        saveButton = findViewById(R.id.saveBuild);
        plusMem = findViewById(R.id.plusMem);
        minusMem = findViewById(R.id.minusMem);
        totalLayout = findViewById(R.id.totalLayout);
        numMem = findViewById(R.id.NumberMem);
        ids = "";
        num="";
        name_action_bar = getIntent().getExtras().getString("Build");
        partsID = getIntent().getIntArrayExtra("ID");
        numParts = getIntent().getIntArrayExtra("Num");
        title.setText(name_action_bar);
        save = findViewById(R.id.save_button);
        totalNum = findViewById(R.id.total);
        wattageNum = findViewById(R.id.wattage);
        checkedCPU = findViewById(R.id.checkedCPU);
        checkedMot = findViewById(R.id.checkedMot);
        checkedMem = findViewById(R.id.checkedMem);
        checkedStor = findViewById(R.id.checkedStor);
        checkedPSU = findViewById(R.id.checkedPSU);
        checkedCool = findViewById(R.id.checkedCool);
        checkedMon = findViewById(R.id.checkedMon);
        checkedVGA = findViewById(R.id.checkedVGA);
        checkedCase = findViewById(R.id.checkedCase);
        editName = findViewById(R.id.editName);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss z");
        currentDateandTime = sdf.format(new Date());
        if (getIntent().getExtras().getInt("Edit") == 0) {
            editName.setVisibility(View.VISIBLE);
            editName.setOnClickListener(view -> {
                showAlertDialog();
            });
        }
        init();
        backButton.setOnClickListener(v -> {
            returnHomeCheck();
        });
        saveButton.setOnClickListener(view -> {
            saveButtonCheck();
        });
        if(partsID[1] == -1){
            plusMem.setVisibility(View.GONE);
            minusMem.setVisibility(View.GONE);
        }
        else if(partsID[1] != -1 && numParts[2] * PriceList.getMemSlots(partsID[2]) >= PriceList.getMemSlots(partsID[1]))
            plusMem.setVisibility(View.GONE);
        plusMem.setOnClickListener(view->{
            //System.out.println("MEM SLOTS: " + PriceList.getMemSlots(partsID[2]));
            numParts[2]=numParts[2]+1;
            memPrice.setText(String.format("$%.2f",PriceList.getPrice(partsID[2])*numParts[2]));
            totalNum.setText(String.format("$%.2f", getPriceSum()));
            wattageNum.setText(wattageStringBuilder());
            if(numParts[2]>1){
                minusMem.setVisibility(View.VISIBLE);
                numMem.setText("x"+numParts[2]);
                numMem.setVisibility(View.VISIBLE);
            }
            if(partsID[1] != -1 && numParts[2] * PriceList.getMemSlots(partsID[2]) >= PriceList.getMemSlots(partsID[1]))
                plusMem.setVisibility(View.GONE);
        });
        if(numParts[2]==1)
            minusMem.setVisibility(View.GONE);
        else if(numParts[2] > 1){
            numMem.setText("x" + numParts[2]);
            numMem.setVisibility(View.VISIBLE);
        }
        minusMem.setOnClickListener(view->{
            if(numParts[2]>0) {
                numParts[2] = numParts[2] - 1;
                memPrice.setText(String.format("$%.2f",PriceList.getPrice(partsID[2]) * numParts[2]));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                numMem.setText("x"+numParts[2]);
                wattageNum.setText(wattageStringBuilder());
            }
            if(numParts[2]==1) {
                minusMem.setVisibility(View.GONE);
                numMem.setVisibility(View.GONE);
            }
            if(partsID[1] != -1 && numParts[2] * PriceList.getMemSlots(partsID[2]) < PriceList.getMemSlots(partsID[1]))
                plusMem.setVisibility(View.VISIBLE);
        });
    }

    public void showAlertDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
        builder.setTitle("Insert Build Name");
        final TextInputEditText input = new TextInputEditText(Build_Activity.this);
        input.setHint("Build Name");
        input.setMaxLines(1);
        input.setMaxWidth(10);
        //final EditText input = new EditText(MyBuildActivity.this);
        builder.setView(input);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name_action_bar = input.getText().toString();
                title.setText("BUILD NAME: " + name_action_bar);
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        name = intent.getExtras().getString("Build");
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            returnHomeCheck();
        } else if (id == R.id.save_button) {
            saveButtonCheck();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveButtonCheck() {
        if (checkAuth()) {
            build_ref.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    build_data = documentSnapshot.toObject(Build_Data.class);
                    if (build_data.getBuild_name().size() < 5) {
                        long unixTime = System.currentTimeMillis() / 1000;
                        price = String.valueOf(unixTime) + String.valueOf(getPriceSum());
                        build_ref.update("build_name", FieldValue.arrayUnion(unixTime + name_action_bar));
                        build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                        build_ref.update("price", FieldValue.arrayUnion(price));
                        build_ref.update("parts_id", FieldValue.arrayUnion(unixTime + turnIntArrtoStr()));
                        build_ref.update("num_parts",FieldValue.arrayUnion(unixTime+turnIntArrtoStrParts()));
                        startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                    } else {
                        Toast.makeText(Build_Activity.this, "LOGIN!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setTitle("Warning");
            builder.setMessage("Not Logged In, Will Not Save");
            builder.setBackground(getResources().getDrawable(R.drawable.dialog_shape, null));
            builder.setPositiveButton("Okay", (dialogInterface, i) -> {
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            });
            builder.show();
        }
    }

    private void returnHomeCheck() {
        if (checkAuth()) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setTitle("Warning");
            builder.setMessage("Exiting without saving, would you like to save?");
            builder.setBackground(getResources().getDrawable(R.drawable.dialog_shape, null));
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Toast.makeText(Build_Activity.this, "Saved", Toast.LENGTH_SHORT).show();
                build_ref.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        build_data = documentSnapshot.toObject(Build_Data.class);
                        if (build_data.getBuild_name().size() < 5) {
                            long unixTime = System.currentTimeMillis() / 1000;
                            price = String.valueOf(unixTime) + String.valueOf(getPriceSum());
                            build_ref.update("build_name", FieldValue.arrayUnion(unixTime + name_action_bar));
                            build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                            build_ref.update("price", FieldValue.arrayUnion(price));
                            build_ref.update("parts_id", FieldValue.arrayUnion(unixTime + turnIntArrtoStr()));
                            build_ref.update("num_parts",FieldValue.arrayUnion(unixTime+turnIntArrtoStrParts()));
                            startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                        } else {
                            Toast.makeText(Build_Activity.this, "TOO MANY BUILDS, DELETE ONE!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
                if (getIntent().getExtras().getString("Edit").equals("yes")) {
                    String[] editBuild = getIntent().getStringArrayExtra("editBuild");
                    build_ref.update("build_name", FieldValue.arrayUnion(editBuild[0]));
                    build_ref.update("build_date", FieldValue.arrayUnion(editBuild[1]));
                    build_ref.update("price", FieldValue.arrayUnion(editBuild[2]));
                    build_ref.update("parts_id", FieldValue.arrayUnion(editBuild[3]));
                    build_ref.update("num_parts",FieldValue.arrayUnion(editBuild[4]));
                    Toast.makeText(Build_Activity.this, "Updates Not Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Build_Activity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                Toast.makeText(Build_Activity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
            });
            builder.show();
        } else {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setTitle("Warning");
            builder.setMessage("Not Logged In, Will Not Save");
            builder.setBackground(getResources().getDrawable(R.drawable.dialog_shape, null));
            builder.setPositiveButton("Okay", (dialogInterface, i) -> {
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            });
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean checkAuth() {
        if (mAuth.getCurrentUser() != null)
            return true;
        else
            return false;
    }

    public void init() {
        findID();
        insertDet();
        checkAddVisibility();
        checkCardVisibility();
        cardListeners();
    }

    private void checkAddVisibility() {
        boolean partsEmptySomewhere = false;
        for (int i = 0; i < partsID.length; i++) {
            if (partsID[i] == -1) {
                partsEmptySomewhere = true;
                addPart.setVisibility(View.VISIBLE);
                break;
            }
        }
        if (!partsEmptySomewhere) {
            addPart.setVisibility(View.GONE);
        }
    }

    private void findID() {
        addPart = findViewById(R.id.addPart);
        cpu = findViewById(R.id.cpu);
        mot = findViewById(R.id.mot);
        mem = findViewById(R.id.mem);
        vga = findViewById(R.id.gpu);
        psu = findViewById(R.id.psu);
        stor = findViewById(R.id.stor);
        mon = findViewById(R.id.mon);
        cool = findViewById(R.id.cool);
        pc_case = findViewById(R.id.id_case);
        cpuTitle = findViewById(R.id.cpuText);
        motTitle = findViewById(R.id.motText);
        memTitle = findViewById(R.id.memText);
        storTitle = findViewById(R.id.storText);
        psuTitle = findViewById(R.id.psuText);
        coolTitle = findViewById(R.id.coolText);
        monTitle = findViewById(R.id.monText);
        vgaTitle = findViewById(R.id.vgaText);
        caseTitle = findViewById(R.id.caseText);
        cpuImage = findViewById(R.id.cpu_link_image);
        motImage = findViewById(R.id.mot_link_image);
        memImage = findViewById(R.id.mem_link_image);
        storImage = findViewById(R.id.stor_link_image);
        psuImage = findViewById(R.id.psu_link_image);
        coolImage = findViewById(R.id.cool_link_image);
        monImage = findViewById(R.id.mon_link_image);
        vgaImage = findViewById(R.id.vga_link_image);
        caseImage = findViewById(R.id.case_link_image);
        cpuPrice = findViewById(R.id.cpu_Price);
        motPrice = findViewById(R.id.mot_Price);
        memPrice = findViewById(R.id.mem_Price);
        storPrice = findViewById(R.id.stor_Price);
        psuPrice = findViewById(R.id.psu_Price);
        coolPrice = findViewById(R.id.cool_Price);
        monPrice = findViewById(R.id.mon_Price);
        vgaPrice = findViewById(R.id.vga_Price);
        casePrice = findViewById(R.id.case_Price);
    }

    private void cardListeners() {
        addPart.setOnClickListener(view -> {
            startActivity(new Intent(Build_Activity.this, PCBuildLayout.class)
                    .putExtra("Edit", "Edit").putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("Build", name_action_bar).putExtra("ID", partsID).putExtra("Num",numParts));
        });
        cpu.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[0]) + " ?");
            builder.setTitle("CPU");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                cpuTitle.setText("Empty");
                cpuImage.setImageResource(R.drawable.ic_blank_image);
                cpuPrice.setText("$0.00");
                checkedCPU.setChecked(false);
                cpu.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                partsID[0] = -1;
                numParts[0]=0;
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                cpu.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "CPU").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });

        mot.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[1]) + " ?");
            builder.setTitle("Motherboard");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                motTitle.setText("Empty");
                motImage.setImageResource(R.drawable.ic_blank_image);
                motPrice.setText("$0.00");
                checkedMot.setChecked(false);
                partsID[1] = -1;
                numParts[1]=0;
                mot.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                mot.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Motherboards").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        mem.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[2]) + " ?");
            builder.setTitle("Removing Memory");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                memTitle.setText("Empty");
                memImage.setImageResource(R.drawable.ic_blank_image);
                memPrice.setText("$0.00");
                checkedMem.setChecked(false);
                partsID[2] = -1;
                numParts[2]=0;
                mem.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                mem.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Memory").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        vga.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[3]) + " ?");
            builder.setTitle("Graphics Card");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                vgaTitle.setText("Empty");
                vgaImage.setImageResource(R.drawable.ic_blank_image);
                vgaPrice.setText("$0.00");
                checkedVGA.setChecked(false);
                partsID[3] = -1;
                numParts[3]=0;
                vga.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                vga.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Video Cards").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        psu.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[4]) + " ?");
            builder.setTitle("Power Supply Unit");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                psuTitle.setText("Empty");
                psuImage.setImageResource(R.drawable.ic_blank_image);
                psuPrice.setText("$0.00");
                checkedPSU.setChecked(false);
                partsID[4] = -1;
                numParts[4]=0;
                psu.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                psu.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Power Supplies").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        stor.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[5]) + " ?");
            builder.setTitle("Storage");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                storTitle.setText("Empty");
                storImage.setImageResource(R.drawable.ic_blank_image);
                storPrice.setText("$0.00");
                checkedStor.setChecked(false);
                partsID[5] = -1;
                numParts[5]=0;
                stor.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                stor.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Storage").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        cool.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[6]) + " ?");
            builder.setTitle("Removing CPU Cooler");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                coolTitle.setText("Empty");
                coolImage.setImageResource(R.drawable.ic_blank_image);
                coolPrice.setText("$0.00");
                checkedCool.setChecked(false);
                partsID[6] = -1;
                numParts[6]=0;
                cool.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                wattageNum.setText(wattageStringBuilder());
                cool.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "CPU Cooler").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();

            return true;
        });

        mon.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[7]) + " ?");
            builder.setTitle("Monitor");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                monTitle.setText("Empty");
                monImage.setImageResource(R.drawable.ic_blank_image);
                monPrice.setText("$0.00");
                checkedMon.setChecked(false);
                partsID[7] = -1;
                numParts[7]=0;
                mon.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                mon.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Monitor").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
        pc_case.setOnLongClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setMessage("What would you like to do with " + PriceList.getName(partsID[8]) + " ?");
            builder.setTitle("Case");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                caseTitle.setText("Empty");
                caseImage.setImageResource(R.drawable.ic_blank_image);
                casePrice.setText("$0.00");
                checkedCase.setChecked(false);
                partsID[8] = -1;
                numParts[8]=0;
                pc_case.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                totalNum.setText(String.format("$%.2f", getPriceSum()));
                pc_case.setVisibility(View.GONE);
                checkAddVisibility();
            });
            builder.setNegativeButton("Edit", (dialog, which) -> {
                startActivity(new Intent(Build_Activity.this, PC_Build_Parts.class)
                        .putExtra("Edit", getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild", getIntent().getStringArrayExtra("editBuild"))
                        .putExtra("name", "Cases").putExtra("Build", name_action_bar)
                        .putExtra("ID", partsID).putExtra("Num",numParts));
            });
            builder.create().show();
            return true;
        });
    }

    private void checkCardVisibility() {
        if (partsID[0] != -1) {
            cpu.setVisibility(View.VISIBLE);
        } else {
            cpu.setVisibility(View.GONE);
        }
        if (partsID[1] != -1) {
            mot.setVisibility(View.VISIBLE);
        } else {
            mot.setVisibility(View.GONE);
        }
        if (partsID[2] != -1) {
            mem.setVisibility(View.VISIBLE);
        } else {
            mem.setVisibility(View.GONE);
        }
        if (partsID[3] != -1) {
            stor.setVisibility(View.VISIBLE);
        } else {
            stor.setVisibility(View.GONE);
        }
        if (partsID[4] != -1) {
            psu.setVisibility(View.VISIBLE);
        } else {
            psu.setVisibility(View.GONE);
        }
        if (partsID[5] != -1) {
            cool.setVisibility(View.VISIBLE);
        } else {
            cool.setVisibility(View.GONE);
        }
        if (partsID[6] != -1) {
            mon.setVisibility(View.VISIBLE);
        } else {
            mon.setVisibility(View.GONE);
        }
        if (partsID[7] != -1) {
            vga.setVisibility(View.VISIBLE);
        } else {
            vga.setVisibility(View.GONE);
        }
        if (partsID[8] != -1) {
            pc_case.setVisibility(View.VISIBLE);
        } else {
            pc_case.setVisibility(View.GONE);
        }
    }

    private void insertDet() {
        if (partsID[0] != -1) {
            cpuTitle.setText(PriceList.getName(partsID[0]));
            cpuPrice.setText(PriceList.getPriceAsString(partsID[0]));
            cpuImage.setImageResource(PriceList.getIcon(partsID[0]));
            cpu.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedCPU.setChecked(true);
        }
        if (partsID[1] != -1) {
            motTitle.setText(PriceList.getName(partsID[1]));
            motPrice.setText(PriceList.getPriceAsString(partsID[1]));
            motImage.setImageResource(PriceList.getIcon(partsID[1]));
            mot.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedMot.setChecked(true);
        }
        if (partsID[2] != -1) {
            memTitle.setText(PriceList.getName(partsID[2]));
            memPrice.setText("$ "+PriceList.getPrice(partsID[2])*numParts[2]);
            memImage.setImageResource(PriceList.getIcon(partsID[2]));
            mem.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedMem.setChecked(true);
        }
        if (partsID[3] != -1) {
            storTitle.setText(PriceList.getName(partsID[3]));
            storPrice.setText(PriceList.getPriceAsString(partsID[3]));
            storImage.setImageResource(PriceList.getIcon(partsID[3]));
            stor.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedStor.setChecked(true);
        }
        if (partsID[4] != -1) {
            psuTitle.setText(PriceList.getName(partsID[4]));
            psuPrice.setText(PriceList.getPriceAsString(partsID[4]));
            psuImage.setImageResource(PriceList.getIcon(partsID[4]));
            psu.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedPSU.setChecked(true);
        }
        if (partsID[5] != -1) {
            coolTitle.setText(PriceList.getName(partsID[5]));
            coolPrice.setText(PriceList.getPriceAsString(partsID[5]));
            coolImage.setImageResource(PriceList.getIcon(partsID[5]));
            cool.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedCool.setChecked(true);
        }
        if (partsID[6] != -1) {
            monTitle.setText(PriceList.getName(partsID[6]));
            monPrice.setText(PriceList.getPriceAsString(partsID[6]));
            monImage.setImageResource(PriceList.getIcon(partsID[6]));
            mon.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedMon.setChecked(true);
        }
        if (partsID[7] != -1) {
            vgaTitle.setText(PriceList.getName(partsID[7]));
            vgaPrice.setText(PriceList.getPriceAsString(partsID[7]));
            vgaImage.setImageResource(PriceList.getIcon(partsID[7]));
            vga.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedVGA.setChecked(true);
        }
        if (partsID[8] != -1) {
            caseTitle.setText(PriceList.getName(partsID[8]));
            casePrice.setText(PriceList.getPriceAsString(partsID[8]));
            caseImage.setImageResource(PriceList.getIcon(partsID[8]));
            pc_case.setCardBackgroundColor(getResources().getColor(R.color.cardview_light));
            checkedCase.setChecked(true);
        }
        totalNum.setText(String.format("$%.2f", getPriceSum()));
        wattageNum.setText(wattageStringBuilder());
    }


    public String turnIntArrtoStr() {
        String[] arr = new String[partsID.length];
        for (int i = 0; i < partsID.length; i++) {
            arr[i] = String.valueOf(partsID[i]);
        }
        ids = TextUtils.join(",", arr);
        return ids;
    }
    public String turnIntArrtoStrParts() {
        String[] arr = new String[numParts.length];
        for (int i = 0; i < numParts.length; i++) {
            arr[i] = String.valueOf(numParts[i]);
        }
        num = TextUtils.join(",", arr);
        return num;
    }

    public double getPriceSum() {
        double price = 0.0;
        for (int i = 0; i < partsID.length; i++) {
            if (partsID[i] != -1) {
                price = price + PriceList.getPrice(partsID[i])*numParts[i];
            }
            //System.out.println(price);
        }
        price = Math.floor(price * 100) / 100;
        return price;
    }

    public int getWattageTotal() {
        int voltage = 0;
        for (int i = 0; i < partsID.length-1; i++) {
            switch(i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 7:
                    if (partsID[i] != -1) {
                        voltage = voltage + PriceList.getWattage(partsID[i]) * numParts[i];
                    }
            }
            //System.out.println(voltage);
        }
        return voltage;
    }

    public String wattageStringBuilder(){
        String stringBuilder = "";
        if(partsID[4] == -1)
            stringBuilder = getWattageTotal() + "W";
        else
            stringBuilder = getWattageTotal() + "W / " + PriceList.getWattage(partsID[4]) + "W";
        return stringBuilder;
    }
}
