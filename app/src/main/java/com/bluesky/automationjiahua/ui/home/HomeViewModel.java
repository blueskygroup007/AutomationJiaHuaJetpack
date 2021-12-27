package com.bluesky.automationjiahua.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    //todo 每次返回fragment都会调用oncreate方法的解决
    //todo 可能:不在viewmodel中保存本页面所需的数据,那么viewmodel,或者livedata,就会失效.那么就会重新create.甚至整个app重新启动
    //当前列表的位置
    private MutableLiveData<Integer> mCurrentItem;
    //当前区域
    private MutableLiveData<Integer> mRange;
    //当前搜索范围
    private MutableLiveData<Integer> mSearch;
    //关键字
    private MutableLiveData<String[]> mKeyWord;
    //历史关键字
    private List<String> mHistory;
    //过滤后的列表数据
//    private LiveData<List<Device>> mFilteredDevices = new MutableLiveData<>();


    public HomeViewModel() {

    }



    public List<String> getHistory() {
        if (mHistory == null) {
            mHistory = new ArrayList<>();
        }
        return mHistory;
    }

    public void setHistory(List<String> history) {
        mHistory = history;
    }

    public MutableLiveData<Integer> getmCurrentItem() {
        if (mCurrentItem == null) {
            mCurrentItem = new MutableLiveData<>(0);
        }
        return mCurrentItem;
    }

    public MutableLiveData<Integer> getmRange() {
        if (mRange == null) {
            mRange = new MutableLiveData<>(0);
        }
        return mRange;
    }

    public MutableLiveData<Integer> getmSearch() {
        if (mSearch == null) {
            mSearch = new MutableLiveData<>(0);
        }
        return mSearch;
    }

    public MutableLiveData<String[]> getmKeyWord() {
        if (mKeyWord == null) {
            mKeyWord = new MutableLiveData<>();
        }
        return mKeyWord;
    }
}