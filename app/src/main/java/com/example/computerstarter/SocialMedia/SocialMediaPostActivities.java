package com.example.computerstarter.SocialMedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.computerstarter.R;
import com.example.computerstarter.app.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SocialMediaPostActivities extends AppCompatActivity {


    String hisuid, ptime, myuid, myname, myemail, mydp, uimage, postId, plike, hisdp, hisname;
    ImageView picture, image;
    TextView name, time, title, description, like, tcomment;
    ImageButton more;
    Button likebtn, share;
    LinearLayout profile;
    EditText comment;
    ImageButton sendb;
    RecyclerView recyclerView;
    List<SocialMediaModelComment> commentList;
    SocialMediaAdapterComment adapterComment;
    ImageView imagep;
    boolean mlike = false;
    ActionBar actionBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Post Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        postId = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.recyclecomment);
        picture = findViewById(R.id.pictureco);
        image = findViewById(R.id.pimagetvco);
        name = findViewById(R.id.unameco);
        time = findViewById(R.id.utimeco);
        more = findViewById(R.id.morebtn);
        imagep = findViewById(R.id.commentimge);
        myemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("ProfileImage/Users/"+myuid+"/profile");
        storageReference1.getDownloadUrl().addOnSuccessListener(uri -> {
            mydp = uri.toString();
            Picasso.get().load(uri).into(imagep);
        });
        description = findViewById(R.id.descriptco);
        tcomment = findViewById(R.id.pcommenttv);
        like = findViewById(R.id.plikebco);
        likebtn = findViewById(R.id.like);
        comment = findViewById(R.id.typecommet);
        sendb = findViewById(R.id.sendcomment);
        share = findViewById(R.id.share);
        profile = findViewById(R.id.profilelayout);
        progressDialog = new ProgressDialog(this);
        loadPostInfo();
        loadComments();
        sendb.setOnClickListener(v -> postComment());
        likebtn.setOnClickListener(v -> likepost());
    }


    private void loadComments() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        commentList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Comments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SocialMediaModelComment modelComment = dataSnapshot1.getValue(SocialMediaModelComment.class);
                    commentList.add(modelComment);
                    adapterComment = new SocialMediaAdapterComment(getApplicationContext(), commentList, myuid, postId);
                    recyclerView.setAdapter(adapterComment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void likepost() {
        mlike = true;
        final DatabaseReference postref = FirebaseDatabase.getInstance().getReference().child("Posts");
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (mlike) {
                    if (dataSnapshot.child(postId).hasChild("isLiked")) {
                        postref.child(postId).child("plike").setValue("" + (Integer.parseInt(plike) - 1));
                        postref.child(postId).child("isLiked").removeValue();
                        mlike = false;
                    } else {
                        postref.child(postId).child("plike").setValue("" + (Integer.parseInt(plike) + 1));
                        postref.child(postId).child("isLiked").setValue("Liked");
                        mlike = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void postComment() {
        progressDialog.setMessage("Adding Comment");
        final String commentss = comment.getText().toString().trim();
        if (TextUtils.isEmpty(commentss)) {
            Toast.makeText(SocialMediaPostActivities.this, "Empty comment", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference("Posts").child(postId+"/pcomments");
        commentRef.setValue(String.valueOf(getIntent().getExtras().getInt("comments")+1));
        DatabaseReference datarf = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Comments");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cId", timestamp);
        hashMap.put("comment", commentss);
        hashMap.put("ptime", timestamp);
        hashMap.put("uid", myuid);
        hashMap.put("uemail", myemail);
        hashMap.put("udp", mydp);
        hashMap.put("uname", myname);
        datarf.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(SocialMediaPostActivities.this, "Added", Toast.LENGTH_LONG).show();
                comment.setText("");
                //updatecommetcount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SocialMediaPostActivities.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
//
//    boolean count = false;
//
//    private void updatecommetcount() {
//        count = true;
//        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postId);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (count) {
//                    String comments = "" + dataSnapshot.child("pcomments").getValue();
//                    int newcomment = Integer.parseInt(comments) + 1;
//                    reference.child("pcomments").setValue("" + newcomment);
//                    count = false;
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
    private void loadPostInfo() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = databaseReference.orderByChild("ptime").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //String ptitle = dataSnapshot1.child("title").getValue().toString();
                    String descriptions = dataSnapshot1.child("description").getValue().toString();
                    uimage = dataSnapshot1.child("uimage").getValue().toString();
                    hisdp = dataSnapshot1.child("udp").getValue().toString();
                    // hisuid = dataSnapshot1.child("uid").getValue().toString();
                    String uemail = dataSnapshot1.child("uemail").getValue().toString();
                    hisname = dataSnapshot1.child("uname").getValue().toString();
                    ptime = dataSnapshot1.child("ptime").getValue().toString();
                    plike = dataSnapshot1.child("plike").getValue().toString();
                    String commentcount = dataSnapshot1.child("pcomments").getValue().toString();
                    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                    calendar.setTimeInMillis(Long.parseLong(ptime));
                    String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
                    name.setText(hisname);
                    //title.setText(ptitle);
                    description.setText(descriptions);
                    like.setText(plike + " Likes");
                    time.setText(timedate);
                    tcomment.setText(commentcount + " Comments");
                    if (dataSnapshot1.child("isDefault").getValue().toString().equals("true")) {
                        image.setVisibility(View.GONE);
                    } else {
                        image.setVisibility(View.VISIBLE);
                        try {
                            Glide.with(SocialMediaPostActivities.this).load(uimage).into(image);
                        } catch (Exception e) {

                        }
                    }
                        try {
                            Glide.with(SocialMediaPostActivities.this).load(hisdp).into(picture);
                        } catch (Exception e) {
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }

}
