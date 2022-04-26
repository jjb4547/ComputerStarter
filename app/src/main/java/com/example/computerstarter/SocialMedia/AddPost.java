package com.example.computerstarter.SocialMedia;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


public class AddPost extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseUser user;
    Uri uri;
    ProgressDialog dialog;
    EditText postDescription;
    AppCompatButton postButton;
    ImageButton imagePostButton,tagButton, backButton;
    ImageView imagePost;
    String tag="";
    String postImage="";
    String description="";
    boolean isImage=true;
    private static final int CAMERA_PERMISSION_CODE = 112;
    private static final int STORAGE_PERMISSION_CODE = 113;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addblogs);
        dialog = new ProgressDialog(AddPost.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please Wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPost.this, MainBuilds.class)
                        .putExtra("from","Social"));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        postDescription = findViewById(R.id.textPost);
        tagButton = findViewById(R.id.tagPost);
        postButton = findViewById(R.id.postButton);
        postButton.setVisibility(View.GONE);
        imagePostButton = findViewById(R.id.imagePostButton);
        imagePost = findViewById(R.id.postImage);
        postDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                description = postDescription.getText().toString();
                if(!description.isEmpty()&&!postImage.equals("")){
                    postButton.setVisibility(View.VISIBLE);
                    postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_light));
                    postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                    postButton.setEnabled(true);
                }else{
                    postButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imagePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicDialog();
            }
        });
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu tagMenu = new PopupMenu(getApplicationContext(), tagButton);
                tagMenu.getMenuInflater().inflate(R.menu.filter_menu, tagMenu.getMenu());
                tagMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        tag = item.getTitle().toString();
                        Toast.makeText(getApplicationContext(), "Selected Tag: "+tag, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                tagMenu.show();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                //System.out.println("PHOTO1: "+user.getPhotoUrl().toString());
                final StorageReference reference = storage.getReference().child("Posts")
                        .child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime()+"");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Post post = new Post();
                                post.setPostImage(uri.toString());
                                post.setPostedBy(user.getDisplayName());
                                post.setPostDescription(postDescription.getText().toString());
                                post.setPostedAt(new Date().getTime());
                                post.setPostUserId(user.getUid());
                                post.setProfile(user.getPhotoUrl().toString());
                                post.setTag(tag);
                                post.setDefaultImage(isImage);
                                database.getReference().child("Posts").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(AddPost.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddPost.this, MainBuilds.class)
                                                .putExtra("from", "Social"));
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void showImagePicDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
        //builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // check for the camera and storage permission if
                // not given the request for permission
                if (which == 0) {
                    checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
                } else if (which == 1) {
                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
                }
            }
        });
        builder.create().show();
    }

    private void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(AddPost.this,permission)== PackageManager.PERMISSION_DENIED){
            //Take Permission
            ActivityCompat.requestPermissions(AddPost.this,new String[]{permission},requestCode);
        }else{
            Toast.makeText(this,"Permission already Granted",Toast.LENGTH_SHORT).show();
            if(permission.compareTo(Manifest.permission.CAMERA)==0){
                pickFromCamera();
            }else if(permission.compareTo(Manifest.permission.READ_EXTERNAL_STORAGE)==0){
                pickFromGallery();
            }
        }
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        galleryActivityResultLauncher.launch(galleryIntent);
    }
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        imagePost.setImageURI(uri);
                        InputStream inputStream = null;
                        try{
                            inputStream = getContentResolver().openInputStream(uri);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 25, stream);
                        byte[] bytes = stream.toByteArray();
                        try{
                            stream.close();
                            stream = null;
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bmp, "Title",null);
                        uri = Uri.parse(path);
                        isImage=false;
                        if(!description.isEmpty()){
                            postButton.setVisibility(View.VISIBLE);
                        }
                        postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_light));
                        postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                        postButton.setEnabled(true);
                    }
                }
            }
    );

    private void pickFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraActivityResultLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bitmap imageBit = (Bitmap) data.getExtras().get("data");
                        imagePost.setImageBitmap(imageBit);
                        isImage =false;
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        imageBit.compress(Bitmap.CompressFormat.JPEG, 25, bytes);
                        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), imageBit, "Title",null);
                        uri = Uri.parse(path);
                        if(!description.isEmpty()){
                            postButton.setVisibility(View.VISIBLE);
                        }
                        postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_light));
                        postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                        postButton.setEnabled(true);
                    }
                }
            }
    );
}