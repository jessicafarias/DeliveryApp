package com.example.godrive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class waitActivity extends AppCompatActivity {


    private TextView mesage;
    private static final String TAG = "waitActivity";
    private ProgressBar progress;
    private ProgressDialog progressD;
    private double latitude, longitude;

    Pasajeros pasajeros;
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        mesage = (TextView) findViewById(R.id.drivername);
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
        Toast.makeText(this, ""+latitude+longitude,Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        progressD = ProgressDialog.show(this, "Buscando calitaxi","Buscando conductores cercanos acepten el viaje ...", true);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                mesage.setText(value);
                if(value != "Te amo bebe") {
                    progressD.dismiss();
                }


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Write(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("id");
        myRef.child("status").setValue("");



        myRef.setValue("Hello, World!");
        //https://firebase.google.com/docs/database/android/start?hl=es-419#java
    }

    public void stopp(View view){
        progress.onVisibilityAggregated(false);

        try {

            this.pasajeros.NewPasajero(1, 1);
            Toast.makeText(this,"save teikirisi", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            String error =e.getMessage();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}