package com.louisngatale.realestate.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.louisngatale.realestate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//TODO: Null pointer bug
public class Map extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {

    Button saveAddress;

    private Geocoder geocoder;
    private HashMap<String,String> address;
    List<Address> addresses;
    Marker position;

    private FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude, longitude;
    private final String TAG = "MAPS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        geocoder = new Geocoder(Map.this, Locale.getDefault());

        address = new HashMap<>();

        saveAddress = findViewById(R.id.confirmAddress);

        saveAddress.setOnClickListener(this);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a PlacesClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        addMarker(googleMap);

        // Add click listener to update the current location when the user clicks on the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                latitude = latLng.latitude;
                longitude = latLng.longitude;

                position.setPosition(latLng);
                position.setTitle("Your house");

                // Decode the updated address
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    address.put("Address",addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    address.put("State",addresses.get(0).getAdminArea());
                    address.put("Country",addresses.get(0).getCountryName());
                    address.put("Postal Code",addresses.get(0).getPostalCode());
                    address.put("Known Name",addresses.get(0).getFeatureName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void addMarker(GoogleMap googleMap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //  Get location
                fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                // Get latitude and longitude
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                //Create new latlng object
                                LatLng latLng = new LatLng(latitude,longitude);

                                //Add latlng to new options object
                                MarkerOptions options = new MarkerOptions()
                                            .position(latLng)
                                            .title("Your location");

                                //Add marker to map
                                if(position == null){
                                    position = googleMap.addMarker(options);
                                }
                                else {
                                    position.setPosition(latLng);
                                }

                                // Set the initial camera position
                                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 14.0f) );

                                // Decode the address
                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    address.put("Address",addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    address.put("State",addresses.get(0).getAdminArea());
                                    address.put("Country",addresses.get(0).getCountryName());
                                    address.put("Postal Code",addresses.get(0).getPostalCode());
                                    address.put("Known Name",addresses.get(0).getFeatureName());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d(TAG, "onFailure: " + e);
                            Log.d(TAG, "onFailure: Failed to get current location" );

                            Toast.makeText(Map.this, "Couldn't get current location", Toast.LENGTH_SHORT).show();
                        }
                });

            }else{
                Toast.makeText(this, "Request denied", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Else check", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirmAddress:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Address",address);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
