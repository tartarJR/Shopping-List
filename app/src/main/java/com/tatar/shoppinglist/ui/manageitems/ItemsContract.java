package com.tatar.shoppinglist.ui.manageitems;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ItemsContract {

    interface ItemsView extends BaseView {
        void displayItems(List<Item> items);

        void showItemCreatedMessage();

        void showItemUpdatedMessage();

        void showItemDeletedMessage();

        void showValidationMessage();
    }

    interface ItemsPresenter {
        void loadItems();

        void createItem(String name);

        void updateItem(String id, String name);

        void deleteItem(String id);
    }

}
