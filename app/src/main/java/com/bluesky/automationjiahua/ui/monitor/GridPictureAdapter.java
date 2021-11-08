package com.bluesky.automationjiahua.ui.monitor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
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
 * @date 2021/10/11
 * Description:
 */
public class GridPictureAdapter extends RecyclerView.Adapter<GridPictureAdapter.ViewHolder> {
    private List<BeanPicture> mData;
    private Context mContext;

    public GridPictureAdapter(List<BeanPicture> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_picture, null);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO 这里应该取缩略图,查看内存占用，并优化
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mData.get(position).getPicture());
        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);
        holder.ivPicture.setImageBitmap(thumbnail);
        holder.tvName.setText(mData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mData.get(holder.getAdapterPosition()).getPicture());

                String uriString = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, null, null);
                Uri uri = Uri.parse(uriString);
                intent.setDataAndType(uri, "image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<BeanPicture> data) {
        mData = data;
        notifyDataSetChanged();
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
