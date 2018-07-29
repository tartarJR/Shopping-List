package com.tatar.shoppinglist.ui.completedshoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedShoppingListAdapter extends RecyclerView.Adapter<CompletedShoppingListAdapter.ViewHolder> {

    private List<ShoppingList> shoppingLists;

    public CompletedShoppingListAdapter() {
        this.shoppingLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public CompletedShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_row, parent, false);
        return new CompletedShoppingListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedShoppingListAdapter.ViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);

        holder.shoppingListNameTv.setText(shoppingList.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public ShoppingList getShoppingList(int position) {
        return shoppingLists.get(position);
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemDot)
        public TextView itemDot;

        @BindView(R.id.shoppingListNameTv)
        public TextView shoppingListNameTv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
