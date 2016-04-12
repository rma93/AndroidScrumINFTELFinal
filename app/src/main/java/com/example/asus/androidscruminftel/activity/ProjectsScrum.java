package com.example.asus.androidscruminftel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.adapter.ExpandableListAdapter;
import com.example.asus.androidscruminftel.model.Project;
import com.example.asus.androidscruminftel.model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class ProjectsScrum extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<String> state;
    ArrayList<Tarea> tasks;
    Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_scrum);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            project = (Project) bundle.get("project");
        }

        project = AndroidScrumINFTELActivity.getInstance().getProject();
        this.setTitle(project.getName());

        state = new ArrayList<>();
        for (int i=0; i<project.getEstados().size();i++){
            state.add(i,project.getEstados().get(i).getNombre());
        }

        tasks = new ArrayList<>();
        for (int t =0; t<project.getTasks().size();t++){
            tasks.add(t,project.getTasks().get(t));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),NewTaskActivity.class);
                startActivity(intent);
            }
        });

        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager(), getActivityProject());
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void showToast(String cadena) {
        Toast toast = Toast.makeText(this, cadena, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    //----------------------- subClase ---------------------------------
    @SuppressLint("ValidFragment")
    public class DesignDemoFragment extends Fragment {
        private static final String TAB_POSITION = "tab_position";
        private ProjectsScrum p;

        public DesignDemoFragment(){

        }

        public DesignDemoFragment(ProjectsScrum p) {
            this.p = p;
        }

        public DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment(p);
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int position = args.getInt(TAB_POSITION);

            String s = state.get(position);
            List<ExpandableListAdapter.Item> data = new ArrayList<>();
            for (Tarea t: tasks) {
                if (s.equals(t.getEstado_tarea())){
                    ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, t.getNombre_tarea());
                    places.invisibleChildren = new ArrayList<>();
                    places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, t.getDescripcion()));
                    places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Tiempo Estimado: " + t.getTiempo_estimado()));
                    places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Fecha de Inicio: " + t.getFecha_inicio()));
                    places.invisibleChildren.add( new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, t.getId_tarea()));
                    data.add(places);
                }
            }

            View rootView = inflater.inflate(R.layout.fragment_project_view, container, false);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            ExpandableListAdapter exp = new ExpandableListAdapter(data);
            exp.setMyProject(p);

            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
            rv.setHasFixedSize(true);
            rv.setAdapter(exp);
            rv.setLayoutManager(llm);


            return rootView;
        }
    }

    //-----------SubClase--------------------

    class DesignDemoPagerAdapter extends FragmentPagerAdapter {
        ProjectsScrum p;

        public DesignDemoPagerAdapter(FragmentManager fm,ProjectsScrum p) {
            super(fm);
            this.p = p;
        }

        @Override
        public Fragment getItem(int position) {
            DesignDemoFragment d = new DesignDemoFragment(p);
            return d.newInstance(position);
        }

        @Override
        public int getCount() {
            return state.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return state.get(position);
        }
    }

    public Context getContext(){
        return getApplicationContext();
    }

    public ProjectsScrum getActivityProject(){
        return this;
    }
}
