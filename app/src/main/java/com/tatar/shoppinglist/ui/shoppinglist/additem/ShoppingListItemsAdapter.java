package com.tatar.shoppinglist.ui.shoppinglist.additem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListItemsAdapter extends RecyclerView.Adapter<ShoppingListItemsAdapter.ViewHolder> {

    private List<ShoppingListItem> shoppingListItems;

    public ShoppingListItemsAdapter() {
        this.shoppingListItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item_row, parent, false);

        return new ShoppingListItemsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItems.get(position);

        holder.itemNameTv.setText(shoppingListItem.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemDot)
        TextView itemDot;

        @BindView(R.id.itemNameTv)
        TextView itemNameTv;

        @BindView(R.id.isCollectedCheckBox)
        CheckBox isCollectedCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
