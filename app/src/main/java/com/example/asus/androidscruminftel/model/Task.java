package com.example.asus.androidscruminftel.model;

/**
 * Created by RMA on 11/04/2016.
 */
public class Task {
    private String id;
    private String tittle;
    private String description;
    private String time;
    private String date;
    private String state;

    public Task(String id, String tittle, String description, String time, String date, String state) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.time = time;
        this.date = date;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (tittle != null ? !tittle.equals(task.tittle) : task.tittle != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null)
            return false;
        if (time != null ? !time.equals(task.time) : task.time != null) return false;
        if (date != null ? !date.equals(task.date) : task.date != null) return false;
        return !(state != null ? !state.equals(task.state) : task.state != null);

    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
