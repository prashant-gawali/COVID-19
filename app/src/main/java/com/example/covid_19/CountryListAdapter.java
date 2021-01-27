package com.example.covid_19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19.model.data;

import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> implements Filterable {
    Context mContext;
    List<data> mDataList;
    List<data> countryListFilter;

    public CountryListAdapter(Context mContext, List<data> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.countryListFilter = mDataList;
    }

    @NonNull
    @Override
    public CountryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = countryListFilter.get(position).getCountry();
        String flag = countryListFilter.get(position).getCountryInfo().getFlag();

        holder.countryName.setText(name);
        Glide.with(mContext).load(flag).into(holder.countryFlag);
    }


    @Override
    public int getItemCount() {
        return countryListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequence = constraint.toString();
                if (charSequence.isEmpty()) {
                    countryListFilter = mDataList;
                } else {
                    List<data> filterList = new ArrayList<>();
                    for (data data:mDataList){
                        if (data.getCountry().toLowerCase().contains(charSequence.toLowerCase())){
                            filterList.add(data);
                        }
                    }
                    countryListFilter=filterList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=countryListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryListFilter=(ArrayList<data>)results.values;
                notifyDataSetChanged();

            }
        };
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countryName;
        ImageView countryFlag;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryName);
            countryFlag = itemView.findViewById(R.id.countryFlag);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, DetailedActivity.class);
            intent.putExtra("CountryName", countryListFilter.get(getAdapterPosition()).getCountry());
            intent.putExtra("CountryFlag", countryListFilter.get(getAdapterPosition()).getCountryInfo().getFlag());
            mContext.startActivity(intent);

        }
    }
}
