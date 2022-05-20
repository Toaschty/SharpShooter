package com.example.sharpshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sharpshooter.template.UserTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Field;
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
    // TODO CURRENT GAME
    public Bitmap userProfilePicture;

    public static FirebaseUtil getInstance()
    {
        if (instance == null)
        {
            // Initialize class
            instance = new FirebaseUtil();
            instance.database = FirebaseFirestore.getInstance();
            instance.authentication = FirebaseAuth.getInstance();
            instance.storage = FirebaseStorage.getInstance().getReference();

            // Read existing user from database
            instance.readUserFromDatabase();

            // Add SnapshotListener which waits for database updates
            instance.database.collection("users").document(instance.authentication.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    instance.updateUserInstance(value);
                }
            });
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

    // Update user instance
    private void updateUserInstance(DocumentSnapshot update)
    {
        // Get updated Object from snapshot
        UserTemplate updateObject = update.toObject(UserTemplate.class);

        try
        {
            // Go trough all members of class
            for (Field field : updateObject.getClass().getDeclaredFields())
            {
                // Make private fields public
                field.setAccessible(true);

                // Get values of current field from booth objects
                Object value1 = field.get(updateObject);
                Object value2 = field.get(userInstance);

                // Check if values differ
                if (!Objects.equals(value1, value2))
                {
                    // Set new value in userInstance
                    field.set(userInstance, value1);
                }

                // Make public fields private again
                field.setAccessible(false);
            }
        }
        catch (Exception ignored) {}
    }

    // // Update functions
    public void updateData(String field, Object data)
    {
        // Update field with data in database
        database.collection("users").document(authentication.getUid()).update(field, data);
    }
}
