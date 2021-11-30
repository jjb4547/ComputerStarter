package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class MyBuildActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FloatingActionButton floatingActionButton;
    private MyBuildAdapter myBuildAdapter;
    private Build_Data build_data;
    private ArrayList<Build_Data> build;
    private DocumentReference build_ref = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build = new ArrayList<>();
        setContentView(R.layout.my_build_layout);
        getSupportActionBar().setTitle("My Builds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclcer_builds);
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
                build_data = documentSnapshot.toObject(Build_Data.class);
                if(build_data.getBuild_name().size()>0||build_data.getBuildName()!=null) {
                    for(int i=0;i<build_data.getBuild_name().size();i++) {
                        build.add(new Build_Data(build_data.getBuild_name().get(i),build_data.getBuild_date().get(i),build_data.getPrice().get(0)));
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
                intent.putExtra("Time",build.size());
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
                .cancelable(false)
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
                            build.remove(position);
                            myBuildAdapter.notifyDataSetChanged();
                            if(build.size()==0){
                                helper();
                            }
                            break;
                    }
            }
        }
    };
}
