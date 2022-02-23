package com.example.computerstarter.Build;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class MyBuildActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FloatingActionButton floatingActionButton;
    private MyBuildAdapter myBuildAdapter;
    private Build_Data build_data;
    private ArrayList<Build_Data> build;
    private DocumentReference build_ref;
    private RecyclerView recyclerView;
    private double[] parts=new double[9];
    private String[] titles = new String[9];
    private int[] images = new int[9];
    private boolean first = false;
    TextView home;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private String[] list;
    private String quiz;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView log;
    private ImageView logBut;
    private MenuItem item_log;
    private MenuItem item_quiz;
    private MenuItem item_acc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build = new ArrayList<>();
        setContentView(R.layout.my_build_layout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //navController = Navigation.findNavController(this,R.id.frame_layout);
        //NavigationUI.setupWithNavController(bottomNavigationView,navController);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        toggle.syncState();
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_quiz = menu.findItem(R.id.quiz);
        item_acc = menu.findItem(R.id.account);
        log = findViewById(R.id.log_Text);
        logBut = findViewById(R.id.logButtton);
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
            item_quiz.setVisible(true);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
            item_quiz.setVisible(false);
        }
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profile = headerView.findViewById(R.id.myimage);
        if(user!=null) {
            String current = user.getUid();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            //profile.setImageURI(user.getPhotoUrl()); //bugging out not sure why but only on the emulator
        }else{
            name.setText("Guest");
            email.setText("Guest Not Signed In");
        }
        log.setOnClickListener(view->{
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                log.setText("Log In");
                //login.logged = false;
                item_acc.setVisible(false);
                item_quiz.setVisible(false);
            }else{
                startActivity(new Intent(this, Login_SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                //log.setText("Log Out");
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        });
        //getSupportActionBar().setTitle("My Builds");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for(int i=0;i<parts.length;i++)
            parts[i]=0;
        addTitles();
        addImages();
        recyclerView = findViewById(R.id.recyclcer_builds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.fab_build);
        floatingActionButton.setOnClickListener(view -> {
            showAlertDialog();
        });
        if(mAuth.getCurrentUser()!=null) {
            build_ref = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
            build_ref.get().addOnSuccessListener(documentSnapshot -> {
                build_data = documentSnapshot.toObject(Build_Data.class);
                if(build_data.getBuild_name().size()>0||build_data.getBuildName()!=null) {
                    for(int i=0;i<build_data.getBuild_name().size();i++) {
                        build.add(new Build_Data(build_data.getBuild_name().get(i),build_data.getBuild_date().get(i),(double) build_data.getPrice().get(i)));
                    }
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    myBuildAdapter = new MyBuildAdapter(build, this);
                    recyclerView.setAdapter(myBuildAdapter);
                }else{
                    helper();
                }
            });
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            startActivity(new Intent(this,MainActivity.class));
            overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

     */
    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MyBuildActivity.this);
        alert.setTitle("Build Name");
        alert.setMessage("Enter Your Build Name");
        final EditText input = new EditText(MyBuildActivity.this);
        alert.setView(input);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();
                startActivity(new Intent(MyBuildActivity.this,Build_Activity.class)
                .putExtra("Build",value).putExtra("Time",build.size()).putExtra("Parts",parts).putExtra("Titles",titles).putExtra("Images",images));
            }
        });
        alert.create().show();
    }
    public void helper(){
        TapTargetView.showFor(this, TapTarget.forView(floatingActionButton,"Build your First PC")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(70)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(first)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(60), new TapTargetView.Listener(){
            @Override
            public void onTargetClick(TapTargetView view){
                super.onTargetClick(view);
                showAlertDialog();
            }
        });
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if(mAuth.getCurrentUser()!=null) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:
                            build_ref.update("build_name", FieldValue.arrayRemove(build.get(position).getBuildName()));
                            build_ref.update("build_date", FieldValue.arrayRemove(build.get(position).getBuildDate()));
                            build_ref.update("price", FieldValue.arrayRemove(build.get(position).getBuildPrice()));
                            build.remove(position);
                            myBuildAdapter.notifyDataSetChanged();
                            if(build.size()==0)
                                helper();
                            break;
                    }
            }
        }
    };
    public void addTitles(){
        titles[0]= "CPU";
        titles[1]= "Motherboard";
        titles[2] = "Memory";
        titles[3] = "Storage";
        titles[4] = "PSU";
        titles[5] = "CPU Cooler";
        titles[6] = "Monitor";
        titles[7] = "Video Card";
        titles[8] = "Cases";
    }
    public void addImages(){
        images[0] = R.drawable.cpu_link;
        images[1] = R.drawable.motherboard_link;
        images[2] = R.drawable.memory_link;
        images[3] = R.drawable.storage_link;
        images[4] = R.drawable.psu_link;
        images[5] = R.drawable.cpu_cooler_link;
        images[6] = R.drawable.monitor_link;
        images[7] = R.drawable.vga_link;
        images[8] = R.drawable.pc_case_link;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_quiz = menu.findItem(R.id.quiz);
        MenuItem item_acc = menu.findItem(R.id.account);
        //real_login login = new real_login();
        switch (item.getItemId()){
            case R.id.social:
                Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
                break;
            case R.id.home:
                Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MyBuildActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.account:
                if(mAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(this, AccountActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
                }else
                    Toast.makeText(this,"LOG IN!!!!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.quiz:
                //showAlertDialogQuiz();
                Toast.makeText(this,"Future Improvement",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return true;
    }
}
