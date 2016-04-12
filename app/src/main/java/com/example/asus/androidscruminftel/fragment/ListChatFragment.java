package com.example.asus.androidscruminftel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.activity.ChatActivity;
import com.example.asus.androidscruminftel.adapter.RecyclerItemClickListener;
import com.example.asus.androidscruminftel.adapter.RecyclerViewAdapter;
import com.example.asus.androidscruminftel.model.ProjectChat;

import java.util.ArrayList;


public class ListChatFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ProjectChat> chatList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        Bundle bundle = getArguments();
        chatList = bundle.getParcelableArrayList("chatList");


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this.getContext(), chatList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
          @Override
            public void onItemClick(View view, int position) {

              Intent intent = new Intent(getContext(), ChatActivity.class);
              intent.putExtra(String.valueOf(R.string.projectId),chatList.get(position).getProjectId());
              startActivity(intent);
            }
        }));

        return view;
    }
}
