package com.seniordesign.computerstarter.Education;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.computerstarter.R;

public class Education_IntermediateFragment extends Fragment {
    public Education_IntermediateFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intermediate, container, false);
        TextView brief_desc = view.findViewById(R.id.first_title);
        TextView info = view.findViewById(R.id.first_info);
        TextView ports_desc =view.findViewById(R.id.second_title);
        TextView info_desc = view.findViewById(R.id.second_info);
        TextView link_title = view.findViewById(R.id.useful_links);
        TextView actual_link = view.findViewById(R.id.component_links);
        ImageView imageView = view.findViewById(R.id.image_comp);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            String position = bundle.getString("component");
            brief_desc.setTypeface(null, Typeface.BOLD);
            ports_desc.setTypeface(null,Typeface.BOLD);
            link_title.setTypeface(null,Typeface.BOLD);
            switch (position){
                case "CPU":
                    //CPU
                    brief_desc.setText(getString(R.string.brief_description_cpu));
                    info.setText(getString(R.string.cpu_desc));
                    info_desc.setText(getString(R.string.cpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_cpu));
                    imageView.setImageResource(R.drawable.amd_cpu);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.cpu_link));
                    break;
                case "MotherBoard":
                    //Motherboard
                    brief_desc.setText(getString(R.string.brief_description_mot));
                    info.setText(getString(R.string.mot_desc));
                    info_desc.setText(getString(R.string.mot_ports_description));
                    ports_desc.setText(getString(R.string.ports_mot));
                    imageView.setImageResource(R.drawable.motherboard);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mot_link));
                    break;
                case "Memory":
                    //Memory
                    brief_desc.setText(getString(R.string.brief_description_mem));
                    info.setText(getString(R.string.mem_desc));
                    info_desc.setText(getString(R.string.mem_ports_description));
                    ports_desc.setText(getString(R.string.ports_mem));
                    imageView.setImageResource(R.drawable.memory);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mem_link));
                    break;
                case "Storage":
                    //Storage
                    brief_desc.setText(getString(R.string.brief_description_stor));
                    info.setText(getString(R.string.stor_desc));
                    info_desc.setText(getString(R.string.stor_ports_description));
                    ports_desc.setText(getString(R.string.ports_stor));
                    imageView.setImageResource(R.drawable.storage);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.stor_link));
                    break;
                case "Video Card":
                    //Video Card
                    brief_desc.setText(getString(R.string.brief_description_gpu));
                    info.setText(getString(R.string.gpu_desc));
                    info_desc.setText(getString(R.string.gpu_ports_description));
                    ports_desc.setText(getString(R.string.ports_gpu));
                    imageView.setImageResource(R.drawable.vga_pic);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.gpu_link));
                    break;
                case "Power Supply":
                    //Power Supply
                    brief_desc.setText(getString(R.string.brief_description_psu));
                    info.setText(getString(R.string.psu_desc));
                    info_desc.setText(getString(R.string.psu_ports_description));
                    ports_desc.setText(getString(R.string.ports_psu));
                    imageView.setImageResource(R.drawable.psu);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.psu_link));
                    break;
                case "Raspberry Pi and More":
                    //Raspberry Pi
                    brief_desc.setText("Coming Soon!");
                    break;
                case "Arduino and More":
                    //Arduino
                    brief_desc.setText("Coming Soon!");
                    break;
                case "CPU Cooler":
                    //CPU Cooler
                    brief_desc.setText(getString(R.string.brief_description_cpucool));
                    info.setText(getString(R.string.cpucool_desc));
                    info_desc.setText(getString(R.string.cpucool_ports_description));
                    ports_desc.setText(getString(R.string.ports_cpucool));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.cpucool_link));
                    break;
                case "Case":
                    //Case
                    brief_desc.setText(getString(R.string.brief_description_case));
                    info.setText(getString(R.string.case_desc));
                    info_desc.setText(getString(R.string.case_ports_description));
                    ports_desc.setText(getString(R.string.ports_case));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.case_link));
                    break;
                case "OS":
                    //OS
                    brief_desc.setText(getString(R.string.brief_description_os));
                    info.setText(getString(R.string.os_desc));
                    info_desc.setText(getString(R.string.os_ports_description));
                    ports_desc.setText(getString(R.string.ports_os));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.os_link));
                    break;
                case "Monitor":
                    //Monitor
                    brief_desc.setText(getString(R.string.brief_description_mon));
                    info.setText(getString(R.string.mon_desc));
                    info_desc.setText(getString(R.string.mon_ports_description));
                    ports_desc.setText(getString(R.string.ports_mon));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mon_link));
                    break;
                case "Peripherals":
                    //Peripherals
                    brief_desc.setText(getString(R.string.brief_description_per));
                    info.setText(getString(R.string.per_desc));
                    info_desc.setText(getString(R.string.per_ports_description));
                    ports_desc.setText(getString(R.string.ports_per));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.per_link));
                    break;
            }
            actual_link.setOnClickListener(v->{
                startActivity(new Intent(getContext(), WebPageEducation.class)
                        .putExtra("component",bundle.getString("component"))
                        .putExtra("from",bundle.getString("from"))
                        .putExtra("Act",bundle.getString("Act")));
            });
        }
        return view;
    }
}