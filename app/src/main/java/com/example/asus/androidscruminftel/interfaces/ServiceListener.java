package com.example.asus.androidscruminftel.interfaces;

import com.example.asus.androidscruminftel.model.ProjectChat;

import java.util.ArrayList;

/**
 * Created by Asus on 08/04/2016.
 */
public interface ServiceListener {
    public void onServiceResponse(ArrayList<ProjectChat> response);
    public void onServiceNoResponse(String result);
}
