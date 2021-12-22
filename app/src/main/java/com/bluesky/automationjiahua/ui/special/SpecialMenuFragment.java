package com.bluesky.automationjiahua.ui.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.databinding.SpecialMenuFragmentBinding;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

public class SpecialMenuFragment extends Fragment {

    private SpecialMenuViewModel mViewModel;
    private SpecialMenuFragmentBinding mBinding;

    public static SpecialMenuFragment newInstance() {
        return new SpecialMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SpecialMenuFragmentBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.special_menu_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SpecialMenuViewModel.class);
        // TODO: Use the ViewModel
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "Main menu")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单01")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单02")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child11 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单01")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child12 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单02")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child13 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单03")).setViewHolder(new MenuTreeHolder(requireContext()));
        TreeNode child14 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单04")).setViewHolder(new MenuTreeHolder(requireContext()));

        child01.addChildren(child11, child12);
        child02.addChildren(child13, child14);
        parent.addChildren(child01, child02);
        root.addChild(parent);
        AndroidTreeView treeView = new AndroidTreeView(requireContext(), root);
        treeView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                mBinding.tvInfo.setText(((MenuTreeHolder.IconTreeItem) value).text);
            }
        });
        treeView.setDefaultAnimation(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyle, false);

        mBinding.llContainerMenu.addView(treeView.getView());
    }

    private void initData() {

    }

    private void initEvent() {

    }

}