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
        devices = DeviceRepository.getmLiveDataDevices();
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

    /*让ViewModel的devices等于Repository的内部Livedata。使得在调用无返回值的查询数据库方法时，能被监听*/
    public MutableLiveData<List<Device>> getDevices() {
        if (devices == null) {
            devices = new MutableLiveData<>();
        }
        return devices;
    }

}