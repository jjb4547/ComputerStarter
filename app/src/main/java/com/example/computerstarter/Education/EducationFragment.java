package com.example.computerstarter.Education;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;


//working on this
public class EducationFragment extends Fragment{
    ListView listView;
    String[] diffTitles;
    private FirebaseAuth mAuth;
    public EducationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //String[] diffTitles = getResources().getStringArray(R.array.comp_names);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //changed so that inflated happens first but is still returned at end
        View view = inflater.inflate(R.layout.education_layout, container, false);
        CardView pc = view.findViewById(R.id.pc_parts);
        CardView arduino = view.findViewById(R.id.Guides);
        CardView rasp = view.findViewById(R.id.rasp_pi);
        pc.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(),PC_Part_Activity.class)
                    .putExtra("Act","Edu"));
        });
        rasp.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), Education_Choosing_Activity.class)
                    .putExtra("component","Raspberry Pi")
                    .putExtra("Act","Edu"));
        });
        arduino.setOnClickListener(view2 -> {
            startActivity(new Intent(getActivity(), Education_Choosing_Activity.class)
                    .putExtra("component","Arduino")
                    .putExtra("Act","Edu"));
        });
        return view;
    }

}