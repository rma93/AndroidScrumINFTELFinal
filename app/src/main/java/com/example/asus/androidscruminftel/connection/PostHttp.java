package com.example.asus.androidscruminftel.connection;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by macbookpro on 29/3/16.
 */
public class PostHttp extends AsyncTask<String, Void, String> {

    private final Context context;


    public PostHttp(Context c){
        this.context = c;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... param) {

        String result="";
        StringBuilder sb = new StringBuilder();
        HttpURLConnection con;

        try {


            URL url = new URL(param[0]);
            con = (HttpURLConnection)url.openConnection();


            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");

            // Get JSONObject here
            String jsonParam = param[1];
            System.out.println("ESTOY AKI 3");

            // SUBIR JSON
            OutputStreamWriter out = new   OutputStreamWriter(con.getOutputStream());
            System.out.println("ESTOY AKI 4");
            out.write(jsonParam);
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();

            result = "\nSending 'POST' request to URL : " + url + "\nResponse Code : " + responseCode;

            System.out.println("\nSending 'POST' request to URL : " + url);
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
