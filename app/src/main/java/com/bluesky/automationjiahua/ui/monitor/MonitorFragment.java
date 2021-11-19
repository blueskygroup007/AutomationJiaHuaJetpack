package com.bluesky.automationjiahua.ui.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bluesky.automationjiahua.databinding.FragmentMonitorBinding;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;


public class MonitorFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private MonitorViewModel mMonitorViewModel;
    private FragmentMonitorBinding binding;
    private GridPictureAdapter mGridAdapter;
/*    private List<BeanPicture> mListPictureHuaChanMain = new ArrayList<>();
    private List<BeanPicture> mListPictureHuaChanZhiSuan = new ArrayList<>();

    {
        mListPictureHuaChanMain.add(new BeanPicture("chuleng", "初冷", R.drawable.chuleng));
        mListPictureHuaChanMain.add(new BeanPicture("dianbu", "电捕", R.drawable.dianbu));
        mListPictureHuaChanMain.add(new BeanPicture("gufengji", "鼓风机", R.drawable.gufengji));
        mListPictureHuaChanMain.add(new BeanPicture("jiaoyouanshui1", "焦油氨水1", R.drawable.jiaoyouanshui1));
        mListPictureHuaChanMain.add(new BeanPicture("jiaoyouanshui2", "焦油氨水2", R.drawable.jiaoyouanshui2));
        mListPictureHuaChanMain.add(new BeanPicture("liuan1", "硫铵1", R.drawable.liuan1));
        mListPictureHuaChanMain.add(new BeanPicture("liuan2", "硫铵2", R.drawable.liuan2));
        mListPictureHuaChanMain.add(new BeanPicture("tuoliu1", "脱硫1", R.drawable.tuoliu1));
        mListPictureHuaChanMain.add(new BeanPicture("tuoliu2", "脱硫2", R.drawable.tuoliu2));
        mListPictureHuaChanMain.add(new BeanPicture("xiaofangshui", "消防水", R.drawable.xiaofangshui));
        mListPictureHuaChanMain.add(new BeanPicture("xidita", "洗涤塔", R.drawable.xidita));
        mListPictureHuaChanMain.add(new BeanPicture("youku", "油库", R.drawable.youku));
        mListPictureHuaChanMain.add(new BeanPicture("zhengan", "蒸氨", R.drawable.zhengan));
        mListPictureHuaChanMain.add(new BeanPicture("zhonglengxiben", "终冷洗苯", R.drawable.zhonglengxiben));
    }

    {
        mListPictureHuaChanMain.add(new BeanPicture("yuchuli", "预处理", R.drawable.yuchuli));
        mListPictureHuaChanMain.add(new BeanPicture("fenshao1", "焚烧一", R.drawable.fenshao1));
        mListPictureHuaChanMain.add(new BeanPicture("fenshao2", "焚烧二", R.drawable.fenshao2));
        mListPictureHuaChanMain.add(new BeanPicture("jinghua", "净化", R.drawable.jinghua));
        mListPictureHuaChanMain.add(new BeanPicture("zhuanhua", "转化", R.drawable.zhuanhua));
        mListPictureHuaChanMain.add(new BeanPicture("ganxi1", "干吸一", R.drawable.ganxi1));
        mListPictureHuaChanMain.add(new BeanPicture("ganxi2", "干吸二", R.drawable.ganxi2));
    }*/


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mMonitorViewModel =
                new ViewModelProvider(this).get(MonitorViewModel.class);

        binding = FragmentMonitorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridAdapter = new GridPictureAdapter(mMonitorViewModel.getPictureHuaChanMain().getValue());
        binding.rvPicture.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        binding.rvPicture.setAdapter(mGridAdapter);
        //RadioGroup的默认选中,设置监听
        binding.rdGroup.check(binding.rbMain.getId());
        binding.rdGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(requireActivity(), Permission.RECORD_AUDIO) &&
                    XXPermissions.isGranted(requireActivity(), Permission.Group.CALENDAR)) {
                toast("用户已经在权限设置页授予了录音和日历权限");
            } else {
                toast("用户没有在权限设置页授予权限");
            }
        }
    }

    private void toast(String str) {
        Toast.makeText(requireActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //返回对应的数组(id,name,drawableId),设置给adapter
        if (i == binding.rbMain.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getPictureHuaChanMain().getValue());
        } else if (i == binding.rbZhisuan.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getPictureHuaChanZhiSuan().getValue());
        } else if (i == binding.rbGongfu.getId()) {

        } else if (i == binding.rbLiansuo.getId()) {

        }

/*        switch (i) {
            case binding.rbMain.getId():

                mGridAdapter.setData(galleryViewModel.getPictureHuaChanMain().getValue());

                break;
            case binding.rbZhisuan.getId():
                mGridAdapter.setData(galleryViewModel.getPictureHuaChanZhiSuan().getValue());
                break;
            case binding.rbGongfu.getId():

                break;
            case binding.rbLiansuo.getId():

                break;

            default:
        }*/
    }
}