package com.example.sharpshooter.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> newGameBtn;

    public HomeViewModel() {
        newGameBtn = new MutableLiveData<>();
    }

    public LiveData<String> getBtnName(){ return newGameBtn; }
}