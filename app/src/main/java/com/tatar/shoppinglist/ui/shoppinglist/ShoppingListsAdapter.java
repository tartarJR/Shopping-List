package com.tatar.shoppinglist.ui.shoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {

    private List<ShoppingList> shoppingLists;

    public ShoppingListsAdapter() {
        this.shoppingLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_row, parent, false);

        return new ShoppingListsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListsAdapter.ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);

        holder.shoppingListNameTv.setText(shoppingList.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shoppingListNameTv)
        TextView shoppingListNameTv;

        @BindView(R.id.itemDot)
        TextView itemDot;

        public ViewHolder(View shoppingListsItemView) {
            super(shoppingListsItemView);
            ButterKnife.bind(this, shoppingListsItemView);
        }
    }

    public ShoppingList getShoppingList(int position) {
        return shoppingLists.get(position);
    }

    public void setshoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }
}
