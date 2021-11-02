package com.example.computerstarter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyBuildAdapter extends RecyclerView.Adapter<MyBuildAdapter.ViewHolder>{
    Build_Data[] build_data;
    Context context;

    public MyBuildAdapter(Build_Data[] build_data, MyBuildActivity activity) {
        this.build_data = build_data;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.build_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Build_Data build_data_list = build_data[position];
        holder.tbuildName.setText(build_data_list.getBuild_name());
        holder.tbuildDate.setText(build_data_list.getBuild_date());
        holder.buildImage.setImageResource(build_data_list.getBuild_image());
    }

    @Override
    public int getItemCount() {
        return build_data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView buildImage;
        TextView tbuildName;
        TextView tbuildDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buildImage = itemView.findViewById(R.id.image_item);
            tbuildName = itemView.findViewById(R.id.build_name);
            tbuildDate = itemView.findViewById(R.id.date_started);
        }
    }
}
