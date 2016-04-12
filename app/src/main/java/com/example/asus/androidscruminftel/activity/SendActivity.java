package com.example.asus.androidscruminftel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.connection.SendMail;


public class SendActivity extends AppCompatActivity {
    private String[] emails= {"bobolouna1981@gmail.com","bobolouna12@gmail.com" ,"bobolouna@hotmail.com","rubenmena93@hotmail.es","jjaldoasenjo@gmail.com","aitor.p.n@gmail.com"};
    private String email;
    private Button buttonSend;
    private AutoCompleteTextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,emails);
        textView =(AutoCompleteTextView) findViewById(R.id.autoEmail);
        textView.setThreshold(1);
        textView.setAdapter(adapter);



        buttonSend =  (Button) findViewById(R.id.send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!emails.equals("")){
                    sendMail();
                }

            }
        });

    }

   public void sendMail(){

       email= textView.getText().toString();
       final SendMail sendMail = new SendMail(this, email);
       sendMail.execute();

   }
}
