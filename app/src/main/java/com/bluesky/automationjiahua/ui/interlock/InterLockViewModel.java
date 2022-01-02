package com.bluesky.automationjiahua.ui.interlock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.database.InterLock;

import java.util.ArrayList;
import java.util.List;

public class InterLockViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<InterLock>> mData;
    private int spinnerDomainPosition = 0;

    public InterLockViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        mData = new MutableLiveData<>(new ArrayList<>());
        mData = DeviceRepository.getInstance().getmLiveDataInterLocks();
    }

    public int getSpinnerDomainPosition() {
        return spinnerDomainPosition;
    }

    public void setSpinnerDomainPosition(int spinnerDomainPosition) {
        this.spinnerDomainPosition = spinnerDomainPosition;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<InterLock>> getData() {
        return mData;
    }
}