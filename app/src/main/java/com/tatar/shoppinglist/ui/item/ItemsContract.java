package com.tatar.shoppinglist.ui.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

public interface ItemsContract {

    interface ItemsView {
        void displayItems(List<Item> items);

        void notifyNewItemAdded(Item item);

        void notifyItemUpdated(int position, String name);

        void toggleEmptyItems();
    }

    interface ItemsPresenter {
        void loadItems();

        void addItem(String name);

        void updateItem(String id, String name, int position);

        void handleFabClick();

        void handleRecyclerViewItemClick(int position);

        List<Item> getItemList();
    }

}
