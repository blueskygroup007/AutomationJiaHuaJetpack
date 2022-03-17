package com.bluesky.automationjiahua.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.databinding.FragmentHomeBinding;
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
        //初始化FragmentViewModel
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        //初始化DeviceViewModel
        mViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(DeviceViewModel.class);
        //初始化列表适配器
        mAdapter = new DeviceRecyclerViewAdapter(binding.rvList, homeViewModel);
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        //确定Item的改变不会影响RecyclerView的宽高
        binding.rvList.setHasFixedSize(true);
        //设置适配器给列表
        binding.rvList.setAdapter(mAdapter);
        //设置列表的监听,以获取当前第一项的item的计数
        binding.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvList.getLayoutManager();
                    if (layoutManager != null) {
                        View topView = layoutManager.getChildAt(0);
                        if (topView != null) {
                            //获取与该view的顶部的偏移量
                            //lastOffset=topView.getTop();
                            //得到该view的数组位置
                            int lastPosition = layoutManager.getPosition(topView);
                            homeViewModel.setCurrentItem(lastPosition);
                        }
                    }
                }
            }
        });
        //当前查询结果的监听
        mViewModel.getLiveDataDevices().observe(getViewLifecycleOwner(), devices -> {
            binding.tvColumnTipDisplay.setText(String.valueOf(devices.size()));
            mAdapter.setData(devices);
        });
        //恢复下拉列表框选择项
        binding.spinnerQuerySearch.setSelection(homeViewModel.getSearch());
        binding.spinnerQueryDomain.setSelection(homeViewModel.getRange());
        //区域下拉列表
        binding.spinnerQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setRange(i);
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getRange()],
                        AppConstant.SEARCH[homeViewModel.getSearch()],
                        homeViewModel.getKeyWord().split(" "));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //范围下拉列表
        binding.spinnerQuerySearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeViewModel.setSearch(i);
                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getRange()],
                        AppConstant.SEARCH[homeViewModel.getSearch()],
                        homeViewModel.getKeyWord().split(" "));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("begin onResume:", "当前列表项保存值=" + homeViewModel.getCurrentItem());

        //TODO 恢复列表位置,只有用scrollToPositionWithOffset才能方便的将该位置置顶
        if (binding.rvList.getLayoutManager() != null) {
            ((LinearLayoutManager) binding.rvList.getLayoutManager()).scrollToPositionWithOffset(homeViewModel.getCurrentItem(), 0);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_home_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView mSearchView = (SearchView) item.getActionView();
        //设置是否显示搜索框展开时的提交按钮
        mSearchView.setSubmitButtonEnabled(true);
        //键盘上显示放大镜图标(默认)
        mSearchView.setImeOptions(3);
        mSearchView.setMaxWidth(1000);
        //当展开搜索栏,准备输入关键字时,填入上次搜索内容
        mSearchView.setOnSearchClickListener(view -> {
            int completeTextId = mSearchView.getResources().getIdentifier("android:id/search_src_text", null, null);
            AutoCompleteTextView completeTextView = mSearchView.findViewById(completeTextId);
            completeTextView.setText(homeViewModel.getSearchWords());
            completeTextView.setSelection(homeViewModel.getSearchWords().length());
        });
        //搜索栏关闭事件的监听处理
        mSearchView.setOnCloseListener(() -> {
            homeViewModel.setKeyWord("");
            mViewModel.findDevicesWithPattern(
                    AppConstant.DOMAIN[homeViewModel.getRange()],
                    AppConstant.SEARCH[homeViewModel.getSearch()],
                    new String[]{""});
            Log.e("setOnCloseListener", "搜索框关闭了!");

            return false;
        });

        //搜索栏查询事件的监听处理
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //一旦查询,列表位置的记录便清零
                homeViewModel.setCurrentItem(0);


                //每当搜索内容变化，更新HomeViewModel，并更新列表
                String keyWord = s.trim();
                homeViewModel.setKeyWord(keyWord);
                if (!keyWord.isEmpty()) {
                    String[] keyWords = keyWord.split(" ");

                    mViewModel.findDevicesWithPattern(
                            AppConstant.DOMAIN[homeViewModel.getRange()],
                            AppConstant.SEARCH[homeViewModel.getSearch()],
                            keyWords);
                }
                return false;

            }

            /*不采用即时输入即时搜索*/
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });

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