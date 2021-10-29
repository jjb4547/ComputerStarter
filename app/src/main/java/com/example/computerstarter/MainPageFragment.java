package com.example.computerstarter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
            showAlertDialog();
        });
        CardView feat = view.findViewById(R.id.helpful_link);
        feat.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Link to CPUs",Toast.LENGTH_SHORT).show();
        });
        CardView feat_1 = view.findViewById(R.id.helpful_link_1);
        feat.setOnClickListener(view ->{
            Toast.makeText(getActivity(),"Link to CPUs",Toast.LENGTH_SHORT).show();
        });
        CardView feat_2 = view.findViewById(R.id.helpful_link_2);
        feat.setOnClickListener(view ->{

        });
        //my changes to get listView
        //might need its own method
        return view;
    }
    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Build Name");
        alert.setMessage("Enter Your Build Name");
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = input.getText().toString();
                Intent intent = new Intent(getActivity(),Build_Activity.class);
                intent.putExtra("Build",value);
                startActivity(intent);
            }
        });
        alert.create().show();
    }
}