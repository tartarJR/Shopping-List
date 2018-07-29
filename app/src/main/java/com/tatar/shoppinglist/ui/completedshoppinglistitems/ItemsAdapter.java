package com.tatar.shoppinglist.ui.completedshoppinglistitems;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<ShoppingListItem> shoppingListItems;

    public ItemsAdapter() {
        this.shoppingListItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_shopping_list_row, parent, false);
        return new ItemsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItems.get(position);

        holder.itemNameTv.setText(shoppingListItem.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));

        if (shoppingListItem.isCollected()) {
            holder.isCollectedTv.setText(R.string.collected_txt);
        } else {
            holder.isCollectedTv.setText(R.string.not_collected_txt);
        }
    }

    @Override
    public int getItemCount() {
        return shoppingListItems.size();
    }

    public ShoppingListItem getShoppingListItem(int position) {
        return shoppingListItems.get(position);
    }

    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
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
