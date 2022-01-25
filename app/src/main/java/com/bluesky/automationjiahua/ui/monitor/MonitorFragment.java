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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mMonitorViewModel =
                new ViewModelProvider(this).get(MonitorViewModel.class);

        binding = FragmentMonitorBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridAdapter = new GridPictureAdapter(mMonitorViewModel.getListPicHuaChanMain());
        binding.rvPictureMonitor.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        binding.rvPictureMonitor.setAdapter(mGridAdapter);
        //RadioGroup的默认选中,设置监听
        binding.rdGroupMonitor.check(binding.rbHuachanMonitor.getId());
        binding.rdGroupMonitor.setOnCheckedChangeListener(this);
    }


    /**
     * 该方法已经过时,官方建议使用Activity Results API
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
        if (i == binding.rbHuachanMonitor.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getListPicHuaChanMain());
        } else if (i == binding.rbZhisuanMonitor.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getListPicHuaChanZhiSuan());
        } else if (i == binding.rbGanxijiaoMonitor.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getListPicGanXiJiao());
        } else if (i == binding.rbJiaoluMonitor.getId()) {
            mGridAdapter.setData(mMonitorViewModel.getListPicJiaoLu());
        }
    }
}