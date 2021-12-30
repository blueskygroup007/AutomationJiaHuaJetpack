package com.bluesky.automationjiahua.ui.special;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluesky.automationjiahua.R;
import com.unnamed.b.atv.model.TreeNode;

/**
 * @author BlueSky
 * @date 2021/12/21
 * Description:
 */
public class MenuTreeHolder extends TreeNode.BaseNodeViewHolder<MenuTreeHolder.IconTreeItem> {

    private ImageView arrowView;

    public MenuTreeHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_menu_tree_node, null, false);
        arrowView = view.findViewById(R.id.iv_node_icon);
        TextView tvText = view.findViewById(R.id.tv_node_text);
        arrowView.setImageResource(R.drawable.ic_baseline_arrow_right_24);
        tvText.setText(value.text);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.INVISIBLE);
        }

        return view;
    }


    @Override
    public void toggle(boolean active) {
        super.toggle(active);
        arrowView.setImageResource(active ? R.drawable.ic_baseline_arrow_drop_down_24 : R.drawable.ic_baseline_arrow_right_24);
    }


    public static class IconTreeItem {
        int icon;
        String text;
        String info;

        public IconTreeItem(int icon, String text, String info) {
            this.icon = icon;
            this.text = text;
            this.info = info;
        }
    }


}
