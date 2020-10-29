package com.example.godrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONArray;

import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;

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
        progress = findViewById(R.id.progressBar);
        mesage = findViewById(R.id.drivername);
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
        Toast.makeText(this, ""+latitude+longitude,Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("viaje_id");


        //DatabaseReference myRef = database.getReference("message").child("childname");


        progressD = ProgressDialog.show(this, "Buscando calitaxi","Buscando conductores cercanos acepten el viaje ...", true);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);

                String value = Integer.toString((int)dataSnapshot.getChildrenCount());
                mesage.setText(value);

                //if(value == "datos taxi 55") {
                //    progressD.dismiss();
                //}
                //if(value != "datos taxi 56") {

                //    Write((int) dataSnapshot.getChildrenCount());
                //    progressD.dismiss();
                //}
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Write((int) snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Write(int count){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("id");
        DatabaseReference myRef = database.getReference("viaje_id");
        myRef.child("id").setValue(5);


        //https://firebase.google.com/docs/database/android/start?hl=es-419#java

        String key = myRef.child("posts").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put("/count_messages/" + key, Integer.toString(count));


        //UPDATE OBJECTS
        ///Map<String, Object> childUpdates = new HashMap<>();
        ///childUpdates.remove("/posts/" + key, "postValues");
        ///childUpdates.remove("/user-posts/" + 1 + "/" + key, "posta");
        //myRef.updateChildren(childUpdates);

        //REMOVE OBJECTS
        //myRef.child("count_messages").removeValue();


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