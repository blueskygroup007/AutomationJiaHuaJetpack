package com.bluesky.automationjiahua.ui.interlock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.database.InterLock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/1/1
 * Description:
 */
public class InterLockRecyclerViewAdapter extends RecyclerView.Adapter<InterLockRecyclerViewAdapter.InterLockViewHolder> {
    private final RecyclerView mRecyclerView;
    private List<InterLock> mData = new ArrayList<>();

    public InterLockRecyclerViewAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public InterLockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new InterLockViewHolder(inflater.inflate(R.layout.recyclerview_item_interlock, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InterLockViewHolder holder, int position) {
        InterLock interLock = mData.get(position);
        holder.tvNumber.setText(String.valueOf(interLock.getNumber()));
        holder.tvTag.setText(interLock.getTag());
        holder.tvName.setText(interLock.getDevice_name());
        holder.tvDomain.setText(interLock.getDomain());
        holder.root.setTag(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterLock interLock = mData.get((Integer) view.getTag());
                NavController controller = Navigation.findNavController(mRecyclerView);
                Bundle bundle = new Bundle();
                bundle.putSerializable("interlock", interLock);
                controller.navigate(R.id.action_interLockFragment_to_interlockDetailFragment, bundle);
            }
        });
    }

    public void setData(List<InterLock> data) {
        mData = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class InterLockViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvTag, tvDomain, tvName;
        CardView root;

        public InterLockViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.card_interlock_item);
            tvNumber = itemView.findViewById(R.id.tv_interlock_number);
            tvDomain = itemView.findViewById(R.id.tv_interlock_domain);
            tvName = itemView.findViewById(R.id.tv_interlock_device_name);
            tvTag = itemView.findViewById(R.id.tv_interlock_tag);
        }
    }
}
