package com.example.sharpshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.sharpshooter.template.UserTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class FirebaseUtil
{
    private static FirebaseUtil instance = null;

    // Firebase
    public FirebaseFirestore database;
    public FirebaseAuth authentication;
    public StorageReference storage;

    // Account
    public UserTemplate userInstance;
    public Bitmap userProfilePicture;

    public static FirebaseUtil getInstance()
    {
        if (instance == null)
        {
            instance = new FirebaseUtil();
            instance.database = FirebaseFirestore.getInstance();
            instance.authentication = FirebaseAuth.getInstance();
            instance.storage = FirebaseStorage.getInstance().getReference();

            // Read existing user from database
            instance.readUserFromDatabase();
        }

        return instance;
    }

    // Create data in database for new user
    public void createNewUserData(UserTemplate user) {
        database.collection("users").document(Objects.requireNonNull(authentication.getUid())).set(user);
    }

    // Read existing user data from database
    private void readUserFromDatabase()
    {
        // Load data from database
        DocumentReference docRef = database.collection("users").document(authentication.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                userInstance = documentSnapshot.toObject(UserTemplate.class);
            }
        });

        // Load profile picture from database
        StorageReference imgReference = storage.child("users/" + authentication.getUid());
        imgReference.getBytes(8000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Save loaded bytes in Bitmap
                userProfilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });
    }

    // Upload profile picture
    public void uploadAccountImage(Uri img) {
        // Get reference to new / existing img file in storage
        StorageReference imgStorage = storage.child("users/" + authentication.getUid());

        // Start new upload task
        UploadTask uploadTask = imgStorage.putFile(img);
    }

    // // Update functions
    public void updateData(String field, int data)
    {
        switch (field) {
            case "broken":      userInstance.setBroken(data);
            case "totalGames":  userInstance.setTotalGames(data);
            case "hits":        userInstance.setHits(data);
            case "kills":       userInstance.setKills(data);
            case "misses":      userInstance.setMisses(data);
            case "points":      userInstance.setPoints(data);
            case "shots":       userInstance.setShots(data);
        }

        database.collection("users").document(authentication.getUid()).update(field, data);
    }

    public void updateData(String field, double data)
    {
        userInstance.setKd(data);

        database.collection("users").document(authentication.getUid()).update(field, data);
    }
}
