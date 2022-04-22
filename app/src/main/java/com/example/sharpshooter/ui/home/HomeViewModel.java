package com.example.sharpshooter.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> newGameBtn;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        newGameBtn = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        //Language can be changed
        newGameBtn.setValue("Neues Spiel");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getBtnName(){return newGameBtn;}

}