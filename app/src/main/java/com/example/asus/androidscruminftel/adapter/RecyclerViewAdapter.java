package com.example.asus.androidscruminftel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.model.ProjectChat;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<ProjectChat> chatList;
    private String url;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public TextView mDescView;
        public ImageView mImageView;
        public TextView mDistanceView;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.projectId);
            mDescView = (TextView) v.findViewById(R.id.projectname);
            mImageView = (ImageView) v.findViewById(R.id.icon);
        }

    }


    public RecyclerViewAdapter(Context context,ArrayList<ProjectChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(final ViewHolder holder, int position){
        //holder.mTextView.setText(chatList.get(position).getProjectId());
        holder.mDescView.setText(chatList.get(position).getProjectName());
    }

    @Override
    public int getItemCount(){
        return chatList.size();
    }



}