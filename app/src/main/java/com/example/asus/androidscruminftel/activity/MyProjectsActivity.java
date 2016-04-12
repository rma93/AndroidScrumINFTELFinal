package com.example.asus.androidscruminftel.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.fragment.FragmentMyProyects;
import com.example.asus.androidscruminftel.fragment.LoadingFragment;
import com.example.asus.androidscruminftel.interfaces.ImageLoaderListener;
import com.example.asus.androidscruminftel.interfaces.ServiceListenerProjects;
import com.example.asus.androidscruminftel.model.Project;
import com.example.asus.androidscruminftel.service.ImageLoaderService;
import com.example.asus.androidscruminftel.service.ProyectsService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MyProjectsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ServiceListenerProjects, ImageLoaderListener {


    private ProyectsService proyectsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        proyectsService = new ProyectsService(this,getApplicationContext());

        proyectsService.getProjects(AndroidScrumINFTELActivity.getInstance().getEmail());


        LoadingFragment loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_proyects, loadingFragment).commit();


         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        AndroidScrumINFTELActivity.getInstance().setmGoogleApiClient(new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build());

        ImageLoaderService imageLoaderService = new ImageLoaderService(this,this);
        imageLoaderService.execute(AndroidScrumINFTELActivity.getInstance().getPhotoUrl());
    }

    @Override
    public void onObjectResponse(Pair<String, ?> response) {

    }

    @Override
    public void onListResponse(Pair<String, List<?>> response) {
        if (response.first.equals("getProyects")) {
            showProyectListFragment((ArrayList<Project>) response.second);
        }

    }

    public void showProyectListFragment(ArrayList<Project> myProyectsList){
        FragmentMyProyects fragmentMyProyects = new FragmentMyProyects();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myProyects", myProyectsList);

        fragmentMyProyects.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_proyects, fragmentMyProyects).commit();

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MyProjectsActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MyProjectsActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_projects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_project) {
            Intent intent = new Intent(this,NewProjectActivity.class);
            startActivity(intent);

        }else if (id == R.id.chatlist) {
            Intent intent = new Intent(this,ListChatActivity.class);
            startActivity(intent);

        }else if (id == R.id.exit) {


            Auth.GoogleSignInApi.signOut(AndroidScrumINFTELActivity.getInstance().getmGoogleApiClient());

            AndroidScrumINFTELActivity.getInstance().setUserName("");
            AndroidScrumINFTELActivity.getInstance().setEmail("");

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", "");
            editor.putString("username", "");
            editor.putString("image","");
            editor.commit();

            Intent logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onStart() {
        AndroidScrumINFTELActivity.getInstance().getmGoogleApiClient().connect();
        super.onStart();
    }

    public void onStop() {
        AndroidScrumINFTELActivity.getInstance().getmGoogleApiClient().disconnect();
        super.onStop();
    }

    @Override
    public void onImageLoaded(Drawable drawable) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        RoundedImageView imageView = (RoundedImageView) navigationView.getHeaderView(0).findViewById(R.id.profileImage);
        imageView.setImageDrawable(drawable);
        TextView nameView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userNameSideBar);
        TextView mailView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmailSideBar);
        nameView.setText(AndroidScrumINFTELActivity.getInstance().getUserName());
        mailView.setText(AndroidScrumINFTELActivity.getInstance().getEmail());
    }

}
