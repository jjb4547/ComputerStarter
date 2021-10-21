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
    private boolean sortAscending = true;
    private boolean unSorted = true;
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
            case R.id.action_sort:
                if(unSorted)
                    Toast.makeText(getActivity(), "A-Z",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Z-A",Toast.LENGTH_SHORT).show();
                sortData();
        }
        return super.onOptionsItemSelected(item);
    }
    private void sortData(){
        List<String>sortedTitles = Arrays.asList(getResources().getStringArray(R.array.comp_names));
        if(unSorted)
            Collections.sort(sortedTitles);
        else {
            Collections.sort(sortedTitles);
            Collections.reverse(sortedTitles);
        }
        diffTitles = sortedTitles.toArray(new String[0]);
        sortAscending=!sortAscending;
        unSorted=!unSorted;
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,diffTitles));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //changed so that inflated happens first but is still returned at end
        View view = inflater.inflate(R.layout.fragment_education, container, false);
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

        //handling click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //they all open the same thing for now
                if(position ==0){
                    //testing
                    Toast.makeText(getActivity(),diffTitles[0], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[0]);
                    startActivity(i);
                }
                else if(position == 1){
                    Toast.makeText(getActivity(),diffTitles[1], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[1]);
                    startActivity(i);
                }
                else if(position == 2){
                    Toast.makeText(getActivity(),diffTitles[2], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[2]);
                    startActivity(i);
                }
                else if(position == 3){
                    Toast.makeText(getActivity(),diffTitles[3], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[3]);
                    startActivity(i);
                }
                else if(position == 4){
                    Toast.makeText(getActivity(),diffTitles[4], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[4]);
                    startActivity(i);
                }
                else if(position == 5){
                    Toast.makeText(getActivity(),diffTitles[5], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[5]);
                    startActivity(i);
                }
                else if(position == 6){
                    Toast.makeText(getActivity(),diffTitles[6], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[6]);
                    startActivity(i);
                }
                else if(position == 7){
                    Toast.makeText(getActivity(),diffTitles[7], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[7]);
                    startActivity(i);
                }
                else if(position == 8){
                    Toast.makeText(getActivity(),diffTitles[8], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[8]);
                    startActivity(i);
                }
                else if(position == 9){
                    Toast.makeText(getActivity(),diffTitles[9], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[9]);
                    startActivity(i);
                }
                else if(position == 10){
                    Toast.makeText(getActivity(),diffTitles[10], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[10]);
                    startActivity(i);
                }
                else if(position == 11){
                    Toast.makeText(getActivity(),diffTitles[11], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[11]);
                    startActivity(i);
                }
                else if(position == 12){
                    Toast.makeText(getActivity(),diffTitles[12], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("component",diffTitles[12]);
                    startActivity(i);
                }
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_education, container, false);
        return view;
    }
    /*
    private void partsInfo(){
        String[] diffTitles ={"CPU","Motherboard","Memory(RAM)","Storage","Video Card(GPU)","Power Supply","Raspberry Pi and Possibilities",
        "Arduino and Possibilities"};
        String[] diffPara;

    }

     */


}