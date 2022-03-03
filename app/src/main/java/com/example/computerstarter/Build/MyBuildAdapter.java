package com.example.computerstarter.Build;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.computerstarter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyBuildAdapter extends RecyclerView.Adapter<MyBuildAdapter.ViewHolder>{
    ArrayList<Build_Data> build_data;
    Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private onCardListener onCardListener;
    private Build_Data build_data_list;

    public MyBuildAdapter(ArrayList<Build_Data> build_data, MyBuildActivity activity, onCardListener onCardListener) {
        this.build_data = build_data;
        this.context = activity;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.build_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, onCardListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        build_data_list = build_data.get(position);
        //System.out.println(build_data.get(position).getBuild_name());
        holder.tbuildName.setText(build_data_list.getBuildName().substring(10));
        holder.tbuildDate.setText(build_data_list.getBuildDate());
        holder.tbuildPrice.setText("Price: $"+build_data_list.getBuildPrice().substring(10));
        holder.tpartId.setText(build_data_list.getPartsId());
        //holder.cpuName.setText(build_data_list.getCpuName());

    }

    @Override
    public int getItemCount() {
        return build_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView buildImage;
        LinearLayout partsList;
        LinearLayout cardLayout;
        TextView tbuildName;
        TextView tbuildDate;
        TextView tbuildPrice;
        TextView tpartId;
        TextView cpuName;
        TextView motName;
        TextView memName;
        TextView storName;
        TextView psuName;
        TextView coolName;
        TextView monName;
        TextView vgaName;
        TextView caseName;
        TextView cpuTitle;
        TextView motTitle;
        TextView memTitle;
        TextView storTitle;
        TextView psuTitle;
        TextView coolTitle;
        TextView monTitle;
        TextView vgaTitle;
        TextView caseTitle;
        CardView cardView;
        onCardListener onCardListener;
        public ViewHolder(@NonNull View itemView, onCardListener onCardListener) {
            super(itemView);
            //buildImage = itemView.findViewById(R.id.image_item);
            cardView = itemView.findViewById(R.id.cardView);
            tbuildName = itemView.findViewById(R.id.build_name);
            tbuildDate = itemView.findViewById(R.id.date_started);
            tbuildPrice = itemView.findViewById(R.id.price);
            tpartId = itemView.findViewById(R.id.partID);
            cpuName = itemView.findViewById(R.id.cpuName);
            motName = itemView.findViewById(R.id.monName);
            memName = itemView.findViewById(R.id.memName);
            storName = itemView.findViewById(R.id.storName);
            psuName = itemView.findViewById(R.id.psuName);
            coolName = itemView.findViewById(R.id.coolName);
            monName = itemView.findViewById(R.id.monName);
            vgaName = itemView.findViewById(R.id.vgaName);
            caseName = itemView.findViewById(R.id.caseName);
            cpuTitle = itemView.findViewById(R.id.cpuTitle);
            motTitle = itemView.findViewById(R.id.monTitle);
            memTitle = itemView.findViewById(R.id.memTitle);
            storTitle = itemView.findViewById(R.id.storTitle);
            psuTitle = itemView.findViewById(R.id.psuTitle);
            coolTitle = itemView.findViewById(R.id.coolTitle);
            monTitle = itemView.findViewById(R.id.monTitle);
            vgaTitle = itemView.findViewById(R.id.vgaTitle);
            caseTitle = itemView.findViewById(R.id.caseTitle);
            partsList = itemView.findViewById(R.id.partsList);
            cardLayout = itemView.findViewById(R.id.cardLayout);
            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCardListener.onCardClick(getAdapterPosition());
            int[] partId = cleanUpIDs(getAdapterPosition());
            cardLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            if(partsList.getVisibility()==View.GONE){
                partsList.setVisibility(View.VISIBLE);
                cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.cardview_light));
                if(partId[0]!=-1) {
                    cpuTitle.setVisibility(View.VISIBLE);
                    cpuTitle.setText("CPU:");
                    cpuName.setVisibility(View.VISIBLE);
                    cpuName.setText(PriceList.getName(partId[0]));
                }else{
                    cpuTitle.setVisibility(View.GONE);
                    cpuName.setVisibility(View.GONE);
                }
                if(partId[1]!=-1) {
                    motTitle.setVisibility(View.VISIBLE);
                    motTitle.setText("Motherboard:");
                    motName.setVisibility(View.VISIBLE);
                    motName.setText(PriceList.getName(partId[1]));
                }else{
                    motTitle.setVisibility(View.GONE);
                    motName.setVisibility(View.GONE);
                }
                if(partId[2]!=-1) {
                    memTitle.setVisibility(View.VISIBLE);
                    memTitle.setText("Memory:");
                    memName.setVisibility(View.VISIBLE);
                    memName.setText(PriceList.getName(partId[2]));
                }else{
                    memTitle.setVisibility(View.GONE);
                    memName.setVisibility(View.GONE);
                }
                if(partId[3]!=-1) {
                    storTitle.setVisibility(View.VISIBLE);
                    storTitle.setText("Storage:");
                    storName.setVisibility(View.VISIBLE);
                    storName.setText(PriceList.getName(partId[3]));
                }else{
                    storTitle.setVisibility(View.GONE);
                    storName.setVisibility(View.GONE);
                }
                if(partId[4]!=-1) {
                    psuTitle.setVisibility(View.VISIBLE);
                    psuTitle.setText("PSU:");
                    psuName.setVisibility(View.VISIBLE);
                    psuName.setText(PriceList.getName(partId[4]));
                }else{
                    psuTitle.setVisibility(View.GONE);
                    psuName.setVisibility(View.GONE);
                }
                if(partId[5]!=-1) {
                    coolTitle.setVisibility(View.VISIBLE);
                    coolTitle.setText("Cooler:");
                    coolName.setVisibility(View.VISIBLE);
                    coolName.setText(PriceList.getName(partId[5]));
                }else{
                    coolTitle.setVisibility(View.GONE);
                    coolName.setVisibility(View.GONE);
                }
                if(partId[6]!=-1) {
                    monTitle.setVisibility(View.VISIBLE);
                    monTitle.setText("Monitor:");
                    monName.setVisibility(View.VISIBLE);
                    monName.setText(PriceList.getName(partId[6]));
                }else{
                    monTitle.setVisibility(View.GONE);
                    monName.setVisibility(View.GONE);
                }
                if(partId[7]!=-1) {
                    vgaTitle.setVisibility(View.VISIBLE);
                    vgaTitle.setText("GPU:");
                    vgaName.setVisibility(View.VISIBLE);
                    vgaName.setText(PriceList.getName(partId[7]));
                }else{
                    vgaTitle.setVisibility(View.GONE);
                    vgaName.setVisibility(View.GONE);
                }
                if(partId[8]!=-1) {
                    caseTitle.setVisibility(View.VISIBLE);
                    caseTitle.setText("Case:");
                    caseTitle.setVisibility(View.VISIBLE);
                    caseName.setText(PriceList.getName(partId[8]));
                }else{
                    caseTitle.setVisibility(View.GONE);
                    caseName.setVisibility(View.GONE);
                }
                notifyDataSetChanged();
            }else{
                partsList.setVisibility(View.GONE);
                cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.cardview_dark));
                notifyDataSetChanged();
            }
        }


        @Override
        public boolean onLongClick(View view) {
            onCardListener.onLongClick(getAdapterPosition());
            return true;
        }
    }
    public interface onCardListener{
        void onCardClick(int position);
        void onLongClick(int position);
    }

    public int[] cleanUpIDs(int position) {
        String[] strArr = null;
        int[] partsID = new int[9];
        strArr = build_data.get(position).getPartsId().substring(10).split(",");
        for(int i=0;i<strArr.length;i++){
            partsID[i] = Integer.parseInt(strArr[i]);
        }
        return partsID;
    }
}
