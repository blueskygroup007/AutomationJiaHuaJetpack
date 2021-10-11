package com.bluesky.automationjiahua.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.atuomationjiahua.R;
import com.bluesky.atuomationjiahua.databinding.FragmentHomeBinding;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.viewmodel.DeviceViewModel;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    //TODO HomeViewModel用于保存当前fragment的状态和数据
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DeviceRecyclerViewAdapter mAdapter;
    //    private LiveData<List<Device>> mFilteredDevices;
    //TODO DeviceViewModel用于整个数据的存取
    private DeviceViewModel mViewModel;
    private SearchView mSearchView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        //TODO 这里使用的不是DataBinding，而是ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        //TODO 回退至home页面时，因为Navigation的原理，导致Fragment的重建，所以要保存上次查询结果到HomeViewModel中。
        //TODO 并在onViewCreate中加载。（当mFilteredDevices不为空时）
        //TODO "区域"和"搜索项"为什么没有回到初始位置呢？也强制回到初始位置吧。(在HomeViewModel中保存)
        //TODO 回退后，当前浏览位置也要恢复。

        //初始化FragmentViewModel
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        //初始化DeviceViewModel
        mViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(DeviceViewModel.class);

        mAdapter = new DeviceRecyclerViewAdapter(binding.rvList);
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.setAdapter(mAdapter);
        mViewModel.findDevicesWithPattern(AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                homeViewModel.getmKeyWord().getValue()).observe(getViewLifecycleOwner(),
                devices -> {
                    mAdapter.setData(devices);
                    mAdapter.notifyDataSetChanged();
                });
        //恢复界面元素
        binding.rvList.scrollToPosition(homeViewModel.getmCurrentItem().getValue());
        binding.spinnerQuerySearch.setSelection(homeViewModel.getmSearch().getValue());
        binding.spinnerQueryDomain.setSelection(homeViewModel.getmRange().getValue());
        //TODO 这里如何将keyword写到activity的toolbar上。
        //TODO ViewModel不应该是能保存数据么？为什么返回后，数据就空了呢？另外查一下Navigation中Fragment的回退，是否重建Fragment。
        //TODO 是不是因为数据库查询是异步的。所以，刚查询后，就获取livedata就是空！！！
        //mViewModel.findDevicesWithPattern(AppConstant.DOMAIN[homeViewModel.getmRange().getValue()], AppConstant.SEARCH[homeViewModel.getmSearch().getValue()], homeViewModel.getmKeyWord().getValue());
        //Log.e(HomeFragment.class.getSimpleName(), "DeviceViewModel.LiveData=" + mViewModel.getLiveDataDevices().getValue().size());


        //区域下拉列表
        binding.spinnerQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.getmRange().setValue(i);
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                        AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                        homeViewModel.getmKeyWord().getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //范围下拉列表
        binding.spinnerQuerySearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.getmSearch().setValue(i);
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                        AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                        homeViewModel.getmKeyWord().getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_home_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) item.getActionView();
        //搜索框展开
        mSearchView.setIconified(false);
        //获取搜索框的编辑框,并填入关键字,并全选
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText etSearch = mSearchView.findViewById(id);
        etSearch.setText(homeViewModel.getmKeyWord().getValue());
        etSearch.selectAll();
        mSearchView.setMaxWidth(1000);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //每当搜索内容变化，更新HomeViewModel，并更新列表
                String pattern = s.trim();
                homeViewModel.getmKeyWord().postValue(pattern);
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                        AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                        pattern);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                //TODO 搜索按钮的实现，没有必要？？？
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        //离开当前页面时，隐藏软键盘
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //***这里的getView也换成requireView***
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
    }
}