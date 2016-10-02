package com.example.abhinay.healthcareapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class patienthistory extends AppCompatActivity implements View.OnClickListener{

    public static final String history_url=Patient.ip + "/healthyme/getdata.php";
    private Button gethistory;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthistory);

        gethistory=(Button) findViewById(R.id.buttonget);
        gethistory.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.listview);
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(history_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(patienthistory.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // private void showJSON(String json){
    //     ParseJSON pj = new ParseJSON(json);
    //     pj.parseJSON();
    //     CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
    //     listView.setAdapter(cl);
    // }

    public void onClick(View v){
        if(v == gethistory)
            sendRequest();
    }
}
