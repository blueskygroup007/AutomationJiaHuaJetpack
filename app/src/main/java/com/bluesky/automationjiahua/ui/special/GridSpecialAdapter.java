package com.bluesky.automationjiahua.ui.special;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.automationjiahua.R;

import java.util.List;

/**
 * @author BlueSky
 * @date 2021/12/22
 * Description:
 */
public class GridSpecialAdapter extends RecyclerView.Adapter<GridSpecialAdapter.ViewHolder> {
    private List<BeanSpecial> mData;
    private Context mContext;

    public GridSpecialAdapter(List<BeanSpecial> data) {
        mData = data;
    }

    public void setData(List<BeanSpecial> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_special, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeanSpecial bean = mData.get(position);
        holder.ivPicture.setImageResource(bean.getPicture());
        holder.tvName.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvName;
        //使用该方法来获取根布局,用于实现点击事件
        View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            ivPicture = itemView.findViewById(R.id.iv_item_picture_pic);
            tvName = itemView.findViewById(R.id.tv_item_picture_name);
        }
    }
}
