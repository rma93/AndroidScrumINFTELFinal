package com.example.asus.androidscruminftel.service;

import android.content.Context;
import android.util.Pair;

import com.example.asus.androidscruminftel.connection.HttpRequest;
import com.example.asus.androidscruminftel.connection.HttpTask;
import com.example.asus.androidscruminftel.interfaces.ResponseListener;
import com.example.asus.androidscruminftel.interfaces.ServiceListenerProjects;
import com.example.asus.androidscruminftel.model.Project;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inftel18 on 7/4/16.
 */
public class ProyectsService implements ResponseListener{
    private ServiceListenerProjects listener;
    private Context context;
   // private String url = "http://192.168.1.136:8080/AppInftelScrum/webresources/entity.usuyproscrum/projects/jjaldoasenjo@gmail.com";
    //private String url = "http://secureuma.no-ip.org:8080/getProjectByUser/juan";
   private String url1 = "http://192.168.1.148:8080/getProjectByUser/";

    public ProyectsService(ServiceListenerProjects listener, Context ap) {
        this.listener = listener;
        this.context=ap;
    }

    public void getProjects(String email) {
        String url= url1 + email;
        HttpRequest httpRequest = new HttpRequest(HttpRequest.GET,url,null);


        new HttpTask(this,"getProyects").execute(httpRequest);

    }


    @Override
    public void onResponse(Pair<String, String> response) {
        if (response.first.equals("getProyects")) {
            parseProyects(response.second);
        }
    }

    private void parseProyects(String response) {
        List<Project> projectsList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i=0; i<jsonArray.length(); i++) {
                Project proyect = Project.fromJSON(jsonArray.getString(i));
                projectsList.add(proyect);
            }
            Pair p = new Pair("getProyects", projectsList);
            listener.onListResponse(p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
