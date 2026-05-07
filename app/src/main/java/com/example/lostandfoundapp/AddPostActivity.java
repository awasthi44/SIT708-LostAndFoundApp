package com.example.lostandfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;

public class AddPostActivity extends AppCompatActivity {

    EditText nameInput, phoneInput, descriptionInput, locationInput;
    Spinner typeSpinner, categorySpinner;
    Button selectImageBtn, saveBtn;
    ImageView previewImage;

    Uri selectedImageUri = null;
    DBHelper dbHelper;

    ActivityResultLauncher<String[]> imagePickerLauncher;

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
        previewImage = findViewById(R.id.previewImage);

        dbHelper = new DBHelper(this);

        String[] types = {"Lost", "Found"};
        String[] categories = {"Electronics", "Pets", "Wallets", "Keys", "Bags", "Documents", "Other"};

        typeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));
        categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

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
        selectImageBtn.setOnClickListener(v -> imagePickerLauncher.launch(new String[]{"image/*"}));

        saveBtn.setOnClickListener(v -> savePost());
    }

    private void savePost() {
        String type = typeSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateTime = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                .format(new Date());

        boolean inserted = dbHelper.insertPost(
                type,
                name,
                phone,
                description,
                category,
                location,
                dateTime,
                selectedImageUri.toString()
        );

        if (inserted) {
            Toast.makeText(this, "Post saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save post", Toast.LENGTH_SHORT).show();
        }
    }
}