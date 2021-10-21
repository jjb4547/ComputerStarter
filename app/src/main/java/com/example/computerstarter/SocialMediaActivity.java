package com.example.computerstarter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class SocialMediaActivity extends AppCompatActivity{

    private static final int CAMERA_REQUEST_CODE = 200;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String[] cameraPermission;
    String[] storagePermission;
    public String photoFileName = "photo.jpg";
    File photoFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forum_information);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Forum Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Button button = (Button) findViewById(R.id.ButtonCreate);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });
    }

    //RecyclerView recyclerView = findViewById(R.id.RecycleForum);

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
    

}
