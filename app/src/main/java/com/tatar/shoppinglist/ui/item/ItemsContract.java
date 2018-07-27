package com.tatar.shoppinglist.ui.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

public interface ItemsContract {

    interface ItemsView {
        void displayItems(List<Item> items);

        void toggleNoItemsTv(int size);

        void displayMessage(String message);

        void toggleProgressBar();
    }

    interface ItemsPresenter {
        void loadItems();

        void createItem(String name);

        void updateItem(String id, String name);

        void deleteItem(String id);
    }

}
