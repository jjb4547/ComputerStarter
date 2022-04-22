package com.example.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.computerstarter.Build.MainBuilds;
import com.example.computerstarter.Login.Login_SignUpActivity;
import com.example.computerstarter.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    String myuid;
    RecyclerView recyclerView;
    ArrayList<Post> posts;
    FloatingActionButton button;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        database = FirebaseDatabase.getInstance();
        //used for spinner dropdown on RecyclerView
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.commView);
        posts = new ArrayList<>();
        button = view.findViewById(R.id.buttonCreate);
        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddPost.class);
            startActivity(intent);
        });
        PostAdapter postAdapter = new PostAdapter(posts, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);
        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                posts.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId(dataSnapshot.getKey());
                    posts.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
