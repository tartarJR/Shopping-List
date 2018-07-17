package com.tatar.shoppinglist.ui.item;

import android.util.Log;

import com.tatar.shoppinglist.ItemAlertDialogUtils;
import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;

import static com.tatar.shoppinglist.ui.item.ItemsContract.*;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;

public class ItemsPresenterImpl implements ItemsPresenter {

    private static final String TAG = ItemsPresenterImpl.class.getSimpleName();

    private ItemsView itemsView;
    private ItemDao itemDao;
    private ItemAlertDialogUtils itemAlertDialogUtils;

    public ItemsPresenterImpl(ItemsView itemsView, ItemDao itemDao, ItemAlertDialogUtils itemAlertDialogUtils) {
        this.itemsView = itemsView;
        this.itemDao = itemDao;
        this.itemAlertDialogUtils = itemAlertDialogUtils;
    }

    @Override
    public void loadItems() {
        for (Item item : itemDao.getAllItems()) {
            Log.i(TAG, "loadItems: " + item.getName());
        }
    }

    @Override
    public void addItem(String name) {
        itemDao.createItem(name);
    }

    @Override
    public void handleFabClick() {
        itemAlertDialogUtils.displayAddItemDialog();
    }
}
