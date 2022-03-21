package com.example.computerstarter.SocialMedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SwipeRefreshLayout swipeRefreshLayout;
    String dp;

    public SocialMediaFragment() {
        // Required empty public constructor
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
        loadPosts();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        ArrayList<String> arrayList = new ArrayList<>();
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
        MaterialButton loginBut = view.findViewById(R.id.loginBut);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        TextView loginText = view.findViewById(R.id.loginText);
        button = view.findViewById(R.id.buttonCreate);
        spinner = view.findViewById(R.id.pspinRV);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            loginBut.setVisibility(View.GONE);
            loginText.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(view1 -> {
            /*Fragment frag = new SocialMediaBlogs();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, frag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();*/
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
        recyclerView = view.findViewById(R.id.RecycleForum);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        posts = new ArrayList<>();
        loadPosts();
        return view;
    }

}
