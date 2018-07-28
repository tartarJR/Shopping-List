package com.tatar.shoppinglist.ui.shoppinglist.additem;

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

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemPresenter;

public class ShoppingListItemsAdapter extends RecyclerView.Adapter<ShoppingListItemsAdapter.ViewHolder> {

    private List<ShoppingListItem> shoppingListItems;
    private AddItemPresenter addItemPresenter;

    public ShoppingListItemsAdapter(AddItemPresenter addItemPresenter) {
        this.addItemPresenter = addItemPresenter;
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
                    ShoppingListItem item = getShoppingListItem(getAdapterPosition());
                    addItemPresenter.updateIsCollectedForItem(item.getId(), isChecked);
                }
            });
        }
    }

}
