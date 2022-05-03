package com.seniordesign.computerstarter.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computerstarter.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletePartsAdapter extends ArrayAdapter<PartsItem> {
    private List<PartsItem> partsListFull;

    public AutoCompletePartsAdapter(@NonNull Context context, @NonNull List<PartsItem> partsItemList) {
        super(context,0, partsItemList);
        partsListFull = new ArrayList<>(partsItemList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return partsFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.parts_autocomplete, parent,false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.text_view);
        ImageView imageView = convertView.findViewById(R.id.image_parts);
        PartsItem partsItem = getItem(position);
        if(partsItem!=null){
            textViewName.setText(partsItem.getPartsName());
            imageView.setImageResource(partsItem.getPartsIml());
        }
        return convertView;
    }

    private Filter partsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<PartsItem> suggestions = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0){
                suggestions.addAll(partsListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(PartsItem item :partsListFull){
                    if(item.getPartsName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((PartsItem) resultValue).getPartsName();
        }
    };
}
