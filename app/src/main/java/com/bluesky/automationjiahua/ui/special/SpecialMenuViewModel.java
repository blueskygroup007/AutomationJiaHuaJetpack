package com.bluesky.automationjiahua.ui.special;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bluesky.automationjiahua.R;
import com.unnamed.b.atv.model.TreeNode;

public class SpecialMenuViewModel extends AndroidViewModel {
    private final Context mContext;
    // TODO: Implement the ViewModel

    //TODO 好像也没有必要用LiveData保存菜单树的根节点。因为：1.不需要监听。2.不会被修改。
    private MutableLiveData<TreeNode> mCurrentTreeNode = new MutableLiveData<>();
    private TreeNode mTreeNode;

    public SpecialMenuViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
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

/*    public MutableLiveData<TreeNode> getMenuTree(String id) {
        TreeNode root = TreeNode.root();
        switch (id) {
            case "yanghuagao":
                root = getMenuTreeYangHuaGao();
                break;
            case "dianbu":
                root = getMenuTreeDianBu();
                break;
            case "SO2&O2":
                root = getMenuTreeSo2();
                break;
            case "PH":
                root = getMenuTreePh();
                break;
            case "H2SO4":
                root = getMenuTreeH2so4();
                break;

        }
        mCurrentTreeNode.setValue(root);

        return mCurrentTreeNode;
    }*/

    public TreeNode getMenuTreeYangHuaGao() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "Main menu")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单01")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "一级菜单02")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child11 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单01")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child12 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单02")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child13 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单03")).setViewHolder(new MenuTreeHolder(mContext));
        TreeNode child14 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "二级菜单04")).setViewHolder(new MenuTreeHolder(mContext));

        child01.addChildren(child11, child12);
        child02.addChildren(child13, child14);
        parent.addChildren(child01, child02);
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeDianBu() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(mContext));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeSo2() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(mContext));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreePh() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(mContext));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeH2so4() {
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入")).setViewHolder(new MenuTreeHolder(mContext));
        root.addChild(parent);
        return root;
    }
}