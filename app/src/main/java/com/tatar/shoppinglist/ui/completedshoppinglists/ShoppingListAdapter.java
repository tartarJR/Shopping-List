package com.tatar.shoppinglist.ui.completedshoppinglists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<RemoteShoppingList> remoteShoppingLists;

    public ShoppingListAdapter() {
        this.remoteShoppingLists = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_row, parent, false);
        return new ShoppingListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        RemoteShoppingList remoteShoppingList = remoteShoppingLists.get(position);

        holder.shoppingListNameTv.setText(remoteShoppingList.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));
    }

    @Override
    public int getItemCount() {
        return remoteShoppingLists.size();
    }

    public RemoteShoppingList getShoppingList(int position) {
        return remoteShoppingLists.get(position);
    }

    public void setRemoteShoppingLists(List<RemoteShoppingList> remoteShoppingLists) {
        this.remoteShoppingLists = remoteShoppingLists;
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
