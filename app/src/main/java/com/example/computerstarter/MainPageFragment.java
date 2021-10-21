package com.example.computerstarter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageFragment extends Fragment {
    private boolean sortAscending = true;
    private boolean unSorted = true;
    private ListView listView;
    private String[] diffTitles;
    public MainPageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    //Comment new
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                Intent intent_settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.login:
                Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
                Intent intent_account = new Intent(getActivity(), Login.class);
                startActivity(intent_account);
                break;
            case R.id.quiz:
                Toast.makeText(getActivity(), "Quiz", Toast.LENGTH_SHORT).show();
                Intent intent_quiz = new Intent(getActivity(), QuizActivity.class);
                intent_quiz.putExtra("ID","Building");
                startActivity(intent_quiz);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        //my changes to get listView
        //might need its own method
        TextView text = view.findViewById(R.id.title);
        listView = view.findViewById(R.id.lvEdu);
        listView.setClickable(true);
        diffTitles = getResources().getStringArray(R.array.comp_names);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                diffTitles
        );
        listView.setAdapter(listViewAdapter);
        return view;
    }

}