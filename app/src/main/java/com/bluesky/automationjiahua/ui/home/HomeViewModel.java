package com.bluesky.automationjiahua.ui.home;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    //todo 每次返回fragment都会调用oncreate方法的解决
    //todo 可能:不在viewmodel中保存本页面所需的数据,那么viewmodel,或者livedata,就会失效.那么就会重新create.甚至整个app重新启动
    //当前列表的位置
    private int mCurrentItem = 0;
    //当前区域
    private int mRange = 0;
    //当前搜索范围
    private int mSearch = 0;
    //关键字
    private String mKeyWord = "";
    //过滤后的列表数据
//    private LiveData<List<Device>> mFilteredDevices = new MutableLiveData<>();


    public HomeViewModel() {

    }

/*    public LiveData<List<Device>> getmFilteredDevices() {
        return mFilteredDevices;
    }

    public void setmFilteredDevices(LiveData<List<Device>> mFilteredDevices) {
        this.mFilteredDevices = mFilteredDevices;
    }*/

    public int getmCurrentItem() {
        return mCurrentItem;
    }

    public void setmCurrentItem(int mCurrentItem) {
        this.mCurrentItem = mCurrentItem;
    }

    public int getmDomain() {
        return mRange;
    }

    public void setmDomain(int mRange) {
        this.mRange = mRange;
    }

    public int getmSearch() {
        return mSearch;
    }

    public void setmSearch(int mSearch) {
        this.mSearch = mSearch;
    }

    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }
}