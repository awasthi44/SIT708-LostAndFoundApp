# Lost and Found Map Mobile App

## 📌 Overview

The Lost and Found Map Mobile App is an Android application developed using Java in Android Studio. The application allows users to create lost or found item adverts, upload images, save item details using SQLite database, and display item locations on Google Maps.

Users can search and filter lost/found items by category, view nearby items on the map, and use geo-location features to find items within a selected radius.

This application was developed as part of SIT708 – Mobile Systems Development at Deakin University.

---

# 🚀 Features

## ✅ Core Functionality

➕ Create Lost or Found adverts  
📋 View all saved lost/found posts  
🗑️ Remove adverts after item is returned  
🖼️ Upload image for each advert  
🕒 Display date and time stamp  
🔍 Search and filter items by category  

---

# 🌍 Geo Features

📍 Get Current Location  
🗺️ Google Maps Integration  
📌 Show all lost/found items on map  
📏 Radius-based nearby item search  
📍 Display nearby items within selected radius  

---

# 💾 Database Functionality

Uses SQLite Database for local storage.

Stores:
- Item Type
- Name
- Phone Number
- Description
- Category
- Location
- Latitude & Longitude
- Date & Time
- Image URI

Data remains saved even after restarting the app.

---

# ⚠️ Validation & Error Handling

✔ All fields required before saving  
✔ Image upload required  
✔ Location permission handling  
✔ Toast messages for user feedback  
✔ Prevents empty advert submission  

---

# 🛠️ Technologies Used

- Java
- XML UI Design
- SQLite Database
- Android Studio
- Google Maps API
- Android Geo-location Services
- ListView & Adapter
- Android SDK

---

# 📱 Screens in the App

## Main Screen

- Create new advert
- Search/filter items
- Show all posts
- Show items on Google Map

---

## Create Advert Screen

Allows users to:
- Select Lost or Found type
- Select item category
- Upload image
- Enter details
- Get current location
- Save advert

---

## Google Map Screen

Displays:
- Current user location
- Nearby lost/found items
- Map markers with item details
- Radius-based nearby filtering

---

# 📂 Project Structure

```text
com.example.lostandfoundapp
│
├── MainActivity.java
├── AddPostActivity.java
├── MapActivity.java
├── DBHelper.java
├── Post.java
├── PostAdapter.java
│
├── res/layout
│   ├── activity_main.xml
│   ├── activity_add_post.xml
│   ├── activity_map.xml
│   └── item_post.xml
│
├── res/drawable
│   ├── button_purple.xml
│   └── card_bg.xml
```

---

# ▶️ How to Run the Project

1. Clone this repository:

👉 https://github.com/awasthi44/SIT708-LostAndFoundApp

2. Open project in Android Studio

3. Add your Google Maps API key in:
```text
AAIzaSyCwynoFlALYOZrppXoU-lrCehKhXyqX3l8"
```

4. Sync Gradle dependencies

5. Run the app using:
- Android Emulator with Google Play
OR
- Physical Android Device

---

# 🧪 Testing Checklist

✔ Create advert successfully  
✔ Upload image successfully  
✔ Save data into SQLite database  
✔ Search/filter by category works  
✔ Google Maps loads successfully  
✔ Current location works  
✔ Nearby item markers displayed  
✔ Radius-based search works  
✔ Data persists after app restart  

---

# 🎥 Demonstration Video

👉 https://deakin.au.panopto.com/Panopto/Pages/Viewer.aspx?id=1520d708-6d48-4308-971d-b44e000a6b14


---

# 🔗 GitHub Repository

👉 https://github.com/awasthi44/SIT708-LostAndFoundApp

---

# 🤖 LLM Usage Declaration

This project was developed with assistance from ChatGPT.

The AI tool was used to support:
- SQLite database integration
- Google Maps integration
- Android geo-location features
- Radius-based search implementation
- UI improvements
- Debugging and validation handling
- README and documentation preparation

All generated code and written content were reviewed, tested, modified, and validated by the developer before final submission.

---

# 📌 Author

SHOVA AWASTHI  
Student ID: 224887189  
Deakin University  

---

# ⭐ Conclusion

This project demonstrates the practical implementation of Android mobile application development concepts including SQLite database integration, Google Maps API, geo-location services, radius-based search, image handling, and user-friendly interface design. The application successfully combines map-based functionality with lost and found management features into a complete Android mobile solution.
