package com.bluesky.automationjiahua.ui.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bluesky.automationjiahua.databinding.FragmentSpecialBinding;

public class SpecialFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private SpecialViewModel mViewModel;
    private FragmentSpecialBinding mBinding;
    private GridSpecialAdapter mGridAdapter;

    public static SpecialFragment newInstance() {
        return new SpecialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentSpecialBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialViewModel.class);
        // TODO: Use the ViewModel
        initView();
        initData();
        initEvent();
    }


    private void initView() {
        mGridAdapter = new GridSpecialAdapter(mBinding.rvSpecialDevices, mViewModel.getSpecialHuaChan().getValue());
        mBinding.rvSpecialDevices.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        mBinding.rvSpecialDevices.setAdapter(mGridAdapter);
        //RadioGroup的默认选中,设置监听
        mBinding.rdGroupSpecial.check(mBinding.rbHuachanSpecial.getId());
        mBinding.rdGroupSpecial.setOnCheckedChangeListener(this);

    }

    private void initData() {

    }

    private void initEvent() {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mBinding.rbHuachanSpecial.getId()) {
            mGridAdapter.setData(mViewModel.getSpecialHuaChan().getValue());
        } else if (checkedId == mBinding.rbGanxijiaoSpecial.getId()) {
            mGridAdapter.setData(mViewModel.getSpecialGanXiJiao().getValue());
        }
    }
}