package com.example.sharpshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.sharpshooter.template.GameTemplate;
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
import java.util.Arrays;
import java.util.Objects;

public class FirebaseUtil
{
    private static FirebaseUtil instance = null;
    private int targetId;


    // Firebase
    public FirebaseFirestore database;
    public FirebaseAuth authentication;
    public StorageReference storage;

    // Account
    public UserTemplate userInstance;
    // TODO CURRENT GAME
    // CurrentGame
    public GameTemplate gameInstance;
    public String activeGame;


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


            instance.database.collection("users").document(instance.authentication.getUid()).collection("games").whereEqualTo("active", true).get().addOnCompleteListener(task -> {
                instance.activeGame = task.getResult().getDocuments().get(0).getId().toString();
                //Read existing active game from database
                instance.readCurrentGameFromDatabase();
                // Add SnapshotListener which waits for gameDocument database updates
                instance.database.collection("users").document(instance.authentication.getUid())
                        .collection("games").document(instance.activeGame).addSnapshotListener((value, error) -> {
                                    instance.updateActiveGameInstance(value);
                                }
                        );
            });

            // Add SnapshotListener which waits for database updates
            instance.database.collection("users").document(instance.authentication.getUid()).addSnapshotListener((value, error) -> {
                instance.updateUserInstance(value);
                    }
            );


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
        docRef.get().addOnSuccessListener(documentSnapshot -> userInstance = documentSnapshot.toObject(UserTemplate.class));

        // Load profile picture from database
        StorageReference imgReference = storage.child("users/" + authentication.getUid());
        imgReference.getBytes(8000000).addOnSuccessListener(bytes -> {
            // Save loaded bytes in Bitmap
            userProfilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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
    public void updateUserData(String field, Object data)
    {
        // Update field with data in database
        database.collection("users").document(authentication.getUid()).update(field, data);
    }


    public void readCurrentGameFromDatabase()
    {
        // Load data from database
        DocumentReference docRef = database.collection("users")
                .document(authentication.getUid()).collection("games").document(instance.activeGame);
        docRef.get().addOnSuccessListener(documentSnapshot -> gameInstance = documentSnapshot.toObject(GameTemplate.class));

    }

    public void updateActiveGame()
    {
        database.collection("users").document(authentication.getUid()).collection("games").whereEqualTo("active", true).get().addOnCompleteListener(task -> {
            activeGame = task.getResult().getDocuments().get(0).getId().toString();
        });

    }

    public void updateGameData(String field, Object data, String game)
    {
        // Update field with data in database
        database.collection("users").document(authentication.getUid()).collection("games").document(game).update(field, data);
    }


    private void updateActiveGameInstance(DocumentSnapshot update)
    {
        // Get updated Object from snapshot
        GameTemplate updateObject = update.toObject(GameTemplate.class);

        try
        {
            // Go trough all members of class
            for (Field field : updateObject.getClass().getDeclaredFields())
            {
                // Make private fields public
                field.setAccessible(true);

                // Get values of current field from booth objects
                Object value1 = field.get(updateObject);
                Object value2 = field.get(gameInstance);

                // Check if values differ
                if (!Objects.equals(value1, value2))
                {
                    // Set new value in userInstance
                    field.set(gameInstance, value1);
                }

                // Make public fields private again
                field.setAccessible(false);
            }
        }
        catch (Exception ignored) {}
    }


    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getTargetId() {
        return targetId;
    }
}
