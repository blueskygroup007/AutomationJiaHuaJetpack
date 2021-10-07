package com.bluesky.automationjiahua.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.database.DeviceRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:
 */
public class DeviceViewModel extends AndroidViewModel {
    private MutableLiveData<List<Device>> mLiveDataDevices;
    private DeviceRepository mDeviceRepository;

    public DeviceViewModel(@NonNull @NotNull Application application) {
        super(application);
        mDeviceRepository = new DeviceRepository(application);
        mLiveDataDevices = new MutableLiveData<>();
    }

    public MutableLiveData<List<Device>> getLiveDataDevices() {
        if (mLiveDataDevices.getValue() == null) {
            mLiveDataDevices.postValue(mDeviceRepository.getAllDevices().getValue());
        }
        return mLiveDataDevices;
    }

    private MutableLiveData<List<Device>> trans(List<Device> data) {
        mLiveDataDevices.setValue(data);
        return mLiveDataDevices;
    }

    public MutableLiveData<List<Device>> findDevicesWithPattern(String domain, String column, String keyWords) {
        mLiveDataDevices.postValue(mDeviceRepository.findDeviceByPattern(domain, column, keyWords).getValue());
        return mLiveDataDevices;
    }

/*    public MutableLiveData<List<Device>> findDevicesWithPatternByTag(String pattern) {
        mLiveDataDevices.setValue(mDeviceRepository.findDeviceByTag(pattern).getValue());
    }

    public MutableLiveData<List<Device>> findDevicesWithPatternByAffect(String pattern) {
        mLiveDataDevices.setValue(mDeviceRepository.findDeviceByAffect(pattern).getValue());
    }

    public MutableLiveData<List<Device>> findDevicesWithPatternByName(String pattern) {
        mLiveDataDevices.setValue(mDeviceRepository.findDeviceByName(pattern).getValue());
    }

    public MutableLiveData<List<Device>> findDevicesWithPatternByStandard(String pattern) {
        mLiveDataDevices.setValue(mDeviceRepository.findDeviceByStandard(pattern).getValue());
    }

    public MutableLiveData<List<Device>> findDevicesWithPatternByType(String pattern) {
        mLiveDataDevices.setValue(mDeviceRepository.findDeviceByType(pattern).getValue());
    }*/


/*    public LiveData<List<Device>> findDevicesWithPattern(String domain, String column, String keyWords) {
        return mDeviceRepository.findDeviceByPattern(domain, column, keyWords);
    }

    public LiveData<List<Device>> findDevicesWithPatternByTag(String pattern) {
        return mDeviceRepository.findDeviceByTag(pattern);
    }

    public LiveData<List<Device>> findDevicesWithPatternByAffect(String pattern) {
        return mDeviceRepository.findDeviceByAffect(pattern);
    }

    public LiveData<List<Device>> findDevicesWithPatternByName(String pattern) {
        return mDeviceRepository.findDeviceByName(pattern);
    }

    public LiveData<List<Device>> findDevicesWithPatternByStandard(String pattern) {
        return mDeviceRepository.findDeviceByStandard(pattern);
    }

    public LiveData<List<Device>> findDevicesWithPatternByType(String pattern) {
        return mDeviceRepository.findDeviceByType(pattern);
    }*/
}
