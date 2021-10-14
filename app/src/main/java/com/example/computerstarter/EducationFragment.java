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


//working on this
public class EducationFragment extends Fragment{

    public EducationFragment() {
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
            case R.id.account:
                Toast.makeText(getActivity(), "Account", Toast.LENGTH_SHORT).show();
                Intent intent_account = new Intent(getActivity(), AccountActivity.class);
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
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        //my changes to get listView
        //might need its own method
        String [] eduParts = {"CPU",
        "MotherBoard",
        "Memory",
        "Storage",
        "Video Card",
        "PowerSupply",
        "Raspberry Pi and more",
        "Arduino and more"};
        ListView listView = (ListView) view.findViewById(R.id.lvEdu);
        listView.setClickable(true);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                eduParts
        );
        listView.setAdapter(listViewAdapter);

        //handling click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //they all open the same thing for now
                if(position ==0){
                    //testing
                    //Toast.makeText(getActivity(),"Clicked CPU", Toast.LENGTH_SHORT).show();
                    //gonna pass more stuff in to make it so I dont need like 8 different activities(just one)
                    /*
                    TextView title = (TextView) view.findViewById(R.id.partTitle);
                    title.setText("CPU");
                    */
                    //trying to change title

                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",0);
                    startActivity(i);
                }
                else if(position == 1){
                    //Toast.makeText(getActivity(),"Clicked mother", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",1);
                    startActivity(i);
                }
                else if(position == 2){
                    //Toast.makeText(getActivity(),"Clicked memory", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",2);
                    startActivity(i);
                }
                else if(position == 3){
                    //Toast.makeText(getActivity(),"Clicked storage", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",3);
                    startActivity(i);
                }
                else if(position == 4){
                    //Toast.makeText(getActivity(),"Clicked video card", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",4);
                    startActivity(i);
                }
                else if(position == 5){
                    //Toast.makeText(getActivity(),"Clicked power supply", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",5);
                    startActivity(i);
                }
                else if(position == 6){
                    //Toast.makeText(getActivity(),"Clicked raspberry", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",6);
                    startActivity(i);
                }
                else if(position == 7){
                    //Toast.makeText(getActivity(),"Clicked arduino", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(),Education_Tabbed.class);
                    i.putExtra("position",7);
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