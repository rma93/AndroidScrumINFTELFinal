package com.example.asus.androidscruminftel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.connection.PostHttp;
import com.example.asus.androidscruminftel.model.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProjectActivity extends AppCompatActivity {

    //String url = "http://192.168.1.136:8080/AppInftelScrum/webresources/entity.proyectoscrum";
    String url = "http://192.168.1.148:8080/newProject";
    //String url = "http://secureuma.no-ip.org:8080/newProject";

    private Project project;
    private Spinner spinner1;
    private TextView textView;
    private Button btnSubmit;
    private int nestados = 1;
    private String[] arrText;
    private String[] arrTemp;
    private ArrayList<String> arrText2 = new ArrayList<>();
    private boolean vacio=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        android.support.v7.app.ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);

        arrText = new String[nestados];
        for(int i=0; i<nestados;i++){
            arrText[i]= "Estado 1";
        }

        arrTemp = new String[arrText.length];
        MyListAdapter myListAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myListAdapter);


        project = new Project();



        addListenerOnSpinnerItemSelection();
        //addListenerOnButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        //Mediante getItem se obtiene el vlaor del botn pulsado
        switch (id){
            case R.id.action_save:

                TextInputEditText textName = (TextInputEditText) findViewById(R.id.input_nameProject);
                project.setName(textName.getText().toString());

                TextInputEditText textDescription = (TextInputEditText) findViewById(R.id.input_descriptionProject);
                project.setDescription(textDescription.getText().toString());


                for(int i=0;i<nestados;i++){
                    arrText2.add(arrTemp[i]);
                    if(arrTemp[i]==null && vacio==false){
                        vacio=true;
                    }else if(arrTemp[i].equals("") && vacio==false){
                        vacio=true;
                    }
                }



                if (textName.getText().toString().equals("") | textDescription.getText().toString().equals("") |
                        vacio==true){
                    CharSequence text = "Debes completar todos los campos!";
                    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                    vacio=false;
                }else {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("nombre", project.getName());
                        jsonObject.put("descripcion", project.getDescription());
                        jsonObject.put("admin", AndroidScrumINFTELActivity.getInstance().getEmail());


                        List<String> usuarios = new ArrayList<>();
                        usuarios.add(0,AndroidScrumINFTELActivity.getInstance().getEmail());
                        jsonObject.put("usuarios",AndroidScrumINFTELActivity.getInstance().getEmail());




                        JSONArray state = new JSONArray();
                        for (int i = 0; i < nestados; i++) {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("nombre", arrTemp[i]);
                            jsonObject1.put("posicion", i);
                            state.put(i, jsonObject1);

                        }

                        jsonObject.put("estados", state);
                        jsonObject.put("chat", "");
                        jsonObject.put("id_proyecto", 1);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    new PostHttp(getContext()).execute(url, jsonObject.toString());

                    Intent intent = new Intent(this, MyProjectsActivity.class);
                    startActivity(intent);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            nestados=Integer.parseInt(parent.getItemAtPosition(pos).toString());

            arrText = new String[nestados];
            for(int i=0; i<nestados;i++){
                arrText[i]= "Estado " + (i+1);


            }


            arrTemp = new String[arrText.length];
            MyListAdapter myListAdapter = new MyListAdapter();
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(myListAdapter);

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if(arrText != null && arrText.length != 0){
                return arrText.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arrText[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = NewProjectActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.listview_list, null);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.editText1.setText(arrTemp[position]);
            holder.editText1.setHint(arrText[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;
        }
    }

    public Context getContext(){
        return getApplicationContext();
    }

}

