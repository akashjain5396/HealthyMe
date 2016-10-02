package com.example.abhinay.healthcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Patient extends AppCompatActivity implements View.OnClickListener{

    public static final String ip="http://172.20.153.43";  //ip of the server
    public static final String LOGIN_URL = ip+"/healthyme/patientsignin.php";

    public static final String KEY_USERNAME="username";
    public static final String KEY_AADHARNUMBER="aadharnumber";

    public static final String PREFS_NAME="MyPrefsFile";
    public static String anumber;
    private String username;

    private ProgressDialog progress;

    private EditText name;
    private EditText aadharnumber;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(Patient.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if (hasLoggedIn) {
            openOptions();
        }

        setContentView(R.layout.activity_patient);

        name = (EditText)findViewById(R.id.name);
        aadharnumber = (EditText)findViewById(R.id.aadharnumber);

        login = (Button)findViewById(R.id.buttonlogin);

        login.setOnClickListener(this);


    }

    private void userLogin() {
        username = name.getText().toString().trim();
         anumber= aadharnumber.getText().toString().trim();

        progress = new ProgressDialog(this);
        progress.setMessage("Signing in...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(10);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);


                    }
                    catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        if(response.trim().equals("success")) {
                            SharedPreferences settings =getSharedPreferences(Patient.PREFS_NAME,0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putBoolean("hasLoggedIn",true);
                            editor.putString("user",username);

                            editor.commit();
                            openOptions();
                            login.setEnabled(true);
                        }

                        else{


                            Toast.makeText(Patient.this,response,Toast.LENGTH_LONG).show();
                            login.setEnabled(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(Patient.this,"No connection",Toast.LENGTH_LONG ).show();
                        login.setEnabled(true);
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_AADHARNUMBER,anumber);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openOptions(){
        Intent intent = new Intent(this,patienthistory.class);
        startActivity(intent);
        finish();
    }


    public void onClick(View v){
        if (v == login)
            userLogin();
    }

}
