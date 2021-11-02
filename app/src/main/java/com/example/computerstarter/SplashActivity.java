package com.example.computerstarter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;


public class SplashActivity extends AppCompatActivity {
    int counter =0;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference islandRef = storageRef.child("test.txt");
    //File localFile = File.createTempFile("test", "txt");



    StorageReference gsReference = storage.getReferenceFromUrl("gs://computerstarter-csun22.appspot.com/test.txt");
    //File localFile;
    final long ONE_MEGABYTE = 1024 * 1024;
    {
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                String s = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("FILE OUTPUT: " + s);
            }
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
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        TextView textView = findViewById(R.id.percent);
        textView.setText("");
        final long period = 50;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(counter<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Loading");
                        }
                    });
                    progressBar.setProgress(counter);
                    counter++;
                }else{
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
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
