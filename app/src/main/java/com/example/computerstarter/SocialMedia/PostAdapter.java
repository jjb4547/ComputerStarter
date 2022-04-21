package com.example.computerstarter.SocialMedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {
    ArrayList<Post> list;
    Context context;

    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Post model = list.get(position);
        if(!model.isDefault()) {
            Picasso.get()
                    .load(model.getPostImage())
                    .placeholder(R.drawable.blank)
                    .into(holder.postImage);
        }else{
            holder.postImage.setVisibility(View.GONE);
        }
        holder.name.setText(model.getPostedBy());
        holder.description.setText(model.getPostDescription());
        holder.tag.setText(model.getTag());
        holder.like.setText(model.getPostLike()+"");
        FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId()).child("Likes")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color, 0, 0, 0);
                    notifyDataSetChanged();
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
                                }
                            });
                            notifyDataSetChanged();
                        }else{
                            FirebaseDatabase.getInstance().getReference().child("Posts")
                                    .child(model.getPostId()).child("Likes").child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId())
                                            .child("postLike").setValue(model.getPostLike()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_heart_color,0,0,0);
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
