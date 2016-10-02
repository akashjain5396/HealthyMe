package com.example.abhinay.healthcareapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Doctor extends AppCompatActivity implements View.OnClickListener {
    private static final String REGISTER_URL = Patient.ip+"/healthyme/upload.php";
    public static final String KEY_DISEASE="disease";
    public static final String KEY_CAUSE="cause";
    public static final String KEY_PRESCRIPTION="prescription";
    public static final String KEY_AADHARNUMBER="aadharnumber";

    private EditText disease;
    private EditText cause;
    private EditText prescription;
    private EditText aadharnumber;
    private Button update;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        disease=(EditText)findViewById(R.id.disease);
        cause=(EditText)findViewById(R.id.cause);
        prescription=(EditText)findViewById(R.id.disease);
        aadharnumber=(EditText)findViewById(R.id.aadharnumber);
        update = (Button)findViewById(R.id.update);

        update.setOnClickListener(this);
    }

    private void sendupdate(){
        final String disease1 = disease.getText().toString().trim();
        final String cause1 = cause.getText().toString().trim();
        final String prescription1 = prescription.getText().toString().trim();
        final String aadharnumber1 = aadharnumber.getText().toString().trim();

        progress = new ProgressDialog(this);
        progress.setMessage("Sending...");
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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        Toast.makeText(Doctor.this,response,Toast.LENGTH_LONG).show();

                        if(response.trim().equals("message sent")) {
                            disease.setText("");
                            cause.setText("");
                            prescription.setText("");
                            aadharnumber.setText("");
                            update.setEnabled(true);
                        }
                        else{

                            update.setEnabled(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(Doctor.this,"No connection",Toast.LENGTH_LONG).show();
                        update.setEnabled(true);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_DISEASE,disease1);
                params.put(KEY_CAUSE,cause1);
                params.put(KEY_AADHARNUMBER,aadharnumber1);
                params.put(KEY_PRESCRIPTION,prescription1);



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Doctor.this.getApplicationContext());
        requestQueue.add(stringRequest);

    }



    @Override
    public void onClick(View v) {
        update.setEnabled(false);
        sendupdate();
    }

}
