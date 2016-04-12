package com.example.asus.androidscruminftel.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Asus on 06/04/2016.
 */
public class Project implements Parcelable {
    String id;
    String name;
    String description;
    int idProject;
    String idAdmin;
    String dateStart;
    String[] chat;
    ArrayList<String> emailUser;
    ArrayList<State> estados;
    ArrayList<Tarea> tasks;
    public Project(){

    }

    protected Project(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        idProject = in.readInt();
        idAdmin = in.readString();
        dateStart = in.readString();
        chat = in.createStringArray();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };


    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getIdProject (){
        return idProject;
    }

    public void setIdProject(int idProject){
        this.idProject = idProject;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String[] getChat() {
        return chat;
    }

    public void setChat(String[] chat) {
        this.chat = chat;
    }

    public ArrayList<State> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<State> estados) {
        this.estados = estados;
    }

    public ArrayList<Tarea> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Tarea> tasks) {
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(ArrayList<String> emailUser) {
        this.emailUser = emailUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (idProject != project.idProject) return false;
        if (idAdmin != project.idAdmin) return false;
        if (!id.equals(project.id)) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (description != null ? !description.equals(project.description) : project.description != null)
            return false;
        if (dateStart != null ? !dateStart.equals(project.dateStart) : project.dateStart != null)
            return false;
        if (!Arrays.equals(chat, project.chat)) return false;
        if (estados != null ? !estados.equals(project.estados) : project.estados != null)
            return false;
        return !(tasks != null ? !tasks.equals(project.tasks) : project.tasks != null);

    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idProject=" + idProject +
                ", idAdmin=" + idAdmin +
                ", dateStart=" + dateStart +
                ", chat=" + Arrays.toString(chat) +
                ", estados=" + estados +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(idProject);
        dest.writeString(idAdmin);
        dest.writeString(dateStart);
        dest.writeStringArray(chat);
    }


    public static Project fromJSON(String response) throws JSONException {

        Project proyect = new Project();
        JSONObject jsonObject = new JSONObject(response);

        proyect.setName(jsonObject.getString("nombre"));
        proyect.setDescription(jsonObject.getString("descripcion"));

        ArrayList<String> usuarios = (ArrayList<String>) jsonObject.get("usuarios");
        proyect.setEmailUser(usuarios);

        JSONArray jsonArray = jsonObject.getJSONArray("estados");
        ArrayList<State> states = new ArrayList<>();
        for (int i =0; i<jsonArray.length();i++){
            State state = new State();
            state.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
            state.setPosicion(jsonArray.getJSONObject(i).getString("posicion"));
            states.add(i,state);
        }

        JSONArray taskArray = jsonObject.getJSONArray("tareas");
        ArrayList<Tarea> tareas  = new ArrayList<>();
        for (int i =0; i<taskArray.length();i++){
            Tarea tarea = new Tarea();
            tarea.setNombre_tarea(taskArray.getJSONObject(i).getString("nombre_tarea"));
            tarea.setDescripcion(taskArray.getJSONObject(i).getString("descripcion"));
            tarea.setEstado_tarea(taskArray.getJSONObject(i).getString("estado"));
            tarea.setFecha_inicio(taskArray.getJSONObject(i).getString("fecha_inicio"));
            tarea.setTiempo_estimado(taskArray.getJSONObject(i).getString("tiempo_estimado"));
            tarea.setNombre_usuario(taskArray.getJSONObject(i).getString("nombre_usuario"));
            tarea.setId_tarea(taskArray.getJSONObject(i).getString("_id"));

            tareas.add(i,tarea);

        }

        proyect.setTasks(tareas);
        proyect.setIdAdmin(jsonObject.getString("admin"));
        proyect.setId(jsonObject.getString("_id"));
        proyect.setEstados(states);
        proyect.setDateStart("date");

        return proyect;
    }
}
