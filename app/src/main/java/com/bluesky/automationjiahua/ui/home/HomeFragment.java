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
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //TODO HomeViewModel用于保存当前fragment的状态和数据
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DeviceRecyclerViewAdapter mAdapter;
    //    private LiveData<List<Device>> mFilteredDevices;
    //TODO DeviceViewModel用于整个数据的存取
    private DeviceViewModel mViewModel;
    private ArrayAdapter<String> mHistoryAdapter;
    private List<String> mFiveHistory = new ArrayList<>();


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

        mAdapter = new DeviceRecyclerViewAdapter(binding.rvList, homeViewModel);
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        //(优化)确定Item的改变不会影响RecyclerView的宽高
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setAdapter(mAdapter);
        //设置列表的监听,以获取当前第一项的item的计数
        binding.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.rvList.getLayoutManager();
                    View topView = layoutManager.getChildAt(0);
                    if (topView != null) {
                        //获取与该view的顶部的偏移量
                        //lastOffset=topView.getTop();
                        //得到该view的数组位置
                        int lastPosition = layoutManager.getPosition(topView);
                        homeViewModel.getmCurrentItem().setValue(lastPosition);
                    }
                }
            }
        });
        //TODO 也不一定起作用.mDeviceRepository.findDeviceByPattern()方法可能根本没起作用
        mViewModel.findDevicesWithPattern(AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                homeViewModel.getmKeyWord().getValue()).observe(getViewLifecycleOwner(),
                devices -> {
                    binding.tvColumnTipDisplay.setText(String.valueOf(devices.size()));
                    mAdapter.setData(devices);
                });
        //---恢复界面元素---
        //恢复列表位置
/*        Log.e("begin onViewCreage:", "当前列表项保存值=" + homeViewModel.getmCurrentItem().getValue());

        if (binding.rvList.getLayoutManager() != null && homeViewModel.getmCurrentItem().getValue() >= 0) {
            ((LinearLayoutManager) binding.rvList.getLayoutManager()).scrollToPosition(homeViewModel.getmCurrentItem().getValue());
        }*/
        //恢复下拉列表框选择项
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
    public void onResume() {
        super.onResume();
/*        Log.e("begin onResume:", "当前列表项保存值=" + homeViewModel.getmCurrentItem().getValue());
        binding.rvList.scrollToPosition(homeViewModel.getmCurrentItem().getValue());
        if (binding.rvList.getLayoutManager() != null && homeViewModel.getmCurrentItem().getValue() >= 0) {
            ((LinearLayoutManager) binding.rvList.getLayoutManager()).scrollToPosition(homeViewModel.getmCurrentItem().getValue());
        }*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_home_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView mSearchView = (SearchView) item.getActionView();
        //用上次的关键字填充搜索栏,并全选,并给与焦点.
        /*if (homeViewModel.getmKeyWord().getValue() != null) {
            //搜索框展开
            mSearchView.setIconified(false);
            //获取搜索框的编辑框,并填入关键字,并全选
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            EditText etSearch = mSearchView.findViewById(id);
            String[] keyWords = homeViewModel.getmKeyWord().getValue();
            StringBuilder keyWord = new StringBuilder();
            for (String str :
                    keyWords) {
                keyWord.append(str);
                keyWord.append(" ");
            }

            etSearch.setText(keyWord.toString().trim());
            etSearch.selectAll();
        }*/

        //TODO 搜索栏历史记录实现
        int completeTextId = mSearchView.getResources().getIdentifier("android:id/search_src_text", null, null);
        AutoCompleteTextView completeTextView = mSearchView.findViewById(completeTextId);

        //历史数组只截取5个(或者不截取,历史数组只保存5个即可)
        mFiveHistory = homeViewModel.getHistory().subList(0, Math.min(homeViewModel.getHistory().size(), 5));
        mHistoryAdapter = new ArrayAdapter<>(requireContext(), R.layout.item_history, R.id.tv_item_history, mFiveHistory);
        completeTextView.setAdapter(mHistoryAdapter);
        completeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //给搜索栏填充搜索关键字,并提交
                mSearchView.setQuery(mFiveHistory.get(i), true);
            }
        });
        //输入长度为0时显示提示列表
        completeTextView.setThreshold(0);

        mSearchView.setMaxWidth(1000);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //历史中是否已经包含
                if (mFiveHistory.contains(s)) {
                    return false;
                }
                //在头部加入关键字,如果超出,尾部去掉
                mFiveHistory.add(0, s);
                if (mFiveHistory.size() > 5) {
                    mFiveHistory.remove(mFiveHistory.size() - 1);
                }
                //保存在ViewModel中
                homeViewModel.setHistory(mFiveHistory);
                //重新生成adapter,并刷新列表
                mHistoryAdapter = new ArrayAdapter<>(requireContext(), R.layout.item_history, R.id.tv_item_history, mFiveHistory);
                int completeTextId = mSearchView.getResources().getIdentifier("android:id/search_src_text", null, null);
                AutoCompleteTextView completeTextView = mSearchView.findViewById(completeTextId);
                completeTextView.setAdapter(mHistoryAdapter);
                mHistoryAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //每当搜索内容变化，更新HomeViewModel，并更新列表
                String keyWord = s.trim();
                String[] keyWords = keyWord.split(" ");
                homeViewModel.getmKeyWord().postValue(keyWords);

                mViewModel.findDevicesWithPattern(
                        AppConstant.DOMAIN[homeViewModel.getmRange().getValue()],
                        AppConstant.SEARCH[homeViewModel.getmSearch().getValue()],
                        keyWords);
                return true;
            }
        });
    }


/*    public class HistoryArrayAdapter extends BaseAdapter {

        private List<String> mData;

        public HistoryArrayAdapter() {
        }

        public void setData(List<String> data) {
            this.mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int i) {
            return mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            //将布局文件转化为View对象

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, null, false);

            *//**
     * 找到item布局文件中对应的控件
     *//*
            TextView tvContent = view.findViewById(R.id.tv_item_history);

            //获取相应索引的ItemBean对象

            */

    /**
     * 设置控件的对应属性值
     *//*

            return view;
        }
    }*/
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