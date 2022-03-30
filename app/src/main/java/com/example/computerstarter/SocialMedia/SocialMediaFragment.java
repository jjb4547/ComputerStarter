package com.example.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.ArrayList;
import java.util.List;

public class SocialMediaFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String myuid;
    RecyclerView recyclerView;
    List<SocialMediaModel> posts;
    SocialMediaAdapter adapterPosts;
    FloatingActionButton button;
    Spinner spinner;
    String textSpin = "";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SwipeRefreshLayout swipeRefreshLayout;
    String dp;

    public SocialMediaFragment() {
        // Required empty public constructor
    }
    private void loadTagPosts(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ProfileImage/Users/"+user.getUid()+"/profile");
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> dp = uri.toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        if(!textSpin.equals("")) {
            databaseReference.orderByChild("tag").equalTo(textSpin).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    posts.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SocialMediaModel modelPost = dataSnapshot.getValue(SocialMediaModel.class);
                        modelPost.setUdp(dp);
                        posts.add(modelPost);
                        adapterPosts = new SocialMediaAdapter(getActivity(), posts);
                        recyclerView.setAdapter(adapterPosts);
                        adapterPosts.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            loadPosts();
        }
    }

    private void loadPosts() {
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("ProfileImage/Users/"+user.getUid()+"/profile");
        storageReference1.getDownloadUrl().addOnSuccessListener(uri -> dp = uri.toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SocialMediaModel modelPost = dataSnapshot1.getValue(SocialMediaModel.class);
                    modelPost.setUdp(dp);
                    posts.add(modelPost);
                    adapterPosts = new SocialMediaAdapter(getActivity(), posts);
                    recyclerView.setAdapter(adapterPosts);
                    adapterPosts.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                //Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if(user!=null) {
            loadPosts();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(user!=null) {
            loadPosts();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.add("Computer");
        arrayList.add("GPU");
        arrayList.add("CPU");
        arrayList.add("Motherboard");
        arrayList.add("Power");
        arrayList.add("Ram");
        arrayList.add("Memory");
        arrayList.add("RGB");
        arrayList.add("Tower");
        arrayList.add("Help!");
        arrayList.add("Monitor");
        arrayList.add("Mouse");
        arrayList.add("Keyboard");
        arrayList.add("Setup");
        arrayList.add("Stream");
        arrayList.add("Coding");
        arrayList.add("Design");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        //used for spinner dropdown on RecyclerView
        spinner = view.findViewById(R.id.pspinRV);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textSpin = spinner.getSelectedItem().toString();
                //System.out.println(textSpin + " Testing Values!");
                loadTagPosts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Inflate the layout for this fragment
        MaterialButton loginBut = view.findViewById(R.id.loginBut);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                spinner.setSelection(0);
                loadPosts();
                swipeRefreshLayout.setRefreshing(false);
                //value of spinner selected, need to send to db
            }
        });
        TextView loginText = view.findViewById(R.id.loginText);
        button = view.findViewById(R.id.buttonCreate);

        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.RecycleForum);
        if(user!=null){
            loginBut.setVisibility(View.GONE);
            loginText.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(view1 -> {
                Intent intent = new Intent(getContext(),SocialMediaBlogs.class);
                startActivity(intent);
            });
        }else{
            loginBut.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
            loginBut.setOnClickListener(v->{
                startActivity(new Intent(getContext(), Login_SignUpActivity.class));
            });
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        posts = new ArrayList<>();
        if(user!=null) {
            loadPosts();
        }
        return view;
    }
}
