package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyBuildActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference documentReference = db.collection("Users").
    FloatingActionButton floatingActionButton;
    private MyBuildAdapter myBuildAdapter;
    int built;
    private Build_Data build_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String name="hello";
        setContentView(R.layout.my_build_layout);
        getSupportActionBar().setTitle("My Builds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclcer_builds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.fab_build);
        floatingActionButton.setOnClickListener(view -> {
            showAlertDialog();
        });
        if(mAuth.getCurrentUser()!=null) {
            String current =mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firestore.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                int builds = Integer.parseInt(documentSnapshot.get("numOfBuilds").toString());
                if(builds>0) {
                    build_data = documentSnapshot.toObject(Build_Data.class);
                    ArrayList<Build_Data> build = new ArrayList<>();
                    for(int i=0;i<build_data.getBuild_name().size();i++) {
                        build.add(new Build_Data(build_data.getBuild_name().get(i),build_data.getBuild_date().get(i),build_data.getPrice().get(0)));
                    }
                    myBuildAdapter = new MyBuildAdapter(build, this);
                    recyclerView.setAdapter(myBuildAdapter);
                }else{
                    helper();
                }
            });


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            startActivity(new Intent(this,MainActivity.class));
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
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
                Intent intent = new Intent(MyBuildActivity.this,Build_Activity.class);
                intent.putExtra("Build",value);
                startActivity(intent);
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
                .cancelable(true)
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
}
