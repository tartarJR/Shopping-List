package com.tatar.shoppinglist.ui.completedshoppinglistitems;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<RemoteShoppingListItem> remoteShoppingListItems;

    public ItemAdapter() {
        this.remoteShoppingListItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_shopping_list_item_row, parent, false);
        return new ItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        RemoteShoppingListItem remoteShoppingListItem = remoteShoppingListItems.get(position);

        holder.itemNameTv.setText(remoteShoppingListItem.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));

        if (remoteShoppingListItem.isCollected()) {
            holder.isCollectedTv.setText(R.string.collected_txt);
        } else {
            holder.isCollectedTv.setText(R.string.not_collected_txt);
        }
    }

    @Override
    public int getItemCount() {
        return remoteShoppingListItems.size();
    }

    public RemoteShoppingListItem getShoppingListItem(int position) {
        return remoteShoppingListItems.get(position);
    }

    public void setRemoteShoppingListItems(List<RemoteShoppingListItem> remoteShoppingListItems) {
        this.remoteShoppingListItems = remoteShoppingListItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemDot)
        public TextView itemDot;

        @BindView(R.id.itemNameTv)
        public TextView itemNameTv;

        @BindView(R.id.isCollectedTv)
        public TextView isCollectedTv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
