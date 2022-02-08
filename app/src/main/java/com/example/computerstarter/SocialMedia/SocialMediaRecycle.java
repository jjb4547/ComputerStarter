package com.example.computerstarter.SocialMedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstarter.R;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaRecycle extends RecyclerView.Adapter<SocialMediaRecycle.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public EditText postTextView;
        TextView TV;
        ImageView IV;
        ImageView PP;
        ViewHolder(View view) {
            super(view);
//            postTextView = view.findViewById(R.id.TextInfForum);
            TV = view.findViewById(R.id.TitleForum);
            IV = view.findViewById(R.id.PostForum);
            PP = view.findViewById(R.id.ProfileForum);
        }
    }

    List<PostInformation> mPI;

    SocialMediaRecycle(List<PostInformation> PI) {
        mPI = PI;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View PostView = inflater.inflate(R.layout.activity_forum, parent, false);
        ViewHolder viewHolder = new ViewHolder(PostView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostInformation PI = mPI.get(position);

//        EditText editText = holder.postTextView;
//        editText.setText(PI.getPost());

        holder.TV.setText(PI.getPost());
        holder.IV.setImageResource(R.mipmap.ic_logo);
        holder.PP.setImageResource(R.drawable.ic_people);

    }

    @Override
    public int getItemCount() {
        return mPI.size();
    }


}

class PostInformation {
    private String mPostText;

    PostInformation(String postText) {
        mPostText = postText;
    }

    String getPost() {
        return mPostText;
    }

    static int postID = 0;

    static ArrayList<PostInformation> createPost() {
        ArrayList<PostInformation> PI = new ArrayList<>();

        for(int i = 1; i <= postID; i++) {
            PI.add(new PostInformation("Post" + ++postID));
        }

        return PI;
    }
}
