package com.example.asus.androidscruminftel;

import android.app.Application;

import com.example.asus.androidscruminftel.model.Project;
import com.example.asus.androidscruminftel.model.Tarea;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class AndroidScrumINFTELActivity extends Application {

    private GoogleApiClient mGoogleApiClient;
    private static AndroidScrumINFTELActivity mInstance;
    private String userName;
    private String email;
    private Project project;
    private String photoUrl;
    private Tarea selected_task;


    public Tarea getSelected_task() {
        return selected_task;
    }

    public void setSelected_task(Tarea selected_task) {
        this.selected_task = selected_task;
    }

    public static ImageLoader imageLoader;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    public GoogleApiClient getmGoogleApiClient() {

        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public static ImageLoader getImageLoader(){
        return imageLoader;
    }

    public static void setImageLoader(ImageLoader imageLoader) {
        AndroidScrumINFTELActivity.imageLoader = imageLoader;
    }

    public static synchronized AndroidScrumINFTELActivity getInstance() {
        return mInstance;
    }
}
