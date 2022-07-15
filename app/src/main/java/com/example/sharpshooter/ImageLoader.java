package com.example.sharpshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.widget.ImageView;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageLoader
{
    private static ImageLoader _instance;

    private final List<Pair<String, Bitmap>> parkourBitmaps;
    private int loadingViews;

    private ImageLoader()
    {
        parkourBitmaps = new ArrayList<>();
        loadingViews = 0;
    }

    public static ImageLoader GetInstance()
    {
        if (_instance == null)
            _instance = new ImageLoader();
        return _instance;
    }

    // Add imageview to loading list
    public void addImageViewToLoading(ImageView view, String parkourId)
    {
        // Set imageview to bitmap if bitmap is already loaded
        if(setLoadedImageIfExisting(view, parkourId)) {
            Objects.requireNonNull(Utils.GetInstance()).StopLoading();
            return;
        }

        Objects.requireNonNull(Utils.GetInstance()).StartLoading();

        // Add view to loading views
        loadingViews++;

        // Load image for specific imageview from database
        StorageReference imgReference = FirebaseUtil.GetInstance().storage.child("parkours/" + parkourId);
        imgReference.getBytes(8000000).addOnSuccessListener(bytes -> {
            // Generate bitmap from loaded data
            Bitmap map = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            // Save bitmap in cache
            parkourBitmaps.add(new Pair<>(parkourId, map));

            // Set view to bitmap
            view.setImageBitmap(map);

            // Remove imageview from loading list -> Now loaded
            loadingViews--;

            // Stop loading if loading list is empty
            if (loadingViews <= 0)
                Utils.GetInstance().StopLoading();
        }).addOnFailureListener(failure -> {
            loadingViews--;

            // Stop loading if loading list is empty
            if (loadingViews <= 0)
                Utils.GetInstance().StopLoading();
        });
    }

    private boolean setLoadedImageIfExisting(ImageView view, String parkourId)
    {
        for (Pair<String, Bitmap> parkour : parkourBitmaps) {
            if (parkour.first.equals(parkourId)) {
                view.setImageBitmap((Bitmap)parkour.second);
                return true;
            }
        }
        return false;
    }
}
