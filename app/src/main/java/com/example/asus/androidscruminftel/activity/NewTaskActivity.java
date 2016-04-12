package com.example.asus.androidscruminftel.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.connection.DeleteTaskHttp;
import com.example.asus.androidscruminftel.connection.PostHttp;
import com.example.asus.androidscruminftel.model.Project;
import com.example.asus.androidscruminftel.model.Tarea;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String urlnew = "http://192.168.1.148:8080/newTask/";
    private String urledit = "http://192.168.1.148:8080/editTask/";
    private String urldelete = "http://192.168.1.148:8080/deleteTask/";
    private Project project;
    private String selState;
    Tarea tarea;

    private Spinner spinner;
    String tittle;
    String content;
    String id_task;
    String time;
    String date;

    TextInputEditText textName;
    TextInputEditText textDescript;
    TextInputEditText fechaInicio;
    TextInputEditText totalTime;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        android.support.v7.app.ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);

        tarea = new Tarea();

        textName = (TextInputEditText) findViewById(R.id.input_tittleTask);
        textDescript =  (TextInputEditText) findViewById(R.id.input_descriptionTask);
        fechaInicio =  (TextInputEditText) findViewById(R.id.input_dateIni);
        totalTime =  (TextInputEditText) findViewById(R.id.input_time);

        bundle = this.getIntent().getExtras();
        if(bundle != null){
            tittle = bundle.getString("tittle");
            content = bundle.getString("content");
            id_task = bundle.getString("idTask");
            time = bundle.getString("time");
            date = bundle.getString("date");

            String[] split = time.split(": ");
            time = split[1];

            split = date.split(": ");
            date = split[1];

            EditText input_tittle = (EditText) findViewById(R.id.input_tittleTask);
            EditText input_content = (EditText) findViewById(R.id.input_descriptionTask);
            EditText input_time = (EditText) findViewById(R.id.input_time);
            EditText input_date = (EditText) findViewById(R.id.input_dateIni);

            input_tittle.setText(tittle);
            input_content.setText(content);
            input_time.setText(time);
            input_date.setText(date);

            this.setTitle(tittle);
        }

        project = AndroidScrumINFTELActivity.getInstance().getProject();
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> state = new ArrayList<>();
        for (int i=0; i<project.getEstados().size();i++){
            state.add(i,project.getEstados().get(i).getNombre());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(bundle == null) {
            getMenuInflater().inflate(R.menu.menu_create, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        return true;
    }

    private void saveTask() throws JSONException {
        String url;
        if (bundle == null){
            url = urlnew + AndroidScrumINFTELActivity.getInstance().getProject().getId();
        }else{
            url = urledit + id_task;
        }
        tarea.setNombre_tarea(textName.getText().toString());
        tarea.setDescripcion(textDescript.getText().toString());
        tarea.setFecha_inicio(fechaInicio.getText().toString());
        tarea.setTiempo_estimado(totalTime.getText().toString());

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nombre_tarea", tarea.getNombre_tarea());
        jsonObject.put("descripcion", tarea.getDescripcion());
        jsonObject.put("tiempo_estimado", tarea.getTiempo_estimado());
        jsonObject.put("nombre_usuario", AndroidScrumINFTELActivity.getInstance().getEmail());
        jsonObject.put("fecha_inicio", tarea.getFecha_inicio());
        jsonObject.put("fichero","eo");
        jsonObject.put("nombre_fichero","nombre._fich");
        jsonObject.put("estado", selState);

       new PostHttp(this).execute(url, jsonObject.toString());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.action_save:

                try {
                    saveTask();
                    Intent intent = new Intent(this,MyProjectsActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            case R.id.action_delete:
                AlertDialog alertDialog = alert();
                alertDialog.show();
                return true;

            case R.id.action_download:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.setSelection(position);
        selState = (String) spinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public AlertDialog alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea borrar la tarea?")
                .setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String url = urldelete + id_task;
                        new DeleteTaskHttp(getContext()).execute(url);
                        Intent intent = new Intent(getContext(), MyProjectsActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }

    public Context getContext(){
        return this;
    }
}
