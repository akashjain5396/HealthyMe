package com.example.abhinay.healthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Register extends AppCompatActivity implements View.OnClickListener {
    private static final String REGISTER_URL = Patient.ip+"/healthyme/register.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_AGE = "age";
    public static final String KEY_AADHARNUMBER = "aadharnumber";
    public static final String KEY_CITY = "city";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_TYPE = "type";

    private ProgressDialog progress;
    private EditText username;
    private EditText age;
    private EditText aadharnumber;
    private EditText city;
    private EditText pincode;
    private EditText type;
    private Button buttonregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        age = (EditText) findViewById(R.id.age);
        aadharnumber = (EditText) findViewById(R.id.aadharnumber);
        city = (EditText) findViewById(R.id.city);
        pincode = (EditText) findViewById(R.id.pincode);
        type = (EditText) findViewById(R.id.type);
        buttonregister = (Button) findViewById(R.id.buttonregister);

        buttonregister.setOnClickListener(this);
    }

    private void registerUser() {
        final String user = username.getText().toString().trim();
        final String age1 = age.getText().toString().trim();
        final String aadharnumber1 = aadharnumber.getText().toString().trim();
        final String city1 = city.getText().toString().trim();
        final String pincode1 = pincode.getText().toString().trim();
        final String type1 = type.getText().toString().trim();

        progress = new ProgressDialog(this);
        progress.setMessage("Registering...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(10);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);

                    } catch (InterruptedException e) {
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
                        Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        if (response.trim().equals("successfully registered")) {
                            openLogin();
                        } else {
                            buttonregister.setEnabled(true);
                        }

                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                        buttonregister.setEnabled(true);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, user);
                params.put(KEY_AGE, age1);
                params.put(KEY_AADHARNUMBER, aadharnumber1);
                params.put(KEY_CITY, city1);
                params.put(KEY_PINCODE, pincode1);
                params.put(KEY_TYPE, type1);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void openLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v==buttonregister) {
            buttonregister.setEnabled(false);
                registerUser();
        }

    }
}

