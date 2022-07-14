package com.example.sharpshooter;

import com.google.firebase.firestore.QuerySnapshot;

public interface AllGamesLoader {
    void onCallback(QuerySnapshot value);
}
