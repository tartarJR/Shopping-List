package com.tatar.shoppinglist.ui.item;

public interface ItemsContract {

    interface ItemsView{
        void displayItems();
    }

    interface ItemsPresenter {
        void loadItems();
        void addItem(String name);
        void handleFabClick();
    }

}
