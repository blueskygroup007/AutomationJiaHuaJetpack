package com.bluesky.automationjiahua.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.database.Device;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:
 */
public class DeviceRecyclerViewAdapter extends RecyclerView.Adapter<DeviceRecyclerViewAdapter.DeviceViewHolder> {
    private HomeViewModel mHomeViewModel;
    List<Device> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private OnItemClickListener mListener;

    public DeviceRecyclerViewAdapter(RecyclerView recyclerView, HomeViewModel mViewModel) {
        this.mRecyclerView = recyclerView;
        this.mListener = new OnItemClickListener();
        mHomeViewModel = mViewModel;
    }

    public void setData(List<Device> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DeviceViewHolder(inflater.inflate(R.layout.recyclerview_item_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeviceViewHolder holder, int position) {
        Device device = mData.get(position);
        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvTag.setText(device.getTag());
        holder.tvAffect.setText(device.getAffect());
        //TODO 最终实现应为：数据库中增加一项联锁字段，如果该字段为真，显示红色Lock，否则显示灰色Lock
        //或者:无联锁不显示lock.显示仪表类型.

        //holder.ivLock.setVisibility(View.VISIBLE);
        holder.ivLock.setImageResource(R.drawable.ic_baseline_lock_grey_24);
        //将position放到tag中,用于传递给Listener
        holder.root.setTag(position);
        //(优化)避免重复创建Listener
        holder.root.setOnClickListener(mListener);
    }

    class OnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Device device = mData.get((Integer) v.getTag());
            NavController controller = Navigation.findNavController(mRecyclerView);
            Bundle bundle = new Bundle();
            bundle.putSerializable("device", device);
            controller.navigate(R.id.action_nav_home_to_detailFragment, bundle);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvTag, tvAffect;
        CardView root;
        ImageView ivLock;

        public DeviceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.card_item);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvTag = itemView.findViewById(R.id.tv_tag);
            tvAffect = itemView.findViewById(R.id.tv_affect);
            ivLock = itemView.findViewById(R.id.iv_lock);
        }
    }

}
