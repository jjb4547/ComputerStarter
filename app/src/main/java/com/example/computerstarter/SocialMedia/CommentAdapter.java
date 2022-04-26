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
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder>{
    Context context;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<Comment> list;
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postcomments,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Comment comment = list.get(position);
        holder.commentBody.setText(comment.getCommentBody());
        holder.date.setText(TimeAgo.using(comment.getCommentedAt()));
        holder.name.setText(comment.getCommentedBy());
        StorageReference profileRef = storageReference.child("ProfileImage/Users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profile");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(holder.profile));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView commentBody, date,name;
        ImageView profile;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            commentBody = itemView.findViewById(R.id.postCommentText);
            date = itemView.findViewById(R.id.postCommentDate);
            name = itemView.findViewById(R.id.postCommentName);
            profile = itemView.findViewById(R.id.profileImageComment);
        }
    }
}
