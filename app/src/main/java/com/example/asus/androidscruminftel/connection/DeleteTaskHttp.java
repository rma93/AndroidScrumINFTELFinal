package com.example.asus.androidscruminftel.connection;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by macbookpro on 29/3/16.
 */
public class DeleteTaskHttp extends AsyncTask<String, Void, String> {

    private final Context context;


    public DeleteTaskHttp(Context c){
        this.context = c;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... param) {

        String result="";;
        HttpURLConnection con;

        try {


            URL url = new URL(param[0]);
            con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("DELETE");

            int responseCode = con.getResponseCode();

            result = "\nSending 'DELETE' request to URL : " + url + "\nResponse Code : " + responseCode;

            System.out.println("\nSending 'DELETE' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);




        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String result) {
    }
}
