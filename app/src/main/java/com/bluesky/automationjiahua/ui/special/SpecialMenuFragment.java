package com.bluesky.automationjiahua.ui.special;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.App;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.databinding.SpecialMenuFragmentBinding;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

public class SpecialMenuFragment extends Fragment {

    private SpecialMenuViewModel mViewModel;
    private SpecialMenuFragmentBinding mBinding;
    private BeanSpecial mBeanSpecial;

    public static SpecialMenuFragment newInstance() {
        return new SpecialMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SpecialMenuFragmentBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.special_menu_fragment, container, false);
        if (getArguments() != null) {
            mBeanSpecial = (BeanSpecial) getArguments().getSerializable("special");
        }
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
        if (mBeanSpecial != null) {
            AndroidTreeView treeView = new AndroidTreeView(requireContext(), getMenuTree(mBeanSpecial.getId()));
            treeView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
                @Override
                public void onClick(TreeNode node, Object value) {
                    mBinding.tvInfo.setText(((MenuTreeHolder.IconTreeItem) value).text);
                }
            });
            treeView.setDefaultAnimation(true);
            treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, false);

            mBinding.llContainerMenu.addView(treeView.getView());
        }

    }

    private void initData() {

    }

    private void initEvent() {

    }

    public TreeNode getMenuTree(String id) {
        switch (id) {
            case "yanghuagao":
                return getMenuTreeYangHuaGao();
            case "dianbu":
                return getMenuTreeDianBu();
            case "SO2&O2":
                return getMenuTreeSo2();
            case "PH":
                return getMenuTreePh();
            case "H2SO4":
                return getMenuTreeH2so4();
            default:
                return TreeNode.root();
        }
    }

    public TreeNode getMenuTreeYangHuaGao() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        //TODO 这里的IconTreeItem中的第一个参数icon图标，并没有用处，在MenuTreeHolder中被替换成箭头了
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "Main menu")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单02")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child11 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child12 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单02")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child13 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单03")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child14 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单04")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child21 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "三级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child22 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "三级菜单02")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child31 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "四级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child32 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "四级菜单02")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child41 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "五级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child51 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "六级菜单02")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child61 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "五级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child71 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "六级菜单02")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child81 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "五级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child91 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "六级菜单02")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child101 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "五级菜单01")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child111 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "六级菜单02")).setViewHolder(new MenuTreeHolder(context));
        child61.addChild(child71.addChild(child81.addChild(child91.addChild(child101.addChild(child111)))));
        child51.addChild(child61);
        child41.addChild(child51);
        child32.addChild(child41);
        child13.addChildren(child21, child22);
        child14.addChildren(child31, child32);
        child01.addChildren(child11, child12);
        child02.addChildren(child13, child14);

        parent.addChildren(child01, child02);
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeDianBu() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeSo2() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreePh() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeH2so4() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }
}