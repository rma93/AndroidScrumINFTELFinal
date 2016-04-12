package com.example.asus.androidscruminftel.service;

import android.os.AsyncTask;

import com.example.asus.androidscruminftel.interfaces.ServiceListener;
import com.example.asus.androidscruminftel.model.ProjectChat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Asus on 08/04/2016.
 */
public class ChatListService {

    ServiceListener listener;

    public ChatListService() {
    }

    public ChatListService(ServiceListener listener){
        this.listener = listener;
    }

    public void getListChat(String user){
        String url = "http://192.168.1.148:8080/getProjectByUser/"+user;
        new GetChatListTask().execute(url);

    }

    //PETICION GET
    private class GetChatListTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {

            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page.URL may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<ProjectChat> chatList = new ArrayList<>();

            JSONArray json = null;
            try {
                json = new JSONArray(result);
                for(int i = 0; i<json.length();i++){

                    try {
                        ProjectChat pc = ProjectChat.fromJSON(json.getString(i));
                        chatList.add(pc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(!chatList.isEmpty()){
                listener.onServiceResponse(chatList);
            }else{
                listener.onServiceNoResponse(result);
            }

        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(1000 /*miliseconds*/);
                conn.setConnectTimeout(1500 /*miliseconds*/);
                conn.setRequestMethod("GET");

                conn.setDoInput(true);
                //Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();


                String contentAsString = readIt(is, len);


                return contentAsString;

            } finally {
                if (is != null) {
                    is.close();
                }
            }

        }

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");

            char[] buffer = new char[len];

            reader.read(buffer);
            return new String(buffer);
        }
    }
}
