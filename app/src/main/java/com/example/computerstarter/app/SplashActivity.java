package com.example.computerstarter.app;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.example.computerstarter.Build.PriceList;
import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity  {
    int counter =0;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference islandRef = storageRef.child("prices.txt");
    //File localFile = File.createTempFile("test", "txt");



    StorageReference gsReference = storage.getReferenceFromUrl("gs://computerstarter-csun22.appspot.com/prices.txt");
    //File localFile;
    final long ONE_MEGABYTE = 1024 * 1024;
    {
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            // Data for "images/island.jpg" is returns, use this as needed
            String s = new String(bytes, StandardCharsets.UTF_8);
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(s);
                System.out.println("JSON ARRAY: " + jsonObj.getJSONArray("items").getJSONObject(0).getString("url"));
                PriceList.jsonObj = jsonObj;
            } catch(JSONException e) {
                System.out.println(e);
            }
            System.out.println("FILE OUTPUT: " + s);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                System.out.println("FILE FAILURE");
            }
        });
    }

//    {
//        try {
//            File localFile = File.createTempFile("test", "txt", null);
//
//            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    System.out.println("FILE SUCCESS");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    System.out.println("FILE FAILURE");
//                }
//            });
//            /**
//            Scanner scnr = new Scanner(localFile);
//            System.out.println("USING SCANNER");
//            System.out.println("SCANNER: " + scnr.next());
//            scnr.close();
//            */
//            System.out.println("FILE OUTPUT: " + localFile.toString());
//            System.out.println("FILE OUTPUT: " + islandRef.listAll());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();
        MotionLayout gradient = findViewById(R.id.gradient);
        gradient.setBackground(getResources().getDrawable(R.drawable.gradient_animation_splash));
        AnimationDrawable animationDrawable = (AnimationDrawable) gradient.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        TextView textView = findViewById(R.id.percent);
        textView.setText("");
        final long period = 55;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(counter<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Loading...");
                        }
                    });
                    progressBar.setProgress(counter);
                    counter++;
                }else{
                    timer.cancel();
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                }
            }
        },0,period);

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
