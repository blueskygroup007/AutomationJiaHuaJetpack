package com.bluesky.automationjiahua.ui.interlock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.database.InterLock;
import com.bluesky.automationjiahua.databinding.FragmentInterlockBinding;

import java.util.List;


public class InterLockFragment extends Fragment {

    private InterLockViewModel mViewModel;
    private FragmentInterlockBinding binding;
    private InterLockRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(InterLockViewModel.class);

        binding = FragmentInterlockBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(requireActivity()).get(InterLockViewModel.class);

        mAdapter = new InterLockRecyclerViewAdapter(binding.rvInterlockList);
        binding.rvInterlockList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvInterlockList.setHasFixedSize(true);
        binding.rvInterlockList.setAdapter(mAdapter);
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<InterLock>>() {
            @Override
            public void onChanged(List<InterLock> interLocks) {
                if (interLocks != null) {
                    mAdapter.setData(interLocks);
                    binding.tvInterlockColumnTipDisplay.setText(String.valueOf(interLocks.size()));
                    //Log.e("setData:", interLocks.size() + "个");
                }
            }
        });
        //恢复界面元素
        binding.spinnerInterlockQueryDomain.setSelection(mViewModel.getSpinnerDomainPosition());

        //下拉列表点击监听
        binding.spinnerInterlockQueryDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setSpinnerDomainPosition(position);
                String strSearch = getResources().getStringArray(R.array.spinner_query_domain_interlock)[position];
                Log.e("spinner search:", strSearch);
                DeviceRepository.getInstance().getInterLocksByDomain("%" + strSearch + "%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}