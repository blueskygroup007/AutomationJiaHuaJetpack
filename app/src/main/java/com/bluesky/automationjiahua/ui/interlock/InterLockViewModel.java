package com.bluesky.automationjiahua.ui.interlock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.database.InterLock;

import java.util.List;

public class InterLockViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<InterLock>> mData;

    public InterLockViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        mData = new MutableLiveData<>();
        mData = DeviceRepository.getInstance().loadAllInterLocks();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<InterLock>> getData() {
        return mData;
    }
}