package com.bluesky.automationjiahua.ui.detail;

import static com.bluesky.automationjiahua.base.App.DETAIL_PAGE_SIMPLIFY;
import static com.bluesky.automationjiahua.base.App.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.App;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.databinding.FragmentDetailBinding;
import com.bluesky.automationjiahua.viewmodel.DeviceViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private DeviceViewModel mViewModel;
    private FragmentDetailBinding mBinding;
    private Device mDevice;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mDevice = (Device) getArguments().getSerializable("device");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_detail, menu);

        MenuItem itemSimple = menu.findItem(R.id.menu_item_detail_simple);
        SwitchCompat aSwitch = (SwitchCompat) itemSimple.getActionView();
        aSwitch.setTextOff("全");
        aSwitch.setTextOn("简");
        aSwitch.setShowText(true);

        aSwitch.setChecked(DETAIL_PAGE_SIMPLIFY);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                App.instance.setSimply(isChecked);
                showOrHideDetail(isChecked ? View.GONE : View.VISIBLE);
            }
        });

    }

    public void showOrHideDetail(int detail) {
        mBinding.tvDetailContentStandard.setVisibility(detail);
        mBinding.tvDetailContentMode.setVisibility(detail);
        mBinding.tvDetailContentPipe.setVisibility(detail);
        mBinding.tvDetailContentType.setVisibility(detail);
        mBinding.tvDetailContentInstall.setVisibility(detail);
        mBinding.tvDetailContentFactory.setVisibility(detail);
        mBinding.tvDetailContentRemark.setVisibility(detail);
        mBinding.tvDetailContentBrand.setVisibility(detail);
        mBinding.tvDetailContentDate.setVisibility(detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //必须加这一行代码才能有菜单
        setHasOptionsMenu(true);

        mViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication())
                .create(DeviceViewModel.class);
        Log.e(TAG, "中文domain=" + App.DOMAIN_DISPLAY.get(mDevice.getDomain()));
        Log.e(TAG, "getDomain=" + mDevice.getDomain());
        mBinding.tvDetailContentDomain.setText(App.DOMAIN_DISPLAY.get(mDevice.getDomain()));
        mBinding.tvDetailContentTag.setText(mDevice.getTag());
        mBinding.tvDetailContentAffect.setText(mDevice.getAffect());
        mBinding.tvDetailContentParameter.setText(mDevice.getParameter());
        mBinding.tvDetailContentName.setText(mDevice.getName());
        mBinding.tvDetailContentRange.setText(mDevice.getRange());

        mBinding.tvDetailContentCount.setText(mDevice.getCount());

        showOrHideDetail(DETAIL_PAGE_SIMPLIFY ? View.GONE : View.VISIBLE);

        mBinding.tvDetailContentStandard.setText(mDevice.getStandard());
        mBinding.tvDetailContentMode.setText(mDevice.getMode());
        mBinding.tvDetailContentPipe.setText(mDevice.getPipe());
        mBinding.tvDetailContentType.setText(mDevice.getType());
        mBinding.tvDetailContentInstall.setText(mDevice.getInstall());
        mBinding.tvDetailContentFactory.setText(mDevice.getFactory());
        mBinding.tvDetailContentRemark.setText(mDevice.getRemark());
        mBinding.tvDetailContentBrand.setText(mDevice.getBrand());
        mBinding.tvDetailContentDate.setText(mDevice.getDate());
    }
}