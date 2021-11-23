package com.example.computerstarter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BeginnerFragment extends Fragment {
    public BeginnerFragment() {
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
                    info.setText(getString(R.string.cpu_desc_basic));
                    info_desc.setText(getString(R.string.cpu_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_cpu));
                    imageView.setImageResource(R.drawable.amd_cpu);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.cpu_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "MotherBoard":
                    //Motherboard
                    brief_desc.setText(getString(R.string.brief_description_mot));
                    info.setText(getString(R.string.mot_desc_basic));
                    info_desc.setText(getString(R.string.mot_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_mot));
                    imageView.setImageResource(R.drawable.motherboard);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mot_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Memory":
                    //Memory
                    brief_desc.setText(getString(R.string.brief_description_mem));
                    info.setText(getString(R.string.mem_desc_basic));
                    info_desc.setText(getString(R.string.mem_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_mem));
                    imageView.setImageResource(R.drawable.memory);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mem_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Storage":
                    //Storage
                    brief_desc.setText(getString(R.string.brief_description_stor));
                    info.setText(getString(R.string.stor_desc_basic));
                    info_desc.setText(getString(R.string.stor_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_stor));
                    imageView.setImageResource(R.drawable.storage);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.stor_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Video Card":
                    //Video Card
                    brief_desc.setText(getString(R.string.brief_description_gpu));
                    info.setText(getString(R.string.gpu_desc_basic));
                    info_desc.setText(getString(R.string.gpu_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_gpu));
                    imageView.setImageResource(R.drawable.vga_pic);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.gpu_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Power Supply":
                    //Power Supply
                    brief_desc.setText(getString(R.string.brief_description_psu));
                    info.setText(getString(R.string.psu_desc_basic));
                    info_desc.setText(getString(R.string.psu_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_psu));
                    imageView.setImageResource(R.drawable.psu);
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.psu_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Raspberry Pi":
                    //Raspberry Pi
                    brief_desc.setText(R.string.brief_description_rasp);
                    info.setText(R.string.rasp_desc);
                    break;
                case "Arduino":
                    //Arduino
                    brief_desc.setText(R.string.brief_description_ard);
                    info.setText(R.string.ard_desc);
                    break;
                case "CPU Cooler":
                    //CPU Cooler
                    brief_desc.setText(getString(R.string.brief_description_cpucool));
                    info.setText(getString(R.string.cpucool_desc_basic));
                    info_desc.setText(getString(R.string.cpucool_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_cpucool));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.cpucool_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Case":
                    //Case
                    brief_desc.setText(getString(R.string.brief_description_case));
                    info.setText(getString(R.string.case_desc_basic));
                    info_desc.setText(getString(R.string.case_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_case));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.case_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "OS":
                    //OS
                    brief_desc.setText(getString(R.string.brief_description_os));
                    info.setText(getString(R.string.os_desc_basic));
                    info_desc.setText(getString(R.string.os_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_os));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.os_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Monitor":
                    //Monitor
                    brief_desc.setText(getString(R.string.brief_description_mon));
                    info.setText(getString(R.string.mon_desc_basic));
                    info_desc.setText(getString(R.string.mon_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_mon));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.mon_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
                case "Peripherals":
                    //Peripherals
                    brief_desc.setText(getString(R.string.brief_description_per));
                    info.setText(getString(R.string.per_desc_basic));
                    info_desc.setText(getString(R.string.per_ports_description_basic));
                    ports_desc.setText(getString(R.string.ports_per));
                    //imageView.setImageResource(R.drawable.amd_cpu); //need image
                    link_title.setText(getString(R.string.link_title));
                    actual_link.setText(getString(R.string.per_link));
                    actual_link.setMovementMethod(LinkMovementMethod.getInstance());
                    actual_link.setLinkTextColor(Color.BLUE);
                    break;
            }
        }
        return view;
    }
}