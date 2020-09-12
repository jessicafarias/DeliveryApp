package com.example.godrive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

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


//public class CurrentlocationActivity extends AppCompatActivity {
public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private static  final int REQUEST_LOCATION=1;

    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Add permission

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        //showLocationTxt=findViewById(R.id.show_location);
        //getlocationBtn=findViewById(R.id.getLocation);
        ButtonAction();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = getLocation();
        LatLng sydney2 = new LatLng(sydney.latitude+0.01, sydney.longitude);
        //LatLng sydney = new LatLng(getLocation().latitude, getLocation().longitude);
        //LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("YOU").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.background)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
        mMap.addMarker(new MarkerOptions().position(sydney2).title("DESTINATION").draggable(true));
        CircleOptions circleOptions = new CircleOptions()
                .center(sydney)
                .radius(1000); // In meters
        // Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);
        mMap.setOnMarkerDragListener(this);

        Location locA = new Location("punto A");
        Location locB = new Location("punto B");
        locA.setLatitude(sydney.latitude);
        locA.setLongitude(sydney.longitude);
        locB.setLatitude(sydney2.latitude);
        locB.setLongitude(sydney2.longitude);
        float dis = locA.distanceTo(locB);

        Toast.makeText(this, ""+dis+"", Toast.LENGTH_LONG).show();
        //https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR&key=AIzaSyB1e7npY-O5g3V7Erm4v3I9nM1wTLFBMw8
        //jsonfile

        //https://developers.google.com/maps/documentation/distance-matrix/overview

        //Volley
        //https://www.youtube.com/watch?v=YNM_-cR9QKQ
        //
        //JUST POLILINE
        //https://www.youtube.com/watch?v=58AxNh2cWRU

        //Extra: https://www.it-swarm.dev/es/git/como-descartar-cambios-locales-y-obtener-lo-ultimo-del-repositorio-de-github/827022313/
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
            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //Toast.makeText(this, ""+latitude+longitude+"", Toast.LENGTH_SHORT).show();
                LatLng sydney = new LatLng(lat, longi);
                return sydney;
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //Toast.makeText(this, ""+latitude+longitude+"", Toast.LENGTH_SHORT).show();
                LatLng sydney = new LatLng(lat, longi);
                return sydney;
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
                Toast.makeText(this, ""+latitude+longitude+"", Toast.LENGTH_SHORT).show();
                //showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
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
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
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

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}


