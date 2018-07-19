package com.tatar.shoppinglist.ui.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

public interface ItemsContract {

    interface ItemsView {
        void displayItems(List<Item> items);

        void toggleNoItemsTv();

        void displayMessage(String message);

        void notifyNewItemCreated(Item item);

        void notifyItemUpdated(int position, String name);

        void notifyItemDeleted(int position);
    }

    interface ItemsPresenter {
        void loadItems();

        void createItem(String name);

        void updateItem(String id, String name, int position);

        void deleteItem(String id, int position);

        void displayAddOrUpdateItemDialog();

        void displayActionsDialog(int position);

        List<Item> getItemList();
    }

}
