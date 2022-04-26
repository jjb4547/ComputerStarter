package com.example.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity  extends AppCompatActivity {
    Intent intent;
    String postId,postedBy;
    FirebaseAuth auth;
    FirebaseDatabase database;
    int postLike;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<Comment> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        intent = getIntent();
        ImageView postImage = findViewById(R.id.imageComment);
        TextView like = findViewById(R.id.like);
        TextView name = findViewById(R.id.profileCommentName);
        TextView comment = findViewById(R.id.comment);
        TextView postDescription = findViewById(R.id.commentDescription);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageView profile = findViewById(R.id.profileImageComment);
        ImageButton sendButton = findViewById(R.id.send);
        EditText commentBody = findViewById(R.id.commentText);
        RecyclerView recyclerView = findViewById(R.id.commentRecycler);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommentActivity.this, MainBuilds.class).putExtra("from","Social"));
            }
        });
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");
        postLike = intent.getExtras().getInt("postLike");
        name.setText(postedBy);
        database.getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                if(!post.getDefaultImage())
                    Picasso.get().load(post.getPostImage()).into(postImage);
                else
                    postImage.setVisibility(View.GONE);
                postDescription.setText(post.getPostDescription());
                like.setText(post.getPostLike()+"");
                comment.setText(post.getPostComment()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        StorageReference profileRef = storageReference.child("ProfileImage/Users/"+FirebaseAuth.getInstance().getUid()+"/profile");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profile));
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comments = new Comment();
                comments.setCommentBody(commentBody.getText().toString());
                comments.setCommentedAt(new Date().getTime());
                comments.setCommentedBy(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                database.getReference().child("Posts").child(postId).child("Comments").push().setValue(comments).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Posts").child(postId).child("postComment").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int postComment =0;
                                if(snapshot.exists()){
                                    postComment = snapshot.getValue(Integer.class);
                                }
                                database.getReference().child("Posts").child(postId).child("postComment").setValue(postComment+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        commentBody.setText("");
                                        Toast.makeText(CommentActivity.this, "Commented", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("Likes").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color,0,0,0);
                        }else{
                            like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_border,0,0,0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("Likes")
                        .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            FirebaseDatabase.getInstance().getReference().child("Posts")
                                    .child(postId).child("Likes").child(FirebaseAuth.getInstance().getUid()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Posts").child(postId)
                                    .child("postLike").setValue(postLike-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_border,0,0,0);
                                }
                            });
                        }else{
                            FirebaseDatabase.getInstance().getReference().child("Posts")
                                    .child(postId).child("Likes").child(FirebaseAuth.getInstance().getUid())
                                    .setValue("Liked").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Posts").child(postId)
                                            .child("postLike").setValue(postLike+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color,0,0,0);
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentAdapter);
        database.getReference().child("Posts").child(postId).child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    list.add(comment);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
