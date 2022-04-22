package com.example.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CommentActivity  extends AppCompatActivity {
    Intent intent;
    String postId,postedBy;
    FirebaseAuth auth;
    FirebaseDatabase database;

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
    }
}
