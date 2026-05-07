package com.example.lostandfoundapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> posts;
    DBHelper dbHelper;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        dbHelper = new DBHelper(context);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.get(position).id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.itemImage);
        TextView title = view.findViewById(R.id.itemTitle);
        TextView details = view.findViewById(R.id.itemDetails);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);

        Post post = posts.get(position);

        title.setText(post.type + " - " + post.category);
        details.setText(
                "Name: " + post.name +
                        "\nPhone: " + post.phone +
                        "\nDescription: " + post.description +
                        "\nLocation: " + post.location +
                        "\nPosted: " + post.dateTime
        );

        try {
            imageView.setImageURI(Uri.parse(post.imageUri));
        } catch (Exception e) {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        deleteBtn.setOnClickListener(v -> {
            dbHelper.deletePost(post.id);
            posts.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Advert removed", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}