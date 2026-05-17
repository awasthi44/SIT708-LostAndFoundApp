package com.example.lostandfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {

    EditText nameInput, phoneInput, descriptionInput, locationInput;

    Spinner typeSpinner, categorySpinner;

    Button selectImageBtn, saveBtn, currentLocationBtn;

    ImageView previewImage;

    Uri selectedImageUri = null;

    DBHelper dbHelper;

    ActivityResultLauncher<String[]> imagePickerLauncher;

    FusedLocationProviderClient fusedLocationProviderClient;

    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        locationInput = findViewById(R.id.locationInput);

        typeSpinner = findViewById(R.id.typeSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);

        selectImageBtn = findViewById(R.id.selectImageBtn);
        saveBtn = findViewById(R.id.saveBtn);

        currentLocationBtn = findViewById(R.id.currentLocationBtn);

        previewImage = findViewById(R.id.previewImage);

        dbHelper = new DBHelper(this);

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);

        String[] types = {"Lost", "Found"};

        String[] categories = {
                "Electronics",
                "Pets",
                "Wallets",
                "Keys",
                "Bags",
                "Documents",
                "Other"
        };

        typeSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        types));

        categorySpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categories));

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {

                    if (uri != null) {

                        selectedImageUri = uri;

                        getContentResolver().takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );

                        previewImage.setImageURI(uri);
                    }
                }
        );

        selectImageBtn.setOnClickListener(v ->
                imagePickerLauncher.launch(new String[]{"image/*"}));

        currentLocationBtn.setOnClickListener(v -> {
            getCurrentLocation();
        });

        saveBtn.setOnClickListener(v -> savePost());
    }

    private void savePost() {

        String type = typeSpinner.getSelectedItem().toString();

        String category =
                categorySpinner.getSelectedItem().toString();

        String name =
                nameInput.getText().toString().trim();

        String phone =
                phoneInput.getText().toString().trim();

        String description =
                descriptionInput.getText().toString().trim();

        String location =
                locationInput.getText().toString().trim();

        if (name.isEmpty()
                || phone.isEmpty()
                || description.isEmpty()
                || location.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        if (selectedImageUri == null) {

            Toast.makeText(
                    this,
                    "Please upload an image",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        String dateTime =
                new SimpleDateFormat(
                        "dd MMM yyyy, hh:mm a",
                        Locale.getDefault())
                        .format(new Date());

        boolean inserted = dbHelper.insertPost(
                type,
                name,
                phone,
                description,
                category,
                location,
                latitude,
                longitude,
                dateTime,
                selectedImageUri.toString()
        );

        if (inserted) {

            Toast.makeText(
                    this,
                    "Post saved successfully",
                    Toast.LENGTH_SHORT).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to save post",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    100);

            return;
        }

        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        latitude = location.getLatitude();

                        longitude = location.getLongitude();

                        Geocoder geocoder =
                                new Geocoder(
                                        this,
                                        Locale.getDefault());

                        try {

                            List<Address> addresses =
                                    geocoder.getFromLocation(
                                            latitude,
                                            longitude,
                                            1);

                            if (addresses != null
                                    && !addresses.isEmpty()) {

                                String address =
                                        addresses.get(0)
                                                .getAddressLine(0);

                                locationInput.setText(address);
                            }

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                });
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

        if (requestCode == 100
                && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        }
    }
}