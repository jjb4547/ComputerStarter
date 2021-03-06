package com.seniordesign.computerstarter.Build;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seniordesign.computerstarter.Education.PC_Part_Activity;
import com.seniordesign.computerstarter.Guides.Guides_Activity;
import com.seniordesign.computerstarter.Login.Login_SignUpActivity;
import com.seniordesign.computerstarter.Others.AccountActivity;
import com.example.computerstarter.R;
import com.seniordesign.computerstarter.SampleProjects.SampleProjects;
import com.seniordesign.computerstarter.app.HomeActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MyBuildActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyBuildAdapter.onCardListener {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FloatingActionButton floatingActionButton;
    private MyBuildAdapter myBuildAdapter;
    private MyBuildAdapter.ViewHolder viewHolder;
    private Build_Data build_data;
    private ArrayList<Build_Data> build;
    private DocumentReference build_ref;
    private RecyclerView recyclerView;
    private double[] parts=new double[9];
    private String[] titles = new String[9];
    private int[] images = new int[9];
    private int[] partsID = new int[9];
    private int[] numParts = new int[9];
    private boolean first = false;
    private ImageButton backHome;
    TextView home;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView log;
    private MenuItem item_log;
    private MenuItem item_acc;
    private MaterialButton loginBut;
    private TextView loginText;
    private TextView cpuName, motName,memName,storName, psuName,coolName,monName,vgaName,caseName;
    private LinearLayout cardLayout;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build = new ArrayList<>();
        setContentView(R.layout.my_build_layout);
        FirebaseUser user = mAuth.getCurrentUser();
        loginBut = findViewById(R.id.loginShortcut);
        loginText = findViewById(R.id.announcement);
        cpuName = findViewById(R.id.cpuName);
        motName = findViewById(R.id.motName);
        storName = findViewById(R.id.storName);
        psuName = findViewById(R.id.psuName);
        coolName = findViewById(R.id.coolName);
        monName = findViewById(R.id.monName);
        vgaName = findViewById(R.id.vgaName);
        caseName = findViewById(R.id.caseName);
        cardLayout = findViewById(R.id.cardLayout);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        toggle.syncState();
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        item_acc = menu.findItem(R.id.account);
        log = findViewById(R.id.log_Text);
        if(user!=null) {
            log.setText("Log Out");
            item_acc.setVisible(true);
            loginText.setVisibility(View.GONE);
            loginBut.setVisibility(View.GONE);
        }else {
            log.setText("Log In");
            item_acc.setVisible(false);
            loginBut.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.VISIBLE);
        }
        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.RIGHT));
        TextView name = headerView.findViewById(R.id.myname);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profile = headerView.findViewById(R.id.myimage);
        if(user!=null) {
            String current = user.getUid();
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
            StorageReference profileRef = storageReference.child("ProfileImage/Users/"+mAuth.getCurrentUser().getUid()+"/profile");
            profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profile));
            //profile.setImageURI(user.getPhotoUrl()); //bugging out not sure why but only on the emulator
        }else{
            name.setText("Guest");
            email.setText("Guest Not Signed In");
        }
        log.setOnClickListener(view->{
            if(mAuth.getCurrentUser()!=null){
                mAuth.signOut();
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainBuilds.class));
                log.setText("Log In");
                //login.logged = false;
                item_acc.setVisible(false);
                startActivity(new Intent(this, Login_SignUpActivity.class));
            }else{
                startActivity(new Intent(this, Login_SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                //log.setText("Log Out");
            }
        });
        backHome = findViewById(R.id.backHome);
        backHome.setOnClickListener(view->{
            startActivity(new Intent(getApplicationContext(), MainBuilds.class).putExtra("from","Builds"));
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        });
        loginBut.setOnClickListener(v->{
            startActivity(new Intent(MyBuildActivity.this,Login_SignUpActivity.class).putExtra("from","Main"));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        });
        //getSupportActionBar().setTitle("My Builds");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for(int i=0;i<parts.length;i++)
            parts[i]=0;
        for(int i=0;i<numParts.length;i++)
            numParts[i]=0;
        recyclerView = findViewById(R.id.recyclcer_builds);
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
                        build.add(new Build_Data(build_data.getBuild_name().get(i),build_data.getBuild_date().get(i), build_data.getPrice().get(i), build_data.getParts_id().get(i),build_data.getNum_parts().get(i)));
                    }
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                    myBuildAdapter = new MyBuildAdapter(build, this, this);
                    recyclerView.setAdapter(myBuildAdapter);
                }else{
                    helper();
                }
            });

        }
    }
    public void showAlertDialog(){
        addIDs();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MyBuildActivity.this);
        builder.setTitle("Insert Build Name");
        final TextInputEditText input = new TextInputEditText(MyBuildActivity.this);
        input.setHint("MAX 8 Characters");
        input.setMaxLines(1);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        input.setImeActionLabel("Submit",KeyEvent.KEYCODE_ENTER);
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER)) {
                    String value = input.getText().toString();
                    startActivity(new Intent(MyBuildActivity.this,Build_Activity.class)
                            .putExtra("Build",value).putExtra("ID",partsID).putExtra("Edit","no").putExtra("Num",numParts));
                }
                return true;
            }
        });
        input.setMaxLines(1);
        input.setMaxWidth(10);
        //final EditText input = new EditText(MyBuildActivity.this);
        builder.setView(input);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();
                startActivity(new Intent(MyBuildActivity.this,Build_Activity.class)
                .putExtra("Build",value).putExtra("ID",partsID).putExtra("Edit","no").putExtra("Num",numParts));
            }
        });
        builder.create().show();
    }
    public void helper(){
        TapTargetView.showFor(this, TapTarget.forView(floatingActionButton,"Build your First PC")
                .outerCircleColor(R.color.cardview_dark)
                .outerCircleAlpha(0.96f)
                .titleTextSize(70)
                .titleTextColor(R.color.white)
                .drawShadow(true)
                .cancelable(!Boolean.parseBoolean(getResources().getString(R.string.pref_previously_started)))
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
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String price = build.get(viewHolder.getBindingAdapterPosition()).getBuildPrice();
            String name = build.get(viewHolder.getBindingAdapterPosition()).getBuildName();
            String build_date = build.get(viewHolder.getBindingAdapterPosition()).getBuildDate();
            String partID = build.get(viewHolder.getBindingAdapterPosition()).getPartsId();
            String numPart = build.get(viewHolder.getBindingAdapterPosition()).getNumParts();
            if(mAuth.getCurrentUser()!=null) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:
                            deleteCard(viewHolder.getBindingAdapterPosition());
                            Snackbar snackbar = Snackbar.make(recyclerView,"Deleting "+name.substring(10),Snackbar.LENGTH_LONG)
                                    .setAction("Undo", view ->{
                                        if(checkAuth()){
                                            build_ref.get().addOnSuccessListener(documentSnapshot -> {
                                                if (documentSnapshot.exists()) {
                                                    build_data = documentSnapshot.toObject(Build_Data.class);
                                                    if (build_data.getBuild_name().size() < 5) {
                                                        long unixTime = System.currentTimeMillis() / 1000;
                                                        //price = String.valueOf(unixTime) + String.valueOf(getPriceSum());
                                                        build_ref.update("build_name", FieldValue.arrayUnion(name));
                                                        build_ref.update("build_date", FieldValue.arrayUnion(build_date));
                                                        build_ref.update("price", FieldValue.arrayUnion(price));
                                                        build_ref.update("parts_id", FieldValue.arrayUnion(partID));
                                                        build_ref.update("num_parts",FieldValue.arrayUnion(numPart));
                                                    }
                                                }
                                            });
                                            build.add(new Build_Data(name,build_date,price,partID,numPart));
                                            myBuildAdapter.notifyDataSetChanged();
                                        }
                                    } );
                            snackbar.show();
                            break;
                        case ItemTouchHelper.RIGHT:
                            editCard(viewHolder.getBindingAdapterPosition());
                            break;
                    }
            }
        }


        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MyBuildActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Delete")
                    .addSwipeLeftCornerRadius(TypedValue.COMPLEX_UNIT_DIP,15)
                    .addSwipeLeftPadding(TypedValue.COMPLEX_UNIT_DIP,8,8,8)
                    .addSwipeRightActionIcon(R.drawable.change_image)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MyBuildActivity.this, R.color.green))
                    .addSwipeRightLabel("Edit")
                    .addSwipeRightCornerRadius(TypedValue.COMPLEX_UNIT_DIP,15)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    public boolean checkAuth(){
        if(mAuth.getCurrentUser()!=null)
            return true;
        else
            return false;
    }

    private void deleteCard(int position) {
        build_ref.update("build_name", FieldValue.arrayRemove(build.get(position).getBuildName()));
        build_ref.update("build_date", FieldValue.arrayRemove(build.get(position).getBuildDate()));
        build_ref.update("price", FieldValue.arrayRemove(build.get(position).getBuildPrice()));
        build_ref.update("parts_id",FieldValue.arrayRemove(build.get(position).getPartsId()));
        build_ref.update("num_parts",FieldValue.arrayRemove(build.get(position).getNumParts()));
        build.remove(position);
        myBuildAdapter.notifyDataSetChanged();
    }

    public void addIDs(){
        for(int i =0;i<9;i++){
            partsID[i]=-1;
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        Menu menu = navigationView.getMenu();
        MenuItem item_acc = menu.findItem(R.id.account);
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this,"Main Page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.building:
                //Toast.makeText(this,"My Builds",Toast.LENGTH_SHORT).show();
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
            case R.id.partsMenu:
                startActivity(new Intent(MyBuildActivity.this, PC_Part_Activity.class).putExtra("Act","Edu"));
                break;
            case R.id.guidesMenu:
                startActivity(new Intent(MyBuildActivity.this, Guides_Activity.class));
                break;
            case R.id.projectsMenu:
                startActivity(new Intent(MyBuildActivity.this, SampleProjects.class));
                break;
            case R.id.community:
                startActivity(new Intent(MyBuildActivity.this,MainBuilds.class)
                        .putExtra("from","Social"));
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

    @Override
    public void onCardClick(int position) {
        editCard(position);
    }

    @Override
    public void onLongClick(int position) {

    }

    private void editCard(int position) {
        cleanUpIDs(position);
        cleanUpPartNums(position);
        //System.out.println(build.get(position).getNumPart());
        String[] editBuild = new String[]{ build.get(position).getBuildName(),
                build.get(position).getBuildDate(),build.get(position).getBuildPrice(),
                build.get(position).getPartsId(),build.get(position).getNumParts()};
        startActivity(new Intent(this,Build_Activity.class)
                .putExtra("Edit","yes")
                .putExtra("Build",build.get(position).getBuildName().substring(10))
                .putExtra("editBuild",editBuild)
                .putExtra("ID",build.get(position).getCleanID())
                .putExtra("Num",build.get(position).getNumPart()));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        deleteCard(position);
    }

    public void cleanUpIDs(int position) {
        String[] strArr = null;
        int[] partsID = new int[9];
        strArr = build.get(position).getPartsId().substring(10).split(",");
        for(int i=0;i<strArr.length;i++){
            partsID[i] = Integer.parseInt(strArr[i]);
        }
        build.get(position).setCleanID(partsID);
    }
    public void cleanUpPartNums(int position) {
        String[] strArr = null;
        int[] numParts = new int[9];
        strArr = build.get(position).getNumParts().substring(10).split(",");
        for(int i=0;i<strArr.length;i++){
            numParts[i] = Integer.parseInt(strArr[i]);
            //System.out.println("i"+i+" "+numParts[i]);
        }
        build.get(position).setNumPart(numParts);
    }
}
