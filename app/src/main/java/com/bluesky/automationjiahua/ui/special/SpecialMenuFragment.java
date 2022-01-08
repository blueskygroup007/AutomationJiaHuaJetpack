package com.bluesky.automationjiahua.ui.special;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
                    mBinding.tvInfo.setText(((MenuTreeHolder.IconTreeItem) value).info);
                }
            });
            treeView.setDefaultAnimation(true);
            treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, false);

            mBinding.llContainerMenu.addView(treeView.getView());
        }
        mBinding.tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());

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
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "OXYGEN    20.60%", "氧气含量(例如20.60%)")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "TEMP    750℃", "温度(例如750℃)")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "CELL    -10mV", "锆池常数")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child03 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "OUTPUT    20.00mA", "输出电流")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "MAINT.STATE", "维护状态")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child05 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "ALARM INFO.", "报警信息")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child04_01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "CODE", "输入密码")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Change code", "更改密码")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_03 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Const  -08.00mV", "修改锆池常数")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_04 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Slope  48.00mV", "修改锆池斜率")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_05 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "High Gas  20.60%", "修改高点标气值")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_06 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Low Gas  2.00%", "修改低点标气值")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_07 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Range  21.00%", "修改氧量测量范围")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_08 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Maint.Hold", "进入维护保持子菜单")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_09 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Fault Out  OFF", "选择故障输出是否打开")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_10 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "H2O Correct  0%", "修改烟气水分含量")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_11 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Cell Res  0Ω", "查看锆池内阻")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_12 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Calibration", "进入标定子菜单")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_13 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "sv06.1.0.02", "查看软件版本")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child04_08_01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Maint Hold  ON/OFF", "OFF时,任何状态下都不执行输出保持;\nON时,系统按当前检测氧量值执行输出保持.")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_08_02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Hold Time  60M", "保持时间(倒计时)默认60分钟")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child04_12_01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Single cal.", "单点标定")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_12_02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24, "Double cal.", "双点标定")).setViewHolder(new MenuTreeHolder(context));

        TreeNode child04_12_01_01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24,
                "InputGas  20.60%",
                "通入标气:\n标气流量为2.0~3.0 L/min;Enter键确认开始标定\n锆池电压:\n波动不超过±0.5mV为稳定;6分钟不稳定标定失败(Cal.fault)\n如果锆池常数超出正常范围,不保存数据,报常数错误(Const error)")).setViewHolder(new MenuTreeHolder(context));


        TreeNode child04_12_02_01 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24,
                "InputGas  20.60%",
                "通入高点标气:\n标气流量为2.0~3.0 L/min;Enter键确认开始标定\n锆池电压:\n波动不超过±0.5mV为稳定;6分钟不稳定标定失败(Cal.fault)\n如果锆池常数超出正常范围,不保存数据,报常数错误(Const error)")).setViewHolder(new MenuTreeHolder(context));
        TreeNode child04_12_02_02 = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_menu_24,
                "InputGas  2.00%",
                "通入低点标气:\n标气流量为2.0~3.0 L/min;Enter键确认开始标定\n锆池电压:\n波动不超过±0.5mV为稳定;6分钟不稳定标定失败(Cal.fault)\n如果锆池常数超出正常范围,不保存数据,报常数错误(Const error)" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
                        + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
                        + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
                        + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")).setViewHolder(new MenuTreeHolder(context));

        child04_12_02.addChildren(child04_12_02_01, child04_12_02_02);
        child04_12_01.addChild(child04_12_01_01);
        child04_12.addChildren(child04_12_01, child04_12_02);
        child04_08.addChildren(child04_08_01, child04_08_02);
        child04.addChildren(child04_01, child04_02, child04_03, child04_04, child04_05, child04_06, child04_07, child04_08, child04_09, child04_10, child04_11, child04_12, child04_13);
        parent.addChildren(child01, child02, child03, child04, child05);
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeDianBu() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入", "电捕氧分析仪菜单")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeSo2() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入", "二氧化硫分析仪菜单")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreePh() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入", "PH计变送器菜单")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }

    public TreeNode getMenuTreeH2so4() {
        Context context = requireContext();
        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode(new MenuTreeHolder.IconTreeItem(R.drawable.ic_baseline_format_indent_increase_24, "该菜单还未录入", "硫酸浓度计菜单")).setViewHolder(new MenuTreeHolder(context));
        root.addChild(parent);
        return root;
    }
}