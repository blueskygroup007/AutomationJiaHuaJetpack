package com.bluesky.automationjiahua.ui.monitor;

import androidx.lifecycle.ViewModel;

import com.bluesky.automationjiahua.R;

import java.util.ArrayList;
import java.util.List;

public class MonitorViewModel extends ViewModel {

/*    private MutableLiveData<List<BeanPicture>> mPictureHuaChanMain = new MutableLiveData<>();

    private MutableLiveData<List<BeanPicture>> mPictureHuaChanZhiSuan = new MutableLiveData<>();*/


    private List<BeanPicture> mListPicHuaChanMain = new ArrayList<>();
    private List<BeanPicture> mListPicHuaChanZhiSuan = new ArrayList<>();
    private List<BeanPicture> mListPicGanXiJiao = new ArrayList<>();
    private List<BeanPicture> mListPicJiaoLu = new ArrayList<>();



    {
        mListPicHuaChanMain.add(new BeanPicture("chuleng", "初冷", R.drawable.chuleng));
        mListPicHuaChanMain.add(new BeanPicture("dianbu", "电捕", R.drawable.dianbu));
        mListPicHuaChanMain.add(new BeanPicture("gufengji", "鼓风机", R.drawable.gufengji));
        mListPicHuaChanMain.add(new BeanPicture("jiaoyouanshui1", "焦油氨水1", R.drawable.jiaoyouanshui1));
        mListPicHuaChanMain.add(new BeanPicture("jiaoyouanshui2", "焦油氨水2", R.drawable.jiaoyouanshui2));
        mListPicHuaChanMain.add(new BeanPicture("liuan1", "硫铵1", R.drawable.liuan1));
        mListPicHuaChanMain.add(new BeanPicture("liuan2", "硫铵2", R.drawable.liuan2));
        mListPicHuaChanMain.add(new BeanPicture("tuoliu1", "脱硫1", R.drawable.tuoliu1));
        mListPicHuaChanMain.add(new BeanPicture("tuoliu2", "脱硫2", R.drawable.tuoliu2));
        mListPicHuaChanMain.add(new BeanPicture("xiaofangshui", "消防水", R.drawable.xiaofangshui));
        mListPicHuaChanMain.add(new BeanPicture("xidita", "洗涤塔", R.drawable.xidita));
        mListPicHuaChanMain.add(new BeanPicture("youku", "油库", R.drawable.youku));
        mListPicHuaChanMain.add(new BeanPicture("zhengan", "蒸氨", R.drawable.zhengan));
        mListPicHuaChanMain.add(new BeanPicture("zhonglengxiben", "终冷洗苯", R.drawable.zhonglengxiben));
    }

    {
        mListPicHuaChanZhiSuan.add(new BeanPicture("yuchuli", "预处理", R.drawable.yuchuli));
        mListPicHuaChanZhiSuan.add(new BeanPicture("fenshao1", "焚烧一", R.drawable.fenshao1));
        mListPicHuaChanZhiSuan.add(new BeanPicture("fenshao2", "焚烧二", R.drawable.fenshao2));
        mListPicHuaChanZhiSuan.add(new BeanPicture("jinghua", "净化", R.drawable.jinghua));
        mListPicHuaChanZhiSuan.add(new BeanPicture("zhuanhua", "转化", R.drawable.zhuanhua));
        mListPicHuaChanZhiSuan.add(new BeanPicture("ganxi1", "干吸一", R.drawable.ganxi1));
        mListPicHuaChanZhiSuan.add(new BeanPicture("ganxi2", "干吸二", R.drawable.ganxi2));
    }

    public MonitorViewModel() {
    }

    public List<BeanPicture> getListPicHuaChanMain() {
        return mListPicHuaChanMain;
    }

    public List<BeanPicture> getListPicHuaChanZhiSuan() {
        return mListPicHuaChanZhiSuan;
    }

    public List<BeanPicture> getListPicGanXiJiao() {
        return mListPicGanXiJiao;
    }

    public List<BeanPicture> getListPicJiaoLu() {
        return mListPicJiaoLu;
    }

    /*    public MutableLiveData<List<BeanPicture>> getPictureHuaChanMain() {
        mPictureHuaChanMain.setValue(mListPicHuaChanMain);
        return mPictureHuaChanMain;
    }


    public MutableLiveData<List<BeanPicture>> getPictureHuaChanZhiSuan() {
        mPictureHuaChanZhiSuan.setValue(mListPicHuaChanZhiSuan);
        return mPictureHuaChanZhiSuan;
    }*/
}