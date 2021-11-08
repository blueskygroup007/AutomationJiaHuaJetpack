package com.bluesky.automationjiahua.ui.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.database.DeviceRepository;

import java.util.List;

public class DatabaseViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> content;
    private MutableLiveData<List<Device>> devices;

    public DatabaseViewModel() {
        devices = DeviceRepository.getmLiveData();
    }

    public LiveData<String> getContent() {
        if (content == null) {
            content = new MutableLiveData<>();
        }
        return content;
    }

    public void setContent(MutableLiveData<String> content) {
        this.content = content;
    }

    public MutableLiveData<List<Device>> getDevices() {
        if (devices == null) {
            devices = DeviceRepository.getmLiveData();
        }
        return devices;
    }

    public void setDevices(LiveData<List<Device>> devices) {
        this.devices.postValue(devices.getValue());
    }
}