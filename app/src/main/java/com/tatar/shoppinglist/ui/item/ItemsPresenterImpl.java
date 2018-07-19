package com.tatar.shoppinglist.ui.item;

import com.tatar.shoppinglist.ui.helpers.ItemAlertDialogHelper;
import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.ArrayList;
import java.util.List;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

public class ItemsPresenterImpl implements ItemsPresenter {

    private static final String TAG = ItemsPresenterImpl.class.getSimpleName();

    private List<Item> itemList;

    private ItemsView itemsView;
    private ItemDao itemDao;
    private ItemAlertDialogHelper itemAlertDialogHelper;

    public ItemsPresenterImpl(ItemsView itemsView, ItemDao itemDao, ItemAlertDialogHelper itemAlertDialogHelper) {
        this.itemsView = itemsView;
        this.itemDao = itemDao;
        this.itemAlertDialogHelper = itemAlertDialogHelper;

        itemList = new ArrayList<>();
    }

    @Override
    public void loadItems() {
        itemList.addAll(itemDao.getAllItems());
        itemsView.displayItems(itemList);
        itemsView.toggleNoItemsTv();
    }

    @Override
    public void createItem(String name) {
        itemsView.notifyNewItemCreated(new Item(name));
        itemDao.createItem(name);
        itemsView.toggleNoItemsTv();
    }

    @Override
    public void updateItem(String id, String name, int position) {
        itemsView.notifyItemUpdated(position, name);
        itemDao.updateItem(id, name);
    }

    @Override
    public void deleteItem(String id, int position) {
        itemsView.notifyItemDeleted(position);
        itemDao.deleteItem(id);
        itemsView.toggleNoItemsTv();
    }

    @Override
    public void displayAddOrUpdateItemDialog() {
        itemAlertDialogHelper.displayAddItemDialog();
    }

    @Override
    public void displayActionsDialog(int position) {
        Item item = itemList.get(position);
        itemAlertDialogHelper.setUpAndDisplayActionsDialog(position, item);
    }

    @Override
    public List<Item> getItemList() {
        return itemList;
    }
}
