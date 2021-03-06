package com.seniordesign.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.seniordesign.computerstarter.Build.MainBuilds;
import com.seniordesign.computerstarter.Guides.Guides_Activity;
import com.example.computerstarter.R;
import com.seniordesign.computerstarter.SampleProjects.SampleProjects;
import com.google.firebase.auth.FirebaseAuth;


//working on this
public class EducationFragment extends Fragment{
    ListView listView;
    String[] diffTitles;
    private FirebaseAuth mAuth;
    MainBuilds mainBuilds;
    public EducationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainBuilds = new MainBuilds();
        //String[] diffTitles = getResources().getStringArray(R.array.comp_names);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //changed so that inflated happens first but is still returned at end
        View view = inflater.inflate(R.layout.education_layout, container, false);
        CardView pc = view.findViewById(R.id.pc_parts);
        CardView guides = view.findViewById(R.id.Guides);
        CardView sampleProj = view.findViewById(R.id.rasp_pi);
        ImageButton imageButton = view.findViewById(R.id.menuButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainBuilds)getActivity()).openDrawer();
            }
        });
        pc.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(),PC_Part_Activity.class)
                    .putExtra("Act","Edu"));
        });
        sampleProj.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), SampleProjects.class));
        });
        guides.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), Guides_Activity.class));
        });
        return view;
    }

}