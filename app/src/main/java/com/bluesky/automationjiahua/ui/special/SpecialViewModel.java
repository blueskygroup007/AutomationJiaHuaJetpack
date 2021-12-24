package com.bluesky.automationjiahua.ui.special;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.automationjiahua.R;

import java.util.ArrayList;
import java.util.List;

public class SpecialViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<BeanSpecial>> mSpecialHuaChan = new MutableLiveData<>();
    private MutableLiveData<List<BeanSpecial>> mSpecialGanXiJiao = new MutableLiveData<>();

    private List<BeanSpecial> mListSpecialHuaChan = new ArrayList<>();
    private List<BeanSpecial> mListSpecialGanXiJiao = new ArrayList<>();


    {
        mListSpecialHuaChan.add(new BeanSpecial("yanghuagao", "氧化锆分析仪", R.drawable.ic_oxt3000, "OXT3000"));
        mListSpecialHuaChan.add(new BeanSpecial("dianbu", "电捕分析仪", R.drawable.ic_oxymat_61, "OXYMAT 61"));
        mListSpecialHuaChan.add(new BeanSpecial("SO2&O2", "SO2分析仪", R.drawable.ic_so2, "SO2分析仪:SERVOTOUGH  SpectraExact  2500 \n 分析仪:SERVOTOUGH OXY 1900"));
        mListSpecialHuaChan.add(new BeanSpecial("PH", "PH值分析仪", R.drawable.ic_cm442, "变送器:E+H CM442 \n 探头:CPS11D"));
        mListSpecialHuaChan.add(new BeanSpecial("H2SO4", "硫酸浓度计", R.drawable.ic_usc_3, "USC-III"));
    }

    public MutableLiveData<List<BeanSpecial>> getSpecialHuaChan() {
        mSpecialHuaChan.setValue(mListSpecialHuaChan);
        return mSpecialHuaChan;
    }

    public MutableLiveData<List<BeanSpecial>> getSpecialGanXiJiao() {
        mSpecialGanXiJiao.setValue(mListSpecialGanXiJiao);
        return mSpecialGanXiJiao;
    }
}