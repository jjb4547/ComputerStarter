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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    //Comment new
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.real_login:
                Toast.makeText(getActivity(), "real_login", Toast.LENGTH_SHORT).show();
                Intent intent_account = new Intent(getActivity(), real_login.class);
                startActivity(intent_account);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        CardView builds = view.findViewById(R.id.builds);
        builds.setOnClickListener(view->{
            startActivity(new Intent(getActivity(),Build_Activity.class));
        });
        //my changes to get listView
        //might need its own method
        return view;
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        mAuth = FirebaseAuth.getInstance();
        MenuItem item;
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
    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Tutorial");
        alert.setMessage("Is this your first time using the app?");
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(),HelpActivity.class);
                startActivity(intent);
            }
        });
        alert.create().show();
    }
}