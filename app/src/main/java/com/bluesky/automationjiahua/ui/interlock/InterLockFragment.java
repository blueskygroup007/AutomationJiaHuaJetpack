package com.bluesky.automationjiahua.ui.interlock;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.App;
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
                mAdapter.setData(interLocks);
                Log.e("setData:", interLocks.size() + "个");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //TODO 使用的是homefragment的search menu.
        inflater.inflate(R.menu.menu_fragment_home_search, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}