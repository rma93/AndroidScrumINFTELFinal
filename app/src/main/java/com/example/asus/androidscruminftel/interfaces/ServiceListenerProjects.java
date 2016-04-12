package com.example.asus.androidscruminftel.interfaces;

import android.util.Pair;

import java.util.List;

/**
 * Created by csalas on 28/3/16.
 */
public interface ServiceListenerProjects {
    public void onObjectResponse(Pair<String, ?> response);
    public void onListResponse(Pair<String, List<?>> response);
}
