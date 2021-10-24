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
import android.widget.Toast;

import androidx.annotation.NonNull;
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
            case R.id.real_login:
                Toast.makeText(getActivity(), "real_login", Toast.LENGTH_SHORT).show();
                Intent intent_account = new Intent(getActivity(), real_login.class);
                startActivity(intent_account);
                break;
            case R.id.quiz:
                Toast.makeText(getActivity(), "Quiz", Toast.LENGTH_SHORT).show();
                Intent intent_quiz = new Intent(getActivity(), QuizActivity.class);
                intent_quiz.putExtra("ID","Building");
                startActivity(intent_quiz);
                break;
            case R.id.add:
                showAlertDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(addcardview)
            view = inflater.inflate(R.layout.fragment_main_page, container, false);
        else
            view = inflater.inflate(R.layout.fragment_main_page_2,container,false);

        //my changes to get listView
        //might need its own method
        return view;
    }
    public void showAlertDialog(){
        final EditText input = new EditText(getActivity());
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Enter name");
        alert.setMessage("Please enter name of build:");
        alert.setView(input);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addcardview=true;
                String name = input.getText().toString();
                Intent intent = new Intent(getActivity(), Build_Activity.class);
                intent.putExtra("names", name);
                startActivity(intent);
            }
        });
        alert.create().show();
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        mAuth = FirebaseAuth.getInstance();
        MenuItem item = menu.findItem(R.id.action_sort);
        if(item!=null)
            item.setVisible(false);
        if(mAuth.getCurrentUser()!=null){
            item = menu.findItem(R.id.real_login);
            if(item!=null)
                item.setVisible(false);
        }else{
            item = menu.findItem(R.id.real_login);
            if(item!=null)
                item.setVisible(true);
        }
    }
}