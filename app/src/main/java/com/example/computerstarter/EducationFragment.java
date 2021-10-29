package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //changed so that inflated happens first but is still returned at end
        View view = inflater.inflate(R.layout.education_layout, container, false);
        CardView pc = view.findViewById(R.id.pc_parts);
        CardView arduino = view.findViewById(R.id.arduinos);
        CardView rasp = view.findViewById(R.id.rasp_pi);
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PC_Part_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}