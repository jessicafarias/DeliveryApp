package com.example.godrive;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


//public class CurrentlocationActivity extends AppCompatActivity {
public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private static  final int REQUEST_LOCATION=1;
    float dist;

    LatLng sydney2, sydney, jmf;

    private RequestQueue queue;

    LocationManager locationManager;
    String latitude,longitude;

    private Marker origin, destination, driver, extra;


    private Button BtnMarkerDestination;
    private EditText EditTextfind;

    //

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BtnMarkerDestination = (Button)findViewById(R.id.BtnMarkerDestination);
        EditTextfind = (EditText)findViewById(R.id.editTextfind);

        ////////////////////////////////// Instantiate the RequestQueue.
       queue = Volley.newRequestQueue(this);
        //////////////////////////////////

        //Add permission
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        //showLocationTxt=findViewById(R.id.show_location);
        //getlocationBtn=findViewById(R.id.getLocation);
        ButtonAction();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BtnMarkerDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //GetGeoName();

                    ChangeDestination();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void ButtonAction() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Check gps is enable or not
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
            getLocation();
        } else {
            getLocation();
        }
    }

    public void GetGeoName() throws IOException {

        Geocoder geo = new Geocoder(this);
        List<Address> adress = null;
        try {
            //Agregar todos los carros activos de la base de datos
            adress = geo.getFromLocationName(EditTextfind.getText().toString(),1,
                    1,1,1,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (adress.size() >0) {
            LatLng jmf = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
            extra = mMap.addMarker(new MarkerOptions().position(jmf).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        }
    }

    public void ChangeDestination() throws IOException {
        Geocoder geo = new Geocoder(this);
        List<Address> adress = null;
        try {
            adress = geo.getFromLocationName(EditTextfind.getText().toString()+" isla mujeres", 1,
                    1, 1, 1, 1);

            if (adress.size() == 0) {
                adress = geo.getFromLocationName(EditTextfind.getText().toString(),1,
                        1,1,1,1);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        if (adress.size() >0){
            jmf = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
            destination.setPosition(jmf);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jmf, 16));
        }
        else{
            Toast.makeText(this,"No se encontró resultado", Toast.LENGTH_SHORT).show();
        }


        //destination= mMap.addMarker(new MarkerOptions().position(jmf).draggable(true).title("DESTINO"));
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        sydney = getLocation();
        if (sydney2==null){
            sydney2 = new LatLng(sydney.latitude+0.01, sydney.longitude);
        }
        origin= mMap.addMarker(new MarkerOptions().position(sydney).title("YOU").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
        driver= mMap.addMarker(new MarkerOptions().position(sydney2).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        destination= mMap.addMarker(new MarkerOptions().position(sydney2).draggable(true).title("DESTINO"));

        CircleOptions circleOptions = new CircleOptions()
                .center(sydney)
                .radius(1000); // In meters Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);
        mMap.setOnMarkerDragListener(this);

    }

    public LatLng getLocation() {
        //Check Permissions again
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps !=null){
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();
                LatLng sydney = new LatLng(lat, longi);
                return sydney;
            }
            else if (LocationNetwork !=null){
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();
                LatLng sydney = new LatLng(lat, longi);
                return sydney;
            }
            else if (LocationPassive !=null){
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();
                LatLng sydney = new LatLng(lat, longi);
                return sydney;


            }
            else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                LatLng sydney = new LatLng(2, 3);
                return sydney;
            }
        }
        LatLng sydney = new LatLng(2, 3);
        return sydney;
    }
    private void OnGPS() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Activar GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        JsonParse();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Location locA = new Location("punto A");
        Location locB = new Location("punto B");
        locA.setLatitude(origin.getPosition().latitude);
        locA.setLongitude(origin.getPosition().longitude);
        locB.setLatitude(destination.getPosition().latitude);
        locB.setLongitude(destination.getPosition().longitude);
        dist = locA.distanceTo(locB);
        Toast.makeText(this, ""+dist+"", Toast.LENGTH_LONG).show();

    }

    private void JsonParse()    {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626&key=AIzaSyB1e7npY-O5g3V7Erm4v3I9nM1wTLFBMw8";
        /////////////////////////////// Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonarray =response.getJSONArray("empleado");
                            //for (int i =0 ; i< jsonarray.length(); i++){
                                //JSONArray empleado = jsonarray.getJSONObject(i);
                                String name = response.getString("error_message");
                                //Toast.makeText(MapsActivity.this, ""+name+"", Toast.LENGTH_LONG).show();
                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        ////////////////////////////////
        queue.add(request);

    }

    public void FindCalitaxi(View view) {
        Intent intent = new Intent(this, waitActivity.class);
        startActivity(intent);
    }
}


