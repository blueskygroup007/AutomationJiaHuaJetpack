package com.bluesky.automationjiahua.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bluesky.atuomationjiahua.R;
import com.bluesky.atuomationjiahua.databinding.FragmentGalleryBinding;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private List<BeanPicture> mListPicture = new ArrayList<>();
    private GridPictureAdapter mGridAdapter;

    {
        int drawable = R.drawable.chuleng;

        mListPicture.add(new BeanPicture("chuleng", "初冷", R.drawable.chuleng));
        mListPicture.add(new BeanPicture("dianbu", "电捕", R.drawable.dianbu));
        mListPicture.add(new BeanPicture("gufengji", "鼓风机", R.drawable.gufengji));
        mListPicture.add(new BeanPicture("jiaoyouanshui1", "焦油氨水1", R.drawable.jiaoyouanshui1));
        mListPicture.add(new BeanPicture("jiaoyouanshui2", "焦油氨水2", R.drawable.jiaoyouanshui2));
        mListPicture.add(new BeanPicture("liuan1", "硫铵1", R.drawable.liuan1));
        mListPicture.add(new BeanPicture("liuan2", "硫铵2", R.drawable.liuan2));
        mListPicture.add(new BeanPicture("tuoliu1", "脱硫1", R.drawable.tuoliu1));
        mListPicture.add(new BeanPicture("tuoliu2", "脱硫2", R.drawable.tuoliu2));
        mListPicture.add(new BeanPicture("xiaofangshui", "消防水", R.drawable.xiaofangshui));
        mListPicture.add(new BeanPicture("xidita", "洗涤塔", R.drawable.xidita));
        mListPicture.add(new BeanPicture("youku", "油库", R.drawable.youku));
        mListPicture.add(new BeanPicture("zhengan", "蒸氨", R.drawable.zhengan));
        mListPicture.add(new BeanPicture("zhonglengxiben", "终冷洗苯", R.drawable.zhonglengxiben));
        mGridAdapter = new GridPictureAdapter(mListPicture);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
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
        binding.rvPicture.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        binding.rvPicture.setAdapter(mGridAdapter);

        //TODO 点击事件:使用图片浏览程序打开.(或在adapter中设置)

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

}