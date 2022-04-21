package com.example.computerstarter.SocialMedia;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
    ImageButton imagePostButton,tagButton;
    ImageView imagePost;
    String tag="";
    boolean isDefault=true;
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
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        postDescription = findViewById(R.id.textPost);
        tagButton = findViewById(R.id.tagPost);
        postButton = findViewById(R.id.postButton);
        imagePostButton = findViewById(R.id.imagePostButton);
        imagePost = findViewById(R.id.postImage);
        postDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String description = postDescription.getText().toString();
                if(!description.isEmpty()){
                    postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_light));
                    postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                    postButton.setEnabled(true);
                }else{
                    postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_no_color));
                    postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                    postButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imagePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
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
                final StorageReference reference = storage.getReference().child("Posts")
                        .child(FirebaseAuth.getInstance().getUid()).child(new Date().getTime()+"");
                Bitmap bitmap = ((BitmapDrawable)imagePost.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] data = byteArrayOutputStream.toByteArray();
                reference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                                post.setTag(tag);
                                post.setDefault(isDefault);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            uri = data.getData();
            isDefault = false;
            imagePost.setImageURI(uri);
            postButton.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.rounded_corners_light));
            postButton.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
            postButton.setEnabled(true);
        }
    }
}