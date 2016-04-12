package com.example.asus.androidscruminftel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.model.Project;

import java.util.List;

/**
 * Created by inftel18 on 7/4/16.
 */
public class RecyclerViewProyectsAdapter extends RecyclerView.Adapter<RecyclerViewProyectsAdapter.ViewHolder> {
    private List<Project> proyects;
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView descripcion;
        public TextView date;

        public ViewHolder(View v){
            super(v);
            name =(TextView) v.findViewById(R.id.name);
            descripcion= (TextView) v.findViewById(R.id.descripcion);
            date=(TextView) v.findViewById(R.id.date);
        }

    }

    public RecyclerViewProyectsAdapter(List<Project> proyects) {
        this.proyects = proyects;

    }
    @Override
    public RecyclerViewProyectsAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.proyect_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(proyects.get(position).getName());
        holder.descripcion.setText(proyects.get(position).getDescription());
        holder.date.setText(proyects.get(position).getDateStart());

    }

    @Override
    public int getItemCount() {
        return proyects.size();
    }




}
