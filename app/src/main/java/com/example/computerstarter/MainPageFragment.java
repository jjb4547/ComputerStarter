package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
            startActivity(new Intent(getActivity(),MyBuildActivity.class));
        });
        CardView feat = view.findViewById(R.id.helpful_link);
        feat.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Future Improvement",Toast.LENGTH_SHORT).show();
        });
        CardView feat_1 = view.findViewById(R.id.helpful_link_1);
        feat_1.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Future Improvement",Toast.LENGTH_SHORT).show();
        });
        CardView feat_2 = view.findViewById(R.id.helpful_link_2);
        feat_2.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Future Improvement",Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getActivity(),Arduino_Projects.class);
            //startActivity(intent);
        });
        return view;
    }
}