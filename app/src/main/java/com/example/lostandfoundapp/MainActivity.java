package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addPostBtn, searchBtn, showAllBtn;
    EditText searchCategory;
    ListView listView;

    DBHelper dbHelper;
    ArrayList<Post> postList;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addPostBtn = findViewById(R.id.addPostBtn);
        searchBtn = findViewById(R.id.searchBtn);
        showAllBtn = findViewById(R.id.showAllBtn);
        searchCategory = findViewById(R.id.searchCategory);
        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);

        loadPosts();

        addPostBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddPostActivity.class));
        });

        searchBtn.setOnClickListener(v -> {
            String category = searchCategory.getText().toString().trim();

            if (category.isEmpty()) {
                Toast.makeText(this, "Enter category to search", Toast.LENGTH_SHORT).show();
            } else {
                postList = dbHelper.searchByCategory(category);
                adapter = new PostAdapter(this, postList);
                listView.setAdapter(adapter);
            }
        });

        showAllBtn.setOnClickListener(v -> {
            searchCategory.setText("");
            loadPosts();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void loadPosts() {
        postList = dbHelper.getAllPosts();
        adapter = new PostAdapter(this, postList);
        listView.setAdapter(adapter);
    }
}