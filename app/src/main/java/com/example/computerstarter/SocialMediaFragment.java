package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaFragment extends Fragment {
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String myuid;
    RecyclerView recyclerView;
    List<SocialMediaModel> posts;
    SocialMediaAdapter adapterPosts;
    public SocialMediaFragment() {
        // Required empty public constructor
    }

    private void loadPosts() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SocialMediaModel modelPost = dataSnapshot1.getValue(SocialMediaModel.class);
                    posts.add(modelPost);
                    adapterPosts = new SocialMediaAdapter(getActivity(), posts);
                    recyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        //FloatingActionButton button = view.findViewById(R.id.buttonCreate);
        recyclerView = view.findViewById(R.id.RecycleForum);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        posts = new ArrayList<>();
        ImageView profile = view.findViewById(R.id.profilePic);
        Button text = view.findViewById(R.id.textSome);
        if(user!=null) {
            loadPosts();
            //profile.setImageURI(user.getPhotoUrl());
            text.setOnClickListener(view1 -> {
                startActivity(new Intent(getContext(), SocialMediaBlogs.class));
            });
        }else{
            Toast.makeText(getContext(),"PLEASE LOGIN TO VIEW POSTS",Toast.LENGTH_SHORT).show();
            profile.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment frag = new SocialMediaBlogs();
//
//
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container, frag);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.addToBackStack(null);
//                ft.commit();
                Intent intent = new Intent(getContext(),SocialMediaBlogs.class);
                startActivity(intent);
            }
        });

         */
        return view;
    }

}
