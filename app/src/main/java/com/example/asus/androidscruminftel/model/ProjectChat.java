package com.example.asus.androidscruminftel.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aitorpagan on 8/4/16.
 */
public class ProjectChat implements Parcelable{

    private String projectId;
    private String projectName;

    protected ProjectChat(Parcel in) {
        projectId = in.readString();
        projectName = in.readString();
    }

    public static final Creator<ProjectChat> CREATOR = new Creator<ProjectChat>() {
        @Override
        public ProjectChat createFromParcel(Parcel in) {
            return new ProjectChat(in);
        }

        @Override
        public ProjectChat[] newArray(int size) {
            return new ProjectChat[size];
        }
    };

    public ProjectChat() {

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(projectId);
        dest.writeString(projectName);
    }

    public static ProjectChat fromJSON(String object) {

        ProjectChat pc = new ProjectChat();

        try {
            JSONObject json = new JSONObject(object);
            pc.setProjectId(json.getString("_id"));
            pc.setProjectName(json.getString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pc;
    }
}
