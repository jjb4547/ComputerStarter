package com.example.computerstarter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BeginnerFragment extends Fragment {
    public BeginnerFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beginner, container, false);
        String[] diffTitles = getResources().getStringArray(R.array.comp_names);
        TextView brief_desc = view.findViewById(R.id.first_title);
        TextView info = view.findViewById(R.id.first_info);
        TextView ports_desc =view.findViewById(R.id.second_title);
        TextView info_desc = view.findViewById(R.id.second_info);
        ImageView imageView = view.findViewById(R.id.image_comp);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            int position = bundle.getInt("position");
            switch (position){
                case 0:
                    //CPU
                    brief_desc.setText("Coming Soon!");
                    break;
                case 1:
                    //Motherboard
                    brief_desc.setText("Coming Soon!");
                    break;
                case 2:
                    //Memory
                    brief_desc.setText("Coming Soon!");
                    break;
                case 3:
                    //Storage
                    brief_desc.setText("Coming Soon!");
                    break;
                case 4:
                    //Video Card
                    brief_desc.setText("Coming Soon!");
                    break;
                case 5:
                    //Power Supply
                    brief_desc.setText("Coming Soon!");
                    break;
                case 6:
                    //Raspberry Pi
                    brief_desc.setText("Coming Soon!");
                    break;
                case 7:
                    //Arduino
                    brief_desc.setText("Coming Soon!");
                    break;
                case 8:
                    //CPU Cooler
                    brief_desc.setText("Coming Soon!");
                    break;
                case 9:
                    //Case
                    brief_desc.setText("Coming Soon!");
                    break;
                case 10:
                    //OS
                    brief_desc.setText("Coming Soon!");
                    break;
                case 11:
                    //Monitor
                    brief_desc.setText("Coming Soon!");
                    break;
                case 12:
                    //Peripherals
                    brief_desc.setText("Coming Soon!");
                    break;

            }
        }
        return view;
    }

}