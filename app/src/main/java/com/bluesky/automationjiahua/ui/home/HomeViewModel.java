package com.bluesky.automationjiahua.ui.home;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    //todo 每次返回fragment都会调用oncreate方法的解决
    //todo 可能:不在viewmodel中保存本页面所需的数据,那么viewmodel,或者livedata,就会失效.那么就会重新create.甚至整个app重新启动
    //当前列表的位置
    private int mCurrentItem;
    //当前区域
    private int mRange;
    //当前搜索范围
    private int mSearch;
    //当前搜索关键字
    private String mSearchWords="";
    //关键字
    private String mKeyWord="";


    public HomeViewModel() {

    }


    public String getSearchWords() {
        return mSearchWords;
    }

    public void setSearchWords(String searchWords) {
        mSearchWords = searchWords;
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setCurrentItem(int currentItem) {
        mCurrentItem = currentItem;
    }

    public int getRange() {
        return mRange;
    }

    public void setRange(int range) {
        mRange = range;
    }

    public int getSearch() {
        return mSearch;
    }

    public void setSearch(int search) {
        mSearch = search;
    }

    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }
}