package com.example.phms;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;




public class AddVitalSign extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnSave, btnCancel;
    TextInputLayout visValue;
    DatabaseReference db_ref;
    private static final String TAG = "AddVitalSign";
    Vitalsign vital;
    long maxid;
    Spinner spinner;
    String vitType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vitalsign);
        getSupportActionBar().hide();
        //============================================
        // Grab fields from XML
        //============================================
        visValue = findViewById(R.id.vitalValue);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        //============================================
        // SPINNER VARIABLES
        //============================================
        spinner = (Spinner) findViewById(R.id.vs_spinner);
        String[] objects = { "Temperature", "Blood Pressure", "Glucose Level", "Cholesterol Level" };
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),android.R.layout.simple_list_item_1 ,objects);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //============================================
        // CLASS AND DB INITIALIZATION
        //============================================
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d", Locale.US);
        final String time = df.format(new Date());
        vital = new Vitalsign();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); //get current user's id
        db_ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VitalSign").child(time); //refer to /Users/<uid>/VitalSign/Date
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=snapshot.getChildrenCount(); //make a snapshot, grab the "children count" which means the number of children of this users Notes (# of notes)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //============================================
        // WHEN USER CLICKS "SAVE"
        //============================================
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(!visValue.getEditText().getText().toString().matches("")) {
                    vital.setType(vitType);
                    vital.setValue(visValue.getEditText().getText().toString());
                    db_ref.child(String.valueOf(maxid + 1)).setValue(vital);
                }

                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Health.class);
                startActivity(intent);
            }
        });


    }

    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        vitType = spinner.getItemAtPosition(pos).toString();
    }
    // Defining the Callback methods here
    public void onNothingSelected(AdapterView arg0) {    }




}
