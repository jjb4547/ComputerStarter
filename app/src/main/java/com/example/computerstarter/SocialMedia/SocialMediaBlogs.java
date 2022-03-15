package com.example.computerstarter.SocialMedia;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SocialMediaBlogs extends AppCompatActivity {
    public SocialMediaBlogs() {
        // Required empty public constructor
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    EditText title, des;
    String email2;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int CAMERA_PERMISSION_CODE =112;
    private static final int STORAGE_PERMISSION_CODE =113;
    private boolean cameraPermission = false;
    private boolean storagePermission = false;
    ProgressDialog pd;
    ImageView image;
    String edititle, editdes, editimage;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    byte[] data;

    Uri imageuri = null;
    TextView name, email, uid;
    String dp;
    DatabaseReference databaseReference;
    Button upload;
    Button blogPicture;
    FirebaseUser user;
    ImageView profile;
    Bitmap bitmap;
    DatabaseReference databaseTags;
    Spinner spinner;
    Boolean isDefault;
    private String tagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        //View view = inflater.inflate(R.layout.fragment_add_blogs, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        tagName="";
        isDefault=false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.fragment_add_blogs);
        //title = findViewById(R.id.ptitle); //change to spinner later
        des = findViewById(R.id.pdes);
        image = findViewById(R.id.imagep);
        upload = findViewById(R.id.pupload);
        blogPicture = findViewById(R.id.blogPicture);
        name = findViewById(R.id.unametv);
        profile = findViewById(R.id.picturetv);
        spinner = findViewById(R.id.pspin);
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        Intent intent = this.getIntent();
        // Retrieving the user data like name ,email and profile pic using query
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReference.orderByChild("email").equalTo(email2);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //name = dataSnapshot1.child("name").getValue().toString();
                    //email = "" + dataSnapshot1.child("email").getValue();
                    dp = "" + dataSnapshot1.child("image").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //used for spinner

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Computer");
        arrayList.add("GPU");
        arrayList.add("CPU");
        arrayList.add("Motherboard");
        arrayList.add("Power");
        arrayList.add("Ram");
        arrayList.add("Memory");
        arrayList.add("RGB");
        arrayList.add("Tower");
        arrayList.add("Help!");
        arrayList.add("Monitor");
        arrayList.add("Mouse");
        arrayList.add("Keyboard");
        arrayList.add("Setup");
        arrayList.add("Stream");
        arrayList.add("Coding");
        arrayList.add("Design");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tagName, Toast.LENGTH_LONG).show();
                //databaseTags = FirebaseDatabase.getInstance().getReference("tagName"); //send data to same place as forum post
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        // Initialising camera and storage permission
        // After click on button we will be selecting an image
        blogPicture.setOnClickListener(v -> showImagePicDialog());
        // Now we will upload out blog
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vvvvv dont forget to change it back!!!
                //String titl = "" + title.getText().toString().trim();
                String titl = " ";
                String description = "" + des.getText().toString().trim();

                //Convert to spinner later
                // If empty set error
//                if (TextUtils.isEmpty(titl)) {
//                    title.setError("Title Cant be empty");
//                    Toast.makeText(SocialMediaBlogs.this, "Title can't be left empty", Toast.LENGTH_LONG).show();
//                    return;
//                }

                // If empty set error
                if (TextUtils.isEmpty(description)) {
                    des.setError("Description Cant be empty");
                    Toast.makeText(SocialMediaBlogs.this, "Description can't be left empty", Toast.LENGTH_LONG).show();
                    return;
                }
                 //If empty show error
                /*if (imageuri == null) {
                    Toast.makeText(SocialMediaBlogs.this, "Select an Image", Toast.LENGTH_LONG).show();
                } else {
                    uploadData(titl, description);
                }*/


                uploadData(titl, description);
            }
        });

    }

    private void showImagePicDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SocialMediaBlogs.this);
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

    // if access is given then pick image from camera and then put
    // the imageuri in intent extra and pass to startactivityforresult
    private void pickFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    // if access is given then pick image from gallery
    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }

    // Upload the value of blog data into firebase
    private void uploadData(final String titl, final String description) {
        // show the progress dialog box
        pd.setMessage("Publishing Post");
        pd.show();
        final String timestamp = String.valueOf(System.currentTimeMillis());
        String filepathname = "Posts/" + "post" + timestamp;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        if("default".equals(image.getTag())){
            isDefault=!isDefault;
        }
        // initialising the storage reference for updating the data
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child(filepathname);
        storageReference1.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            // getting the url of image uploaded
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful()) ;
            String downloadUri = uriTask.getResult().toString();
            if (uriTask.isSuccessful()) {
                // if task is successful the update the data into firebase
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("uid", user.getUid());
                hashMap.put("uname", user.getDisplayName());
                hashMap.put("uemail", user.getEmail());
                //hashMap.put("profileimage",user.getPhotoUrl());
                hashMap.put("udp", dp);
                hashMap.put("tag", tagName);
                hashMap.put("description", description);
                hashMap.put("uimage", downloadUri);
                hashMap.put("ptime", timestamp);
                hashMap.put("plike", "0");
                hashMap.put("pcomments", "0");
                hashMap.put("isDefault",isDefault);
                // set the data into firebase and then empty the title ,description and image data
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
                databaseReference.child(timestamp).setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pd.dismiss();
                                Toast.makeText(SocialMediaBlogs.this, "Published", Toast.LENGTH_LONG).show();
                                //title.setText("");
                                tagName = "";
                                image.setTag("default");
                                des.setText("");
                                //name.setText("");
                                image.setImageURI(null);
                                imageuri = null;
                                //startActivity(new Intent(SocialMediaBlogs.this, SocialMediaFragment.class));
                                //Toast.makeText(getApplicationContext(),"SETTING NULL", Toast.LENGTH_SHORT).show();
                                SocialMediaBlogs.this.finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(SocialMediaBlogs.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(SocialMediaBlogs.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Here we are getting data from image
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RequestCode "+requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                Toast.makeText(this, "Gallery Upload Done",Toast.LENGTH_SHORT).show();
                imageuri = data.getData();
                image.setTag("not default");
                image.setImageURI(imageuri);
            }else if(requestCode ==1){
                Toast.makeText(this, "Camera Upload Done",Toast.LENGTH_SHORT).show();
                Bitmap imageBit = (Bitmap) data.getExtras().get("data");
                image.setTag("not default");
                image.setImageBitmap(imageBit);
            }
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

    public void checkPermission(String permission, int requestCode){
        //Checking if permission granted or not
        if(ContextCompat.checkSelfPermission(SocialMediaBlogs.this,permission)==PackageManager.PERMISSION_DENIED){
            //Take Permission
            ActivityCompat.requestPermissions(SocialMediaBlogs.this,new String[]{permission},requestCode);
        }else{
            Toast.makeText(this,"Permission already Granted",Toast.LENGTH_SHORT).show();
            if(permission.compareTo(Manifest.permission.CAMERA)==0){
                pickFromCamera();
            }else if(permission.compareTo(Manifest.permission.READ_EXTERNAL_STORAGE)==0){
                pickFromGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                cameraPermission =true;
            }else{
                Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==STORAGE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                storagePermission=true;
            }else{
                Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }
}