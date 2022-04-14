package com.example.computerstarter.Build;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.computerstarter.Guides.PC.PC_Building_Guide_Activity;
import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainBuildsFragment extends Fragment {
    public boolean addcardview;
    private View view;
    FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser user;
    private DocumentReference build_ref;
    private Build_Data build_data;
    private ArrayList<Build_Data> build;
    TextView recent_title, recent_Price,recent_Name;

    public MainBuildsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        user = mAuth.getCurrentUser();
        build = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_builds_main, container, false);
        CardView builds = view.findViewById(R.id.builds);
        recent_Price = view.findViewById(R.id.recent_build_price);
        recent_title = view.findViewById(R.id.recent_build_Title);
        recent_Name  = view.findViewById(R.id.recent_build_Name);
        ImageButton menuButton = view.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainBuilds)getActivity()).openDrawer();
            }
        });
        builds.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), MyBuildActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        CardView recent_build = view.findViewById(R.id.recent_build);
        if(user!=null){
            build_ref = firestore.collection("Users").document(user.getUid());
            build_ref.get().addOnSuccessListener(documentSnapshot -> {
                build_data = documentSnapshot.toObject(Build_Data.class);
                if(build_data.getBuild_name().size()==0){
                    recent_build.setVisibility(View.VISIBLE);
                    recent_title.setVisibility(View.GONE);
                    recent_Name.setText("No Recent Builds");
                }else if(build_data.getBuild_name().size()>0){
                    recent_build.setVisibility(View.VISIBLE);
                    int size = build_data.getBuild_name().size();
                    build.add(new Build_Data(build_data.getBuild_name().get(size-1),
                            build_data.getBuild_date().get(size-1),build_data.getPrice().get(size-1),
                            build_data.getParts_id().get(size-1),build_data.getNum_parts().get(size-1)));
                    recent_Name.setText("NAME: "+build.get(0).getBuildName().substring(10));
                    recent_Price.setText("PRICE: $"+build.get(0).getBuildPrice().substring(10));
                    recent_build.setOnClickListener(v->{

                    });
                }
            });
        }else{
            recent_build.setVisibility(View.GONE);
        }
        CardView feat = view.findViewById(R.id.helpful_link);
        feat.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"PC Building Guides",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), PC_Building_Guide_Activity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
        });
        return view;
    }
}