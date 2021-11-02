package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MyBuildActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name="hello";
        MyBuildAdapter myBuildAdapter;
        setContentView(R.layout.my_build_layout);
        getSupportActionBar().setTitle("My Builds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclcer_builds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_build);
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
                        Map<String, Object> user_data = documentSnapshot.getData();
                        String name = user_data.get("Build 1").toString();
                        Boolean built = Boolean.parseBoolean(user_data.get("Built").toString());
                        if(built) {
                            Build_Data[] build_data = new Build_Data[]{
                                    new Build_Data(name, "10/12/2021", R.mipmap.ic_logo)
                            };
                            MyBuildAdapter myBuildAdapter = new MyBuildAdapter(build_data, MyBuildActivity.this);
                            recyclerView.setAdapter(myBuildAdapter);
                        }
                        else{
                            Build_Data[] build_data = new Build_Data[]{};
//                new Build_Data("Build 1","10/12/2021",R.mipmap.ic_logo),
//                new Build_Data("Build 2","10/13/2021",R.mipmap.ic_logo),
//                new Build_Data("Build 3","10/15/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 1","10/12/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 2","10/13/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 3","10/15/2021",R.mipmap.ic_logo)
//        };
                            MyBuildAdapter myBuildAdapter = new MyBuildAdapter(build_data,MyBuildActivity.this);
                            recyclerView.setAdapter(myBuildAdapter);
                        }
                        //email.setText(user_data.get("Email").toString());
                    } else {
                        //Toast.makeText(MainActivity.this, "Document Does not Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            //name.setText("");
            //email.setText("");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
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
    public MyBuildAdapter Builds(String name,boolean built){
        Build_Data[] build_data = new Build_Data[]{};
//                new Build_Data("Build 1","10/12/2021",R.mipmap.ic_logo),
//                new Build_Data("Build 2","10/13/2021",R.mipmap.ic_logo),
//                new Build_Data("Build 3","10/15/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 1","10/12/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 2","10/13/2021",R.mipmap.ic_logo),
//            new Build_Data("Build 3","10/15/2021",R.mipmap.ic_logo)
//        };
        MyBuildAdapter myBuildAdapter = new MyBuildAdapter(build_data,MyBuildActivity.this);
        return myBuildAdapter;
    }
}
