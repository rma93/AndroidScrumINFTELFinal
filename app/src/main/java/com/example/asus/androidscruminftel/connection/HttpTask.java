package com.example.asus.androidscruminftel.connection;

import android.os.AsyncTask;
import android.util.Pair;

import com.example.asus.androidscruminftel.interfaces.ResponseListener;


/**
 * Created by csalas on 23/3/16.
 */
public class HttpTask extends AsyncTask<HttpRequest, Void, String> {
     private ResponseListener listener = null;

    private String method;

    public HttpTask(ResponseListener listener, String method) {
        this.listener = listener;
        this.method = method;
    }

    @Override
    protected String doInBackground(HttpRequest... requests) {
        return requests[0].execute();
    }

    @Override
    protected void onPostExecute(String response) {
        if (listener != null) {
            Pair<String, String> resp = new Pair<>(method, response);
            listener.onResponse(resp);
        }

    }
}
