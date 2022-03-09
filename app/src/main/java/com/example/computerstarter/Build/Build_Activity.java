package com.example.computerstarter.Build;

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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference build_ref;
    private Boolean isMenuOpen = false;
    private Build_Data build_data;
    private CardView cpu,mot,mem,vga,psu,stor,mon,cool,pc_case;
    private MenuItem save;
    private boolean first=true;
    private CheckedTextView checkedCPU,checkedMot,checkedMem,checkedStor,checkedPSU,checkedCool,checkedMon,checkedVGA,checkedCase;
    private TextView cpuTitle,motTitle,memTitle,storTitle,psuTitle,coolTitle,monTitle,vgaTitle,caseTitle,title,totalNum;
    TextView cpuPrice,motPrice, memPrice, storPrice,  psuPrice, coolPrice, monPrice, vgaPrice, casePrice;
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
    private String ids;
    private RelativeLayout relLayout;
    String name,currentDateandTime;
    String name_action_bar;
    int[] partsID;
    private ImageButton editName;
    private TextView homeBut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBuildsFragment main = new MainBuildsFragment();
        if(mAuth.getCurrentUser()!=null)
            build_ref= firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
        main.addcardview = true;
        setContentView(R.layout.build_layout);
        Build_Data build_data = new Build_Data();
        title = findViewById(R.id.welcome);
        homeBut = findViewById(R.id.homeBut);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ids = "";
        name_action_bar = getIntent().getExtras().getString("Build");
        partsID = getIntent().getIntArrayExtra("ID");
        getSupportActionBar().setTitle("PC Parts List");
        title.setText("BUILD NAME: "+name_action_bar);
        save = findViewById(R.id.save_button);
        totalNum = findViewById(R.id.total);
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
        if(getIntent().getExtras().getInt("from")==0){
            editName.setVisibility(View.VISIBLE);
            editName.setOnClickListener(view -> {
                showAlertDialog();
            });
        }
        init();
        homeBut.setOnClickListener(v->{
            returnHomeCheck();
        });
    }

    public void showAlertDialog(){
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
                title.setText("BUILD NAME: "+name_action_bar);
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        name = intent.getExtras().getString("Build");
        int id = item.getItemId();
        if (item.getItemId()==android.R.id.home) {
            returnHomeCheck();
        }else if(id==R.id.save_button){
            saveButtonCheck();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveButtonCheck() {
        if(checkAuth()){
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
                        startActivity(new Intent(Build_Activity.this, MyBuildActivity.class)
                                .putExtra("from","Main"));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                    } else {
                        Toast.makeText(Build_Activity.this, "LOGIN!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setTitle("Warning");
            builder.setMessage("Not Logged In, Will Not Save");
            builder.setBackground(getResources().getDrawable(R.drawable.dialog_shape, null));
            builder.setPositiveButton("Okay",(dialogInterface, i) -> {
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            });
            builder.show();
        }
    }

    private void returnHomeCheck() {
        if(checkAuth()) {
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
                            build_ref.update("build_name", FieldValue.arrayUnion(unixTime+name_action_bar));
                            build_ref.update("build_date", FieldValue.arrayUnion(currentDateandTime));
                            build_ref.update("price", FieldValue.arrayUnion(price));
                            build_ref.update("parts_id", FieldValue.arrayUnion(unixTime + turnIntArrtoStr()));
                            startActivity(new Intent(Build_Activity.this, MyBuildActivity.class)
                                    .putExtra("from","Main"));
                            overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                        } else {
                            Toast.makeText(Build_Activity.this, "TOO MANY BUILDS, DELETE ONE!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
            builder.setNegativeButton("No",(dialogInterface, i) -> {
                if(getIntent().getExtras().getInt("from")==0){
                    String[] editBuild = getIntent().getStringArrayExtra("editBuild");
                    build_ref.update("build_name",FieldValue.arrayUnion(editBuild[0]));
                    build_ref.update("build_date",FieldValue.arrayUnion(editBuild[1]));
                    build_ref.update("price",FieldValue.arrayUnion(editBuild[2]));
                    build_ref.update("parts_id",FieldValue.arrayUnion(editBuild[3]));
                    Toast.makeText(Build_Activity.this,"Updates Not Saved",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Build_Activity.this,"Not Saved",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Build_Activity.this,MyBuildActivity.class));
                Toast.makeText(Build_Activity.this,"Not Saved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Build_Activity.this,MyBuildActivity.class)
                        .putExtra("from","Main"));
            });
            builder.show();
        }else{
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
            builder.setTitle("Warning");
            builder.setMessage("Not Logged In, Will Not Save");
            builder.setBackground(getResources().getDrawable(R.drawable.dialog_shape, null));
            builder.setPositiveButton("Okay",(dialogInterface, i) -> {
                startActivity(new Intent(Build_Activity.this, MyBuildActivity.class)
                        .putExtra("from","Main"));
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            });
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean checkAuth(){
        if(mAuth.getCurrentUser()!=null)
            return true;
        else
            return false;
    }
    public void init(){
        findID();
        insertDet();
        cardListeners();
    }

    private void findID() {
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
        cpu.setOnClickListener(view -> {
            Intent cpu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("from","Main");
            cpu_intent.putExtra("name","CPU");
            cpu_intent.putExtra("Build",name_action_bar);
            cpu_intent.putExtra("ID",partsID);
            startActivity(cpu_intent);
        });
        cpu.setOnLongClickListener(v ->{
            if(partsID[0]!=-1) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[0])+" ?");
                builder.setTitle("Removing CPU");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    cpuTitle.setText("Empty");
                    cpuImage.setImageResource(R.drawable.ic_blank_image);
                    cpuPrice.setText("$0.00");
                    checkedCPU.setChecked(false);
                    cpu.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    partsID[0] = -1;
                    totalNum.setText("TOTAL: $" + getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        mot.setOnClickListener(view -> {
            Intent mot_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from","Main")
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"));
            mot_intent.putExtra("name","Motherboards");
            mot_intent.putExtra("Build",name_action_bar);
            mot_intent.putExtra("ID",partsID);
            startActivity(mot_intent);
        });
        mot.setOnLongClickListener(v->{
            if(partsID[1]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[1])+" ?");
                builder.setTitle("Removing Motherboard");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    motTitle.setText("Empty");
                    motImage.setImageResource(R.drawable.ic_blank_image);
                    motPrice.setText("$0.00");
                    checkedMot.setChecked(false);
                    partsID[1] = -1;
                    mot.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $" + getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        mem.setOnClickListener(view -> {
            Intent mem_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("from","Main");
            mem_intent.putExtra("name","Memory");
            mem_intent.putExtra("Build",name_action_bar);
            mem_intent.putExtra("ID",partsID);
            startActivity(mem_intent);
        });
        mem.setOnLongClickListener(v->{
            if(partsID[2]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[2])+" ?");
                builder.setTitle("Removing Memory");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    memTitle.setText("Empty");
                    memImage.setImageResource(R.drawable.ic_blank_image);
                    memPrice.setText("$0.00");
                    checkedMem.setChecked(false);
                    partsID[2]=-1;
                    mem.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        vga.setOnClickListener(view -> {
            Intent vga_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from","Main")
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"));
            vga_intent.putExtra("name","Video Cards");
            vga_intent.putExtra("Build",name_action_bar);
            vga_intent.putExtra("ID",partsID);
            startActivity(vga_intent);
        });
        vga.setOnLongClickListener(v->{
            if(partsID[3]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[3])+" ?");
                builder.setTitle("Removing Graphics Caed");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    vgaTitle.setText("Empty");
                    vgaImage.setImageResource(R.drawable.ic_blank_image);
                    vgaPrice.setText("$0.00");
                    checkedVGA.setChecked(false);
                    partsID[3]=-1;
                    vga.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        psu.setOnClickListener(view -> {
            Intent psu_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("from","Main");
            psu_intent.putExtra("name","Power Supplies");
            psu_intent.putExtra("Build",name_action_bar);
            psu_intent.putExtra("ID",partsID);
            startActivity(psu_intent);
        });
        psu.setOnLongClickListener(v->{
            if(partsID[4]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[4])+" ?");
                builder.setTitle("Removing Power Suppl Unit");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    psuTitle.setText("Empty");
                    psuImage.setImageResource(R.drawable.ic_blank_image);
                    psuPrice.setText("$0.00");
                    checkedPSU.setChecked(false);
                    partsID[4]=-1;
                    psu.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        stor.setOnClickListener(view -> {
            Intent stor_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from","Main")
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"));
            stor_intent.putExtra("name","Storage");
            stor_intent.putExtra("Build",name_action_bar);
            stor_intent.putExtra("ID",partsID);
            startActivity(stor_intent);
        });
        stor.setOnLongClickListener(v->{
            if(partsID[5]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[5])+" ?");
                builder.setTitle("Removing Storage");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    storTitle.setText("Empty");
                    storImage.setImageResource(R.drawable.ic_blank_image);
                    storPrice.setText("$0.00");
                    checkedStor.setChecked(false);
                    partsID[5]=-1;
                    stor.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        cool.setOnClickListener(view -> {
            Intent cool_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"))
                    .putExtra("from","Main");
            cool_intent.putExtra("name","CPU Cooler");
            cool_intent.putExtra("Build",name_action_bar);
            cool_intent.putExtra("ID",partsID);
            startActivity(cool_intent);
        });
        cool.setOnLongClickListener(v->{
            if(partsID[6]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[6])+" ?");
                builder.setTitle("Removing CPU Cooler");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    coolTitle.setText("Empty");
                    coolImage.setImageResource(R.drawable.ic_blank_image);
                    coolPrice.setText("$0.00");
                    checkedCool.setChecked(false);
                    partsID[6]=-1;
                    cool.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        mon.setOnClickListener(view -> {
            Intent mon_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from","Main")
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"));
            mon_intent.putExtra("name","Monitor");
            mon_intent.putExtra("Build",name_action_bar);
            mon_intent.putExtra("ID",partsID);
            startActivity(mon_intent);
        });
        mon.setOnLongClickListener(v->{
            if(partsID[7]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[7])+" ?");
                builder.setTitle("Removing Monitor");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    monTitle.setText("Empty");
                    monImage.setImageResource(R.drawable.ic_blank_image);
                    monPrice.setText("$0.00");
                    checkedMon.setChecked(false);
                    partsID[7]=-1;
                    mon.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        pc_case.setOnClickListener(view -> {
            Intent pc_case_intent = new Intent(Build_Activity.this, PC_Build_Parts.class)
                    .putExtra("from","Main")
                    .putExtra("from",getIntent().getExtras().getInt("from"))
                    .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild"));
            pc_case_intent.putExtra("name","Cases");
            pc_case_intent.putExtra("Build",name_action_bar);
            pc_case_intent.putExtra("ID",partsID);
            startActivity(pc_case_intent);
        });
        pc_case.setOnLongClickListener(v->{
            if(partsID[8]!=-1){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Build_Activity.this);
                builder.setMessage("Delete " + PriceList.getName(partsID[8])+" ?");
                builder.setTitle("Removing Case");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    caseTitle.setText("Empty");
                    caseImage.setImageResource(R.drawable.ic_blank_image);
                    casePrice.setText("$0.00");
                    checkedCase.setChecked(false);
                    partsID[8]=-1;
                    pc_case.setCardBackgroundColor(getResources().getColor(R.color.cardview_dark));
                    totalNum.setText("TOTAL: $"+getPriceSum());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(this, "Nothing Happened.", Toast.LENGTH_SHORT).show();
                });
                builder.create().show();
            }else{
                Toast.makeText(this, "Cannot Delete anything.",Toast.LENGTH_SHORT).show();
            }
            return true;
        });
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
            memPrice.setText(PriceList.getPriceAsString(partsID[2]));
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
        totalNum.setText("TOTAL: $"+getPriceSum());
    }


    public String turnIntArrtoStr(){
        String[] arr = new String[partsID.length];
        for(int i =0; i< partsID.length;i++){
            arr[i] = String.valueOf(partsID[i]);
        }
        ids = TextUtils.join(",",arr);
        return ids;
    }

    public double getPriceSum(){
        double price=0.0;
        for(int i=0;i<partsID.length;i++) {
            if(partsID[i]!=-1) {
                price = price + PriceList.getPrice(partsID[i]);
            }
            System.out.println(price);
        }
        price = Math.floor(price*100)/100;
        return price;
    }
}
