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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
        //menu.findItem(R.id.action_sort).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                Intent intent_settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.real_login:
                Toast.makeText(getActivity(), "login", Toast.LENGTH_SHORT).show();
                Intent intent_account = new Intent(getActivity(), real_login.class);
                startActivity(intent_account);
                break;

            case R.id.quiz:
                Toast.makeText(getActivity(), "Quiz", Toast.LENGTH_SHORT).show();
                Intent intent_quiz = new Intent(getActivity(), QuizActivity.class);
                intent_quiz.putExtra("ID","Education");
                startActivity(intent_quiz);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.add);
        if(item!=null)
            item.setVisible(false);
        item = menu.findItem(R.id.action_sort);
        if(item!=null)
            item.setVisible(false);
    }
}