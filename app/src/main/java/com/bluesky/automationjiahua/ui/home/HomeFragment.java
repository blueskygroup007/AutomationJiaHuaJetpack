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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.atuomationjiahua.R;
import com.bluesky.atuomationjiahua.databinding.FragmentHomeBinding;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.viewmodel.DeviceViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragment extends Fragment {

    //TODO HomeViewModel用于保存当前fragment的状态和数据
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DeviceRecyclerViewAdapter mAdapter;
    //    private LiveData<List<Device>> mFilteredDevices;
    //TODO DeviceViewModel用于整个数据的存取
    private DeviceViewModel mViewModel;
//    private String mDomainPattern;
//    private String mColumnPattern;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        //TODO 这里使用的不是DataBinding，而是ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化界面
        binding.spinnerQuerySearch.setSelection(homeViewModel.getmSearch());
        binding.spinnerQueryDomain.setSelection(homeViewModel.getmDomain());
        binding.rvList.scrollToPosition(homeViewModel.getmCurrentItem());
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
/*        mViewModel.getLiveDataDevices().observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                mAdapter.setData(devices);
                mAdapter.notifyDataSetChanged();
            }
        });*/

        //区域下拉列表
        binding.spinnerQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setmDomain(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //范围下拉列表
        binding.spinnerQuerySearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setmSearch(i);
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
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //每当搜索内容变化，更新HomeViewModel，并更新列表
                String pattern = s.trim();
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getmDomain()],
                        AppConstant.SEARCH[homeViewModel.getmSearch()],
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