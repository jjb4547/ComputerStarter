package com.example.computerstarter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
