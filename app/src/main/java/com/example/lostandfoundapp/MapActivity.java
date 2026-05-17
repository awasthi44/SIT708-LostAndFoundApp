package com.example.lostandfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    GoogleMap mMap;

    DBHelper dbHelper;

    ArrayList<Post> posts;

    FusedLocationProviderClient fusedLocationProviderClient;

    double currentLatitude = 0.0;
    double currentLongitude = 0.0;

    double radiusInKm = 10.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DBHelper(this);

        posts = dbHelper.getAllPosts();

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        getCurrentLocationAndShowMarkers();
    }

    private void getCurrentLocationAndShowMarkers() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    200);

            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        currentLatitude = location.getLatitude();

                        currentLongitude = location.getLongitude();

                    } else {

                        currentLatitude = -37.8136;
                        currentLongitude = 144.9631;
                    }

                    LatLng currentLocation =
                            new LatLng(
                                    currentLatitude,
                                    currentLongitude);

                    mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                    currentLocation,
                                    12));

                    showNearbyMarkers();
                });
    }

    private void showNearbyMarkers() {

        if (posts.size() == 0) {

            Toast.makeText(
                    this,
                    "No posts available",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        int nearbyCount = 0;

        for (Post post : posts) {

            if (post.latitude == 0.0
                    || post.longitude == 0.0) {

                continue;
            }

            float[] results = new float[1];

            Location.distanceBetween(
                    currentLatitude,
                    currentLongitude,
                    post.latitude,
                    post.longitude,
                    results);

            float distanceInMeters = results[0];

            float distanceInKm = distanceInMeters / 1000;

            if (distanceInKm <= radiusInKm) {

                nearbyCount++;

                LatLng itemLocation =
                        new LatLng(
                                post.latitude,
                                post.longitude);

                mMap.addMarker(
                        new MarkerOptions()
                                .position(itemLocation)
                                .title(post.type + " - " + post.category)
                                .snippet(
                                        post.description
                                                + "\n"
                                                + String.format(
                                                "%.2f km away",
                                                distanceInKm)
                                ));
            }
        }

        Toast.makeText(
                this,
                nearbyCount
                        + " nearby items found within "
                        + radiusInKm
                        + " km",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == 200
                && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocationAndShowMarkers();

        } else {

            Toast.makeText(
                    this,
                    "Location permission denied",
                    Toast.LENGTH_SHORT).show();
        }
    }
}