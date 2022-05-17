package com.example.sharpshooter;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.sharpshooter.template.UserTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class FirebaseUtil
{
    private static FirebaseUtil instance = null;

    public FirebaseFirestore database;
    public FirebaseAuth authentication;
    public StorageReference storage;

    public static FirebaseUtil getInstance()
    {
        if (instance == null)
        {
            instance = new FirebaseUtil();
            instance.database = FirebaseFirestore.getInstance();
            instance.authentication = FirebaseAuth.getInstance();
            instance.storage = FirebaseStorage.getInstance().getReference();
        }

        return instance;
    }

    // Create data in database for new user
    public void createNewUserData(UserTemplate user) {
        database.collection("users").document(Objects.requireNonNull(authentication.getUid())).set(user);
    }

    // Upload profile picture
    public void uploadAccountImage(Uri img) {
        // Get reference to new / existing img file in storage
        StorageReference imgStorage = storage.child("users/" + authentication.getUid());

        // Start new upload task
        UploadTask uploadTask = imgStorage.putFile(img);
    }
}
