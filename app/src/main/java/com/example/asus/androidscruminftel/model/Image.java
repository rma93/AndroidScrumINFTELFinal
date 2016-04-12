package com.example.asus.androidscruminftel.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Image implements Parcelable{
    private String id;
    private String url;

    public Image() {
    }

    public Image(String id, String url) {
        this.id = id;
        this.url = url;
    }

    protected Image(Parcel in) {
        id = in.readString();
        url = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Image fromJSON(String response) throws JSONException {
        Image image = new Image();
        JSONObject jsonObject = new JSONObject(response);
        image.setId(jsonObject.getString("idImage"));
        image.setUrl(jsonObject.getString("urlImage"));
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
    }
}
