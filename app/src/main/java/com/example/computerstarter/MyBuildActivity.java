package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyBuildActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference documentReference = db.collection("Users").
    FloatingActionButton floatingActionButton;
    MyBuildAdapter myBuildAdapter;
    int built;
    ArrayList<Build_Data> build_data= new ArrayList<>();

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
            DocumentReference documentReference = db.collection("Users").document(current);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        SwipeController swipeController = new SwipeController();
                        new ItemTouchHelper(swipeController).attachToRecyclerView(recyclerView);
                        Map<String, Object> user_data = documentSnapshot.getData();
                        built = Integer.parseInt(user_data.get("Built").toString());
                        MyBuildAdapter myBuildAdapter;
                        int counter = 0;
                        while (counter<built) {
                            String name = user_data.get("Build "+counter).toString();
                            if(name.compareTo("build_"+counter)!=0) {
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                String formattedDate = df.format(c);
                                build_data.add(new Build_Data(name, formattedDate, R.mipmap.ic_logo,0));
                                myBuildAdapter = new MyBuildAdapter(build_data, MyBuildActivity.this);
                                recyclerView.setAdapter(myBuildAdapter);
                                myBuildAdapter.notifyDataSetChanged();
                                counter++;
                            }
                        }if (built == 0) {
                            helper();
                        }
                    }
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
                .outerCircleColor(R.color.dodger_blue)
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
