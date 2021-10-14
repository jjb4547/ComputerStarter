package com.example.computerstarter;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_cpu));
                    info.setText(getString(R.string.cpu_desc));
                    info_desc.setText(getString(R.string.cpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_cpu));
                    imageView.setImageResource(R.mipmap.ic_logo);
                    break;
                case 1:
                    //Motherboard
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_mot));
                    info.setText(getString(R.string.mot_desc));
                    info_desc.setText(getString(R.string.mot_ports_description));
                    ports_desc.setText(getString(R.string.ports_mot));
                    break;
                case 2:
                    //Memory
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_mem));
                    info.setText(getString(R.string.mem_desc));
                    info_desc.setText(getString(R.string.mem_ports_description));
                    ports_desc.setText(getString(R.string.ports_mem));
                    break;
                case 3:
                    //Storage
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_stor));
                    info.setText(getString(R.string.stor_desc));
                    info_desc.setText(getString(R.string.stor_ports_description));
                    ports_desc.setText(getString(R.string.ports_stor));
                    break;
                case 4:
                    //Video Card
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_gpu));
                    info.setText(getString(R.string.gpu_desc));
                    info_desc.setText(getString(R.string.gpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_gpu));
                    break;
                case 5:
                    //Power Supply
                    brief_desc.setTypeface(null, Typeface.BOLD);
                    ports_desc.setTypeface(null,Typeface.BOLD);
                    brief_desc.setText(getString(R.string.brief_description_psu));
                    info.setText(getString(R.string.psu_desc));
                    info_desc.setText(getString(R.string.psu_ports_description));
                    ports_desc.setText(getString(R.string.ports_psu));
                    break;
                case 6:
                    //Raspberry Pi
                    break;
                case 7:
                    //Arduino
                    break;

            }
        }
        return view;
    }

}