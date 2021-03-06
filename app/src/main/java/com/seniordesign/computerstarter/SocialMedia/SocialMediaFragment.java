package com.seniordesign.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seniordesign.computerstarter.Build.MainBuilds;
import com.example.computerstarter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SocialMediaFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    String myuid;
    RecyclerView recyclerView;
    ArrayList<Post> posts;
    FloatingActionButton button;
    ImageButton filter,menuButton;
    PostAdapter postAdapter;
    String tag = "";
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
        filter = view.findViewById(R.id.filterButton);
        menuButton = view.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainBuilds)getActivity()).openDrawer();
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), filter);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        tag = item.getTitle().toString();
                        if(tag.equals("Clear Filter"))
                            tag = "";
                        if(user!=null)
                            loadPost();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddPost.class);
            startActivity(intent);
        });
        postAdapter = new PostAdapter(posts, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(postAdapter);
        loadPost();
        return view;
    }

    private void loadPost() {
        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tag.equals("")) {
                    posts.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        post.setPostId(dataSnapshot.getKey());
                        posts.add(post);
                    }
                    postAdapter.notifyDataSetChanged();
                }else{
                    database.getReference().child("Posts").orderByChild("tag").equalTo(tag).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            posts.clear();
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
