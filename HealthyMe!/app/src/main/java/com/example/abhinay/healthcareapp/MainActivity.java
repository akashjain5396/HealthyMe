package com.example.abhinay.healthcareapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView patient;
    private TextView doctor;
    private TextView chemist;

    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patient = (Button)findViewById(R.id.buttonPatient);
        doctor = (Button)findViewById(R.id.buttonDoctor);
        chemist = (Button)findViewById(R.id.buttonChemist);
        signup = (Button)findViewById(R.id.signup);

        patient.setOnClickListener(this);
        chemist.setOnClickListener(this);
        doctor.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    private void openpatient(){
        Intent intent = new Intent(this,Patient.class);
        startActivity(intent);
    }

    private void opendoctor(){
        Intent intent = new Intent(this,Doctor.class);
        startActivity(intent);
    }

    private void openchemist(){
        Intent intent = new Intent(this,Chemist.class);
        startActivity(intent);
    }

    private void opensignup(){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void onClick(View v){
        if(v==patient)
            openpatient();
        else if(v==doctor)
            opendoctor();
        else if(v==chemist)
            openchemist();
        else if (v==signup)
            opensignup();
    }
}
