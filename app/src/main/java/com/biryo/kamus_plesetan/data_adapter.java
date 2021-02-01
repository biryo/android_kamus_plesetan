package com.biryo.kamus_plesetan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class data_adapter extends RecyclerView.Adapter<data_adapter.ViewHolder>{
    data_plesetan[] data ;
    Context context;
    String fitur;

    public data_adapter(data_plesetan[] data, result_plesetan activity,String fitur) {
        this.data = data;
        this.context = activity;
        this.fitur = fitur;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final data_plesetan datalist = data[position];
        holder.textViewName.setText(datalist.getKata());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Detail.class);
                i.putExtra("query", datalist.getKata());
                i.putExtra("fitur", fitur);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewName = itemView.findViewById(R.id.list);
        }
    }
}
