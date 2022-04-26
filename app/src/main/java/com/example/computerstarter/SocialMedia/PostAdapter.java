package com.example.computerstarter.SocialMedia;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.computerstarter.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {
    ArrayList<Post> list;
    Context context;
    StorageReference profileRef;
    FirebaseUser user;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Dialog dialog;
    ImageView imageView;

    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent,false);
        user = mAuth.getCurrentUser();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Post model = list.get(position);
        Picasso.get().load(model.getPostImage()).into(holder.postImage);
        holder.postImage.setOnClickListener(v -> {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.imagelayout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            imageView = dialog.findViewById(R.id.imagePrev);
            try {
                Glide.with(context).load(model.getPostImage()).into(imageView);
            }catch (Exception e) {
            }
            dialog.show();
            imageView.setOnClickListener(view1 -> dialog.dismiss());
        });
        holder.name.setText(model.getPostedBy());
        holder.description.setText(model.getPostDescription());
        holder.tag.setText(model.getTag());
        holder.date.setText(TimeAgo.using(model.getPostedAt()));
        holder.like.setText(model.getPostLike()+"");
        holder.comment.setText(model.getPostComment()+"");
        Picasso.get().load(model.getProfile()).into(holder.profile);
        holder.comment.setOnClickListener(v -> context.startActivity(new Intent(context, CommentActivity.class)
                .putExtra("postId",model.getPostId())
                .putExtra("postedBy",model.getPostedBy())
                .putExtra("postLike",model.getPostLike())));
        FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId()).child("Likes").child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color,0,0,0);
                        }else{
                            holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_border,0,0,0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId()).child("Likes")
                        .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            FirebaseDatabase.getInstance().getReference().child("Posts")
                                    .child(model.getPostId()).child("Likes").child(FirebaseAuth.getInstance().getUid()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId())
                                    .child("postLike").setValue(model.getPostLike()-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_border,0,0,0);
                                    notifyDataSetChanged();
                                }
                            });
                        }else{
                            FirebaseDatabase.getInstance().getReference().child("Posts")
                                    .child(model.getPostId()).child("Likes").child(FirebaseAuth.getInstance().getUid())
                                    .setValue("Liked").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId())
                                            .child("postLike").setValue(model.getPostLike()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color,0,0,0);
                                            notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView profile, postImage;
        TextView name, date, like, comment, tag, description;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profileImage);
            postImage = itemView.findViewById(R.id.postImage);
            name = itemView.findViewById(R.id.usersName);
            date = itemView.findViewById(R.id.datePosted);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            tag = itemView.findViewById(R.id.tagText);
            description = itemView.findViewById(R.id.postDescription);
        }
    }
}
