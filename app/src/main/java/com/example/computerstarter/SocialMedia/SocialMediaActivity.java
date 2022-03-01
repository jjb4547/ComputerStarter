package com.example.computerstarter.SocialMedia;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.R;

import java.io.File;
import java.util.ArrayList;

public class SocialMediaActivity extends AppCompatActivity{

    private static final int CAMERA_REQUEST_CODE = 200;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String[] cameraPermission;
    String[] storagePermission;
    ArrayList<PostInformation> PI;
    public String photoFileName = "photo.jpg";
    File photoFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forum_information);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Forum Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editText = findViewById(R.id.TextInfForum);
        String stringText = editText.getText().toString().trim();

        Button button = findViewById(R.id.PostInfForum);
        button.setOnClickListener(v -> {

        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

}
