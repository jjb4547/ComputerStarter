package com.example.computerstarter.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.computerstarter.Build.MyBuildActivity;
import com.example.computerstarter.Guides.Arduino.Arduino_Guides_Activity;
import com.example.computerstarter.Guides.PC.PC_Building_Guide_Activity;
import com.example.computerstarter.Guides.RaspberryPi.RaspberryPi_Guides_Activity;
import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainPageFragment extends Fragment {
    private boolean sortAscending = true;
    private boolean unSorted = true;
    private ListView listView;
    private String[] diffTitles;
    public boolean addcardview;
    private View view;
    FirebaseAuth mAuth;
    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        CardView builds = view.findViewById(R.id.builds);
        builds.setOnClickListener(view->{
            startActivity(new Intent(getActivity(), MyBuildActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
        });
        CardView feat = view.findViewById(R.id.helpful_link);
        feat.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"PC Building Guides",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), PC_Building_Guide_Activity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
        });
        CardView feat_1 = view.findViewById(R.id.helpful_link_1);
        feat_1.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Community Builds",Toast.LENGTH_SHORT).show();
        });
        CardView feat_2 = view.findViewById(R.id.helpful_link_2);
        feat_2.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Raspberry Pi Guides",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), RaspberryPi_Guides_Activity.class));
        });
        CardView feat_3 = view.findViewById(R.id.helpful_link_3);
        feat_3.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Arduino Guides",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), Arduino_Guides_Activity.class));
        });
        return view;
    }
}