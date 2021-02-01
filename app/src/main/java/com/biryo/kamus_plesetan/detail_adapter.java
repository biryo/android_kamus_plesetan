package com.biryo.kamus_plesetan;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class detail_adapter extends RecyclerView.Adapter<detail_adapter.ViewHolder>{
    detail_data[] data ;
    Context context;

    public detail_adapter(detail_data[] data, Detail activity) {
        this.data = data;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.detail_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final detail_data detail = data[position];
        holder.textViewName.setText(detail.getKata());
        holder.textViewMakna.setText(Html.fromHtml(detail.getMakna()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewMakna;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewName = itemView.findViewById(R.id.kata);
            textViewMakna = itemView.findViewById(R.id.makna);
        }
    }
}

