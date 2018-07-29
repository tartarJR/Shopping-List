package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayPresenter;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ShoppingListItem> shoppingListItems;
    private ItemDisplayPresenter itemDisplayPresenter;
    private OnItemCheckListener onItemCheckListener;

    public ItemAdapter(ItemDisplayPresenter itemDisplayPresenter, OnItemCheckListener onItemCheckListener) {
        this.itemDisplayPresenter = itemDisplayPresenter;
        this.onItemCheckListener = onItemCheckListener;
        this.shoppingListItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_shopping_list_item_row, parent, false);
        return new ItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItems.get(position);

        holder.itemNameTv.setText(shoppingListItem.getName());
        holder.itemDot.setText(Html.fromHtml("&#8226;"));
        holder.isCollectedCheckBox.setChecked(shoppingListItem.isCollected());
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

    public boolean isEveryItemCollected() {
        int counter = 0;

        for (ShoppingListItem shoppingListItem : shoppingListItems) {
            if (shoppingListItem.isCollected()) {
                counter++;
            }
        }

        return counter == shoppingListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.backgroundView)
        public RelativeLayout backgroundView;

        @BindView(R.id.foregroundView)
        public ConstraintLayout foregroundView;

        @BindView(R.id.itemDot)
        public TextView itemDot;

        @BindView(R.id.itemNameTv)
        public TextView itemNameTv;

        @BindView(R.id.isCollectedCheckBox)
        public CheckBox isCollectedCheckBox;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            isCollectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()) {
                        ShoppingListItem item = getShoppingListItem(getAdapterPosition());
                        itemDisplayPresenter.updateIsCollectedForItem(item.getId(), isChecked);
                        item.setCollected(isChecked);
                        onItemCheckListener.onItemCheck();
                    }
                }
            });
        }
    }

    public interface OnItemCheckListener {
        void onItemCheck();
    }
}
