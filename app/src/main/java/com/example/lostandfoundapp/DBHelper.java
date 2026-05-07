package com.example.lostandfoundapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "lost_found.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE posts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, " +
                "name TEXT, " +
                "phone TEXT, " +
                "description TEXT, " +
                "category TEXT, " +
                "location TEXT, " +
                "dateTime TEXT, " +
                "imageUri TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS posts");
        onCreate(db);
    }

    public boolean insertPost(String type, String name, String phone, String description,
                              String category, String location, String dateTime, String imageUri) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", type);
        values.put("name", name);
        values.put("phone", phone);
        values.put("description", description);
        values.put("category", category);
        values.put("location", location);
        values.put("dateTime", dateTime);
        values.put("imageUri", imageUri);

        long result = db.insert("posts", null, values);
        return result != -1;
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM posts ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Post(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public ArrayList<Post> searchByCategory(String category) {
        ArrayList<Post> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM posts WHERE category LIKE ? ORDER BY id DESC",
                new String[]{"%" + category + "%"}
        );

        if (cursor.moveToFirst()) {
            do {
                list.add(new Post(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void deletePost(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("posts", "id=?", new String[]{String.valueOf(id)});
    }
}