package com.example.asus.androidscruminftel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.activity.MyProjectsActivity;
import com.example.asus.androidscruminftel.activity.ProjectsScrum;
import com.example.asus.androidscruminftel.adapter.RecyclerViewProyectsAdapter;
import com.example.asus.androidscruminftel.interfaces.ServiceListenerProjects;
import com.example.asus.androidscruminftel.model.Project;

import java.util.ArrayList;
import java.util.List;


public class FragmentMyProyects extends Fragment implements ServiceListenerProjects {
    private View view;
    private List<Project>  myProyects;
    private RecyclerView recyclerView;
    private RecyclerViewProyectsAdapter adapterRecyclerView;



    public FragmentMyProyects() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_my_proyects, container, false);
        Bundle bundle = getArguments();
        myProyects = bundle.getParcelableArrayList("myProyects");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapterRecyclerView = new RecyclerViewProyectsAdapter(myProyects);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapterRecyclerView);

        adapterRecyclerView.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new MyProjectsActivity.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new MyProjectsActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Project project = myProyects.get(position);

                Intent intent = new Intent(getActivity().getApplicationContext(), ProjectsScrum.class);
                //intent.putExtra("project",project);
                AndroidScrumINFTELActivity.getInstance().setProject(project);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));
        return  view;
    }






    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getProyects")) {
            this.myProyects.clear();
            this.myProyects.addAll((ArrayList<Project>) response.second);
            adapterRecyclerView.notifyDataSetChanged();
            recyclerView.scrollToPosition(myProyects.size() - 1);
        }
    }


}
